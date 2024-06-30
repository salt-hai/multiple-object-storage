package salthai.top.object.storage.aliyun.operations;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SetObjectAclRequest;
import salthai.top.object.storage.aliyun.BaseOssOperations;
import salthai.top.object.storage.aliyun.OssConstants;
import salthai.top.object.storage.aliyun.converter.ObjectVersionArgumentsToGenericRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToCopyObjectRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToDeleteObjectsRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToGeneratePreSignedUrlRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToGetObjectRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToListObjectsRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToPutObjectRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToSetObjectAclRequestConverter;
import salthai.top.object.storage.aliyun.converter.domain.DeleteObjectsResultToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.ObjectListingToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.ObjectMetadataToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.OssObjectToGetObjectDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.PutObjectResultToPutObjectDomainConverter;
import salthai.top.object.storage.core.arguments.object.CopyObjectArguments;
import salthai.top.object.storage.core.arguments.object.DelObjectArguments;
import salthai.top.object.storage.core.arguments.object.DelObjectsArguments;
import salthai.top.object.storage.core.arguments.object.DoesObjectExistArguments;
import salthai.top.object.storage.core.arguments.object.GenPreSignedUrlArguments;
import salthai.top.object.storage.core.arguments.object.GetObjectArguments;
import salthai.top.object.storage.core.arguments.object.GetObjectMetadataArguments;
import salthai.top.object.storage.core.arguments.object.ListObjectsArguments;
import salthai.top.object.storage.core.arguments.object.PutObjectArguments;
import salthai.top.object.storage.core.arguments.object.SetObjectAclArguments;
import salthai.top.object.storage.core.domain.object.DelObjectDomain;
import salthai.top.object.storage.core.domain.object.GetObjectDomain;
import salthai.top.object.storage.core.domain.object.ListObjectsDomain;
import salthai.top.object.storage.core.domain.object.ObjectMetadataDomain;
import salthai.top.object.storage.core.domain.object.PutObjectDomain;
import salthai.top.object.storage.core.exceptions.CopyFileException;
import salthai.top.object.storage.core.exceptions.DelFileException;
import salthai.top.object.storage.core.exceptions.GetFileException;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;
import salthai.top.object.storage.core.exceptions.PutFileException;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.unit.DataSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 阿里云 oss 操作客户端实例，以完成对应的操作
 *
 * @author Kuang HaiBo 2023/10/25 17:44
 */
public class OssObjectOperations extends BaseOssOperations implements ObjectOperations {

	private static final String LOG_PREFIX = OssConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(OssBucketOperations.class);

	/**
	 * 1GB
	 */
	private static final long ONE_GB = DataSize.ofGigabytes(1).toBytes();

	public OssObjectOperations(ProviderClientManager<OSS> aliYunOssManager) {
		super(aliYunOssManager);
	}

	/**
	 * 上传一个对象 注意 该方法不会关闭相应的输入流
	 * @param putObjectArguments 上传参数,
	 * @return 上传对象结果
	 * @throws ObjectStorageException 上传对象时发生异常
	 */
	@Override
	public PutObjectDomain putObject(PutObjectArguments putObjectArguments) throws ObjectStorageException {
		return execute(oss -> {
			// 请求参数转换
			ArgumentsToPutObjectRequestConverter toRequestConverter = new ArgumentsToPutObjectRequestConverter();
			try {
				PutObjectResult putObjectResult = oss.putObject(toRequestConverter.convert(putObjectArguments));
				// 响应参数转换
				PutObjectDomain objectDomain = new PutObjectResultToPutObjectDomainConverter().convert(putObjectResult);
				objectDomain.setObjectName(putObjectArguments.getObjectName());
				objectDomain.setBucketName(putObjectArguments.getBucketName());
				return objectDomain;
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} oss put error: ", LOG_PREFIX, e);
				throw new PutFileException(e);
			}
		});
	}

	/**
	 * 文件是否存在
	 * @param doesObjectExistArguments 请求参数
	 * @return ture 存在
	 */
	@Override
	public boolean doesObjectExist(DoesObjectExistArguments doesObjectExistArguments) {
		return execute(oss -> {

			Converter<DoesObjectExistArguments, GenericRequest> toReq = new ObjectVersionArgumentsToGenericRequestConverter<>();
			try {
				GenericRequest genericRequest = toReq.convert(doesObjectExistArguments);
				return oss.doesObjectExist(genericRequest);
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss doesObjectExist error:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

	/**
	 * 获取对象元信息
	 * @param arguments 获取对象元信息 {@link GetObjectMetadataArguments}
	 * @return 对象元信息结果对象 {@link ObjectMetadataDomain}
	 */
	@Override
	public ObjectMetadataDomain getObjectMetadata(GetObjectMetadataArguments arguments) {
		return execute(oss -> {
			Converter<GetObjectMetadataArguments, GenericRequest> toReq = new ObjectVersionArgumentsToGenericRequestConverter<>();
			try {
				// 并不支持其他的搜索条件 这里使用简单的查询
				ObjectMetadata objectMetadata = oss.getObjectMetadata(toReq.convert(arguments));
				ObjectMetadataToDomainConverter toResult = new ObjectMetadataToDomainConverter();
				ObjectMetadataDomain domain = toResult.convert(objectMetadata);

				domain.setObjectName(arguments.getObjectName());
				domain.setBucketName(arguments.getBucketName());
				return domain;
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss get object metadata error:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

	/**
	 * 获取（下载）对象
	 * @param arguments 获取（下载）对象请求参数实体 {@link GetObjectArguments}
	 * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
	 */
	@Override
	public GetObjectDomain getObject(GetObjectArguments arguments) {
		return execute(oss -> {
			ArgumentsToGetObjectRequestConverter toReq = new ArgumentsToGetObjectRequestConverter();
			try {
				OSSObject ossObject = oss.getObject(toReq.convert(arguments));
				return new OssObjectToGetObjectDomainConverter().convert(ossObject);
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss get object error:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

	/**
	 * 列表查询
	 * @param arguments 查询参数
	 * @return 查询结果
	 */
	@Override
	public ListObjectsDomain listObjects(ListObjectsArguments arguments) {
		Objects.requireNonNull(arguments, "query arguments cant be null");
		return execute(oss -> {
			try {
				ObjectListing objectListing = oss
					.listObjects(new ArgumentsToListObjectsRequestConverter().convert(arguments));
				return new ObjectListingToDomainConverter().convert(objectListing);
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} list objects error:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}

		});
	}

	/**
	 * 生成 预签名 url
	 * @param arguments 生成参数
	 * @return url
	 */
	@Override
	public String genPreSignedUrl(GenPreSignedUrlArguments arguments) throws ObjectStorageException {
		return execute(oss -> {
			try {
				return oss
					.generatePresignedUrl(new ArgumentsToGeneratePreSignedUrlRequestConverter().convert(arguments))
					.toString();
			}
			catch (ClientException e) {
				log.error("==> {} oss  generate pre signed url error:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

	/**
	 * 删除对象
	 * @param delObjectArguments 删除参数
	 * @return true:成功
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	@Override
	public boolean delObject(DelObjectArguments delObjectArguments) throws ObjectStorageException {
		Converter<DelObjectArguments, GenericRequest> toReq = new ObjectVersionArgumentsToGenericRequestConverter<>();
		GenericRequest genericRequest = toReq.convert(delObjectArguments);
		return execute(oss -> {
			try {
				oss.deleteObject(genericRequest);
				return true;
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss delete object error", LOG_PREFIX, e);
				throw new DelFileException(e);
			}
		});
	}

	/**
	 * 同时删除多个对象
	 * @param delObjectsArguments 请求参数
	 * @return 删除成功的对象集合
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	@Override
	public List<DelObjectDomain> delObjects(DelObjectsArguments delObjectsArguments) throws ObjectStorageException {
		ArgumentsToDeleteObjectsRequestConverter toReq = new ArgumentsToDeleteObjectsRequestConverter();
		return execute(oss -> {
			try {
				DeleteObjectsResult ossDelResult = oss.deleteObjects(toReq.convert(delObjectsArguments));
				DeleteObjectsResultToDomainConverter toDomain = new DeleteObjectsResultToDomainConverter();
				// 参数转换
				return toDomain.convert(ossDelResult);
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss delete objects error", LOG_PREFIX, e);
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * 设置对象acl
	 * @param setObjectAclArguments 设置参数
	 * @return 设置结果
	 */
	@Override
	public boolean setObjectAcl(SetObjectAclArguments setObjectAclArguments) {
		return execute(oss -> {
			ArgumentsToSetObjectAclRequestConverter toReq = new ArgumentsToSetObjectAclRequestConverter();
			SetObjectAclRequest setObjectAclRequest = toReq.convert(setObjectAclArguments);
			try {
				oss.setObjectAcl(setObjectAclRequest);
				return true;
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss set object acl error:", LOG_PREFIX, e);
				return false;
			}
		});

	}

	/**
	 * <p>
	 * 拷贝对象
	 * <p>
	 * 拷贝文件时，您必须拥有源文件的读权限及目标Bucket的读写权限。
	 * <p>
	 * 拷贝文件时，您需要确保源Bucket和目标Bucket均未设置合规保留策略，否则报错The object you specified is immutable.
	 * @param arguments 拷贝参数
	 * @return true：成功
	 * @throws ObjectStorageException 拷贝过程发生的异常
	 */
	@Override
	public boolean copyObject(CopyObjectArguments arguments) throws ObjectStorageException {
		// 先获取源文件
		GetObjectMetadataArguments getObjectMetadataArguments = new GetObjectMetadataArguments();
		getObjectMetadataArguments.setBucketName(arguments.getSource().getBucketName());
		getObjectMetadataArguments.setObjectName(arguments.getSource().getObjectName());
		getObjectMetadataArguments.setVersionId(arguments.getSource().getVersionId());
		ObjectMetadataDomain sourceMetadata = getObjectMetadata(getObjectMetadataArguments);
		return execute(oss -> {
			if (sourceMetadata.getContentLength() >= ONE_GB) {
				log.warn(
						"==> {} oss simple copy object:[{}];when i write this code, the copy threshold is one gigabytes,maybe you can use multipart copy",
						LOG_PREFIX, arguments.getSource().getObjectName());
			}
			ArgumentsToCopyObjectRequestConverter toReq = new ArgumentsToCopyObjectRequestConverter();
			// 普通拷贝
			try {
				oss.copyObject(toReq.convert(arguments));
				return true;
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} oss copy error:", LOG_PREFIX, e);
				throw new CopyFileException(e);
			}
		});
	}

}
