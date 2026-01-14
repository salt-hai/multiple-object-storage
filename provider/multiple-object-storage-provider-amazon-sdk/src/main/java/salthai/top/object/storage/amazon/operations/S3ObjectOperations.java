package salthai.top.object.storage.amazon.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salthai.top.object.storage.amazon.BaseS3Operations;
import salthai.top.object.storage.amazon.S3Constants;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.converter.argument.ArgumentsToGetObjectRequestConverter;
import salthai.top.object.storage.amazon.converter.argument.ArgumentsToHeadObjectRequestConverter;
import salthai.top.object.storage.amazon.converter.argument.ArgumentsToUploadRequestConverter;
import salthai.top.object.storage.amazon.converter.domain.GetObjectResponseToDomainConverter;
import salthai.top.object.storage.amazon.converter.domain.HeadObjectResponseToMetadataDomainConverter;
import salthai.top.object.storage.amazon.converter.domain.PutObjectResponseToDomainConverter;
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
import salthai.top.object.storage.core.exceptions.GetFileException;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;
import salthai.top.object.storage.core.exceptions.PutFileException;
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse as S3GetObjectResponse;
import software.amazon.awssdk.transfer.s3.model.CompletedUpload;
import software.amazon.awssdk.transfer.s3.model.Upload;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * s3实现对象操作
 *
 * @author Kuang HaiBo 2025/5/13 16:04
 */
public class S3ObjectOperations extends BaseS3Operations implements ObjectOperations {

	private static final String LOG_PREFIX = S3Constants.LOG_PREFIX;

	private static final Logger log = LoggerFactory.getLogger(S3ObjectOperations.class);

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public S3ObjectOperations(ProviderClientManager<S3ClientPackage> clientManager) {
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
		return execute(s3ClientPackage -> {
			try {
				Upload upload = s3ClientPackage.getS3TransferManager()
					.upload(ConverterUtils.toTarget(putObjectArguments, new ArgumentsToUploadRequestConverter()));
				CompletedUpload join = upload.completionFuture().join();
				PutObjectResponse response = join.response();
				PutObjectDomain target = ConverterUtils.toTarget(response, new PutObjectResponseToDomainConverter());
				target.setObjectName(putObjectArguments.getObjectName());
				target.setBucketName(putObjectArguments.getBucketName());
				target.setRegion(putObjectArguments.getRegion());
				return target;
			}
			catch (UnsupportedOperationException e) {
				log.error("==>  {} s3 put error: ", LOG_PREFIX, e);
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
		return execute(s3ClientPackage -> {
			HeadObjectRequest request = HeadObjectRequest.builder()
				.bucket(doesObjectExistArguments.getBucketName())
				.key(doesObjectExistArguments.getObjectName())
				.versionId(doesObjectExistArguments.getVersionId())
				.build();
			try {
				HeadObjectResponse headObjectResponse = s3ClientPackage.getS3Client().headObject(request);
				return Objects.nonNull(headObjectResponse);
			}
			catch (UnsupportedOperationException e) {
				log.error("==> {} s3 doesObjectExist error:", LOG_PREFIX, e);
				return false;
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
		return execute(s3ClientPackage -> {
			try {
				HeadObjectResponse headObjectResponse = s3ClientPackage.getS3Client()
					.headObject(ConverterUtils.toTarget(arguments, new ArgumentsToHeadObjectRequestConverter()));
				ObjectMetadataDomain domain = ConverterUtils.toTarget(headObjectResponse,
						new HeadObjectResponseToMetadataDomainConverter());
				domain.setRegion(arguments.getRegion());
				domain.setBucketName(arguments.getBucketName());
				domain.setObjectName(arguments.getObjectName());
				return domain;
			}
			catch (UnsupportedOperationException e) {
				log.error("==>  {} s3 get object metadata error: ", LOG_PREFIX, e);
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
		return execute(s3ClientPackage -> {
			try {
				software.amazon.awssdk.services.s3.model.GetObjectRequest request = ConverterUtils.toTarget(arguments,
						new ArgumentsToGetObjectRequestConverter());
				ResponseBytes<S3GetObjectResponse> response = s3ClientPackage.getS3Client().getObjectAsBytes(request);
				GetObjectDomain domain = ConverterUtils.toTarget(response, new GetObjectResponseToDomainConverter());
				domain.setBucketName(arguments.getBucketName());
				domain.setObjectName(arguments.getObjectName());
				return domain;
			}
			catch (UnsupportedOperationException e) {
				log.error("==> {} s3 get object error: ", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

	/**
	 * 列举对象
	 * @param arguments 对象查询参数
	 * @return 列举对象的结果
	 */
	@Override
	public ListObjectsDomain listObjects(ListObjectsArguments arguments) {
		return null;
	}

	/**
	 * 删除对象
	 * @param delObjectArguments 删除参数
	 * @return true:成功
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	@Override
	public boolean delObject(DelObjectArguments delObjectArguments) throws ObjectStorageException {
		return false;
	}

	/**
	 * 同时删除多个对象
	 * @param delObjectsArguments 请求参数
	 * @return 删除成功的对象集合
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	@Override
	public List<DelObjectDomain> delObjects(DelObjectsArguments delObjectsArguments) throws ObjectStorageException {
		return Collections.emptyList();
	}

	/**
	 * 设置对象acl
	 * @param setObjectAclArguments 设置参数
	 * @return 设置结果
	 * @throws ObjectStorageException 设置过程异常
	 */
	@Override
	public boolean setObjectAcl(SetObjectAclArguments setObjectAclArguments) throws ObjectStorageException {
		return false;
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
	 * @return true：成功
	 * @throws ObjectStorageException 拷贝过程发生的异常
	 */
	@Override
	public boolean copyObject(CopyObjectArguments arguments) throws ObjectStorageException {
		return false;
	}

	/**
	 * 生成 预签名 url
	 * @param arguments 生成参数
	 * @return url
	 */
	@Override
	public String genPreSignedUrl(GenPreSignedUrlArguments arguments) throws ObjectStorageException {
		return "";
	}

}
