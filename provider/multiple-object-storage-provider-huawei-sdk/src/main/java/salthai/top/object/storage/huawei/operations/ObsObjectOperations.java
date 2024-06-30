package salthai.top.object.storage.huawei.operations;

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
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import salthai.top.object.storage.huawei.BaseObsOperations;
import salthai.top.object.storage.huawei.ObsConstants;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToCopyObjectRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToDeleteObjectRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToGetObjectMetadataArgumentsConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToGetObjectRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToListObjectsRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToObjectDoesExistRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToSetObjectAclRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentsToDeleteObjectsRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentsToPutObjectRequestConverter;
import salthai.top.object.storage.huawei.converter.domain.DeleteObjectsResultToDomainConverter;
import salthai.top.object.storage.huawei.converter.domain.ObjectListingToDomainConverter;
import salthai.top.object.storage.huawei.converter.domain.ObjectMetadataToDomainConverter;
import salthai.top.object.storage.huawei.converter.domain.ObsObjectToGetObjectDomainConverter;
import salthai.top.object.storage.huawei.converter.domain.PutObjectResultToDomainConverter;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.DeleteObjectResult;
import com.obs.services.model.DeleteObjectsResult;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.TemporarySignatureRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 华为云 obs obs
 *
 * @author Kuang HaiBo 2023/11/9 16:00
 */
public class ObsObjectOperations extends BaseObsOperations implements ObjectOperations {

	private static final String LOG_PREFIX = ObsConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(ObsObjectOperations.class);

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public ObsObjectOperations(ProviderClientManager<ObsClient> clientManager) {
		super(clientManager);
	}

	/**
	 * 上传一个对象 注意 该方法不会关闭相应的输入流
	 * @param putObjectArguments 上传参数,
	 * @return 上传对象结果
	 * @throws ObjectStorageException 上传对象时发生异常
	 */
	@Override
	public PutObjectDomain putObject(PutObjectArguments putObjectArguments) throws ObjectStorageException {
		// 即使 obs client 是可以关闭的资源,但是这里不会主动的区维护,生命周期由管理器管理,这里不关注
		return execute(obsClient -> {
			try {
				ArgumentsToPutObjectRequestConverter toReq = new ArgumentsToPutObjectRequestConverter();
				PutObjectResult putObjectResult = obsClient.putObject(toReq.convert(putObjectArguments));
				return new PutObjectResultToDomainConverter().convert(putObjectResult);
			}
			catch (ObsException e) {
				log.error("==>  {}  put error: ", LOG_PREFIX, e);
				throw new PutFileException(e);
			}
		});
	}

	/**
	 * 文件是否存在
	 * @param doesObjectExistArguments 对象是否存在
	 * @return ture 存在
	 * @throws ObjectStorageException 检查过程中发生的异常
	 */
	@Override
	public boolean doesObjectExist(DoesObjectExistArguments doesObjectExistArguments) throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				return obsClient.doesObjectExist(ConverterUtils.toTarget(doesObjectExistArguments,
						new ArgumentToObjectDoesExistRequestConverter()));
			}
			catch (ObsException e) {
				log.error("==> {} doesObjectExist error:", LOG_PREFIX, e);
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
		return execute(obsClient -> {
			try {
				ObjectMetadata objectMetadata = obsClient.getObjectMetadata(
						ConverterUtils.toTarget(arguments, new ArgumentToGetObjectMetadataArgumentsConverter()));
				ObjectMetadataDomain metadataDomain = ConverterUtils.toTarget(objectMetadata,
						new ObjectMetadataToDomainConverter());
				metadataDomain.setObjectName(arguments.getObjectName());
				metadataDomain.setBucketName(arguments.getBucketName());
				return metadataDomain;
			}
			catch (ObsException e) {
				log.error("==> {} getObjectMetadata error:", LOG_PREFIX, e);
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
		return execute(obsClient -> {
			try {
				ObsObject obsObject = obsClient
					.getObject(ConverterUtils.toTarget(arguments, new ArgumentToGetObjectRequestConverter()));
				return ConverterUtils.toTarget(obsObject, new ObsObjectToGetObjectDomainConverter());
			}
			catch (ObsException e) {
				log.error("==> {} get object error:", LOG_PREFIX, e);
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
		return execute(obsClient -> {
			try {
				ObjectListing objectListing = obsClient
					.listObjects(ConverterUtils.toTarget(arguments, new ArgumentToListObjectsRequestConverter()));
				return ConverterUtils.toTarget(objectListing, new ObjectListingToDomainConverter());
			}
			catch (ObsException e) {
				log.error("==> {} list objects error:", LOG_PREFIX, e);
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
		return execute(obsClient -> {
			try {
				DeleteObjectResult result = obsClient.deleteObject(
						ConverterUtils.toTarget(delObjectArguments, new ArgumentToDeleteObjectRequestConverter()));
				return result.isDeleteMarker();
			}
			catch (ObsException e) {
				log.error("==> {}  delete object error", LOG_PREFIX, e);
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
		return execute(obsClient -> {
			try {
				DeleteObjectsResult result = obsClient.deleteObjects(
						ConverterUtils.toTarget(delObjectsArguments, new ArgumentsToDeleteObjectsRequestConverter()));
				return ConverterUtils.toTarget(result, new DeleteObjectsResultToDomainConverter());
			}
			catch (ObsException e) {
				log.error("==> {}  delete objects error", LOG_PREFIX, e);
				throw new DelFileException(e);
			}
		});
	}

	/**
	 * 设置对象acl
	 * @param setObjectAclArguments 设置参数
	 * @return 设置结果
	 * @throws ObjectStorageException 设置过程异常
	 */
	@Override
	public boolean setObjectAcl(SetObjectAclArguments setObjectAclArguments) throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				obsClient.setObjectAcl(
						ConverterUtils.toTarget(setObjectAclArguments, new ArgumentToSetObjectAclRequestConverter()));
				return true;
			}
			catch (ObsException e) {
				log.error("==> {} set object acl error:", LOG_PREFIX, e);
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
	 * 拷贝不允许跨区域复制
	 * </p>
	 * @param arguments 拷贝参数
	 * @throws ObjectStorageException 拷贝过程发生的异常
	 * @return true：成功
	 */
	@Override
	public boolean copyObject(CopyObjectArguments arguments) throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				obsClient.copyObject(ConverterUtils.toTarget(arguments, new ArgumentToCopyObjectRequestConverter()));
				return true;
			}
			catch (ObsException e) {
				log.error("==> {}  copy error:", LOG_PREFIX, e);
				throw new CopyFileException(e);
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
		return execute(obsClient -> {
			try {
				TemporarySignatureRequest request = new TemporarySignatureRequest();
				request.setBucketName(arguments.getBucketName());
				request.setObjectKey(arguments.getObjectName());
				request.setRequestDate(new Date());
				request.setExpires(arguments.getExpiration().getSeconds());
				request.setMethod(HttpMethodEnum.valueOf(arguments.getMethod().name()));
				return obsClient.createTemporarySignature(request).getSignedUrl();
			}
			catch (Exception e) {
				log.error("==> {} gen pre url error", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

}
