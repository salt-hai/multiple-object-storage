package com.hengtacc.object.storage.baidu.operations;

import com.baidubce.BceClientException;
import com.baidubce.http.HttpMethodName;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.CopyObjectRequest;
import com.baidubce.services.bos.model.DeleteMultipleObjectsResponse;
import com.baidubce.services.bos.model.GeneratePresignedUrlRequest;
import com.baidubce.services.bos.model.GetObjectMetadataRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.hengtacc.object.storage.baidu.BaseBosOperations;
import com.hengtacc.object.storage.baidu.BosConstants;
import com.hengtacc.object.storage.baidu.converter.acl.BosAclConverter;
import com.hengtacc.object.storage.baidu.converter.arguments.ArgumentsToDeleteMultipleObjectsRequestConverter;
import com.hengtacc.object.storage.baidu.converter.arguments.ArgumentsToListObjectsRequestConverter;
import com.hengtacc.object.storage.baidu.converter.arguments.ArgumentsToPutObjectRequestConverter;
import com.hengtacc.object.storage.baidu.converter.domain.ListObjectsResponseToDomainConverter;
import com.hengtacc.object.storage.baidu.converter.domain.ObjectMetadataToDomainConverter;
import com.hengtacc.object.storage.baidu.converter.domain.PutObjectResponseToDomainConverter;
import com.hengtacc.object.storage.core.arguments.object.CopyObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.DelObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.DelObjectsArguments;
import com.hengtacc.object.storage.core.arguments.object.DoesObjectExistArguments;
import com.hengtacc.object.storage.core.arguments.object.GenPreSignedUrlArguments;
import com.hengtacc.object.storage.core.arguments.object.GetObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.GetObjectMetadataArguments;
import com.hengtacc.object.storage.core.arguments.object.ListObjectsArguments;
import com.hengtacc.object.storage.core.arguments.object.PutObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.SetObjectAclArguments;
import com.hengtacc.object.storage.core.domain.object.DelObjectDomain;
import com.hengtacc.object.storage.core.domain.object.GetObjectDomain;
import com.hengtacc.object.storage.core.domain.object.ListObjectsDomain;
import com.hengtacc.object.storage.core.domain.object.ObjectMetadataDomain;
import com.hengtacc.object.storage.core.domain.object.PutObjectDomain;
import com.hengtacc.object.storage.core.exceptions.CopyFileException;
import com.hengtacc.object.storage.core.exceptions.DelFileException;
import com.hengtacc.object.storage.core.exceptions.GetFileException;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;
import com.hengtacc.object.storage.core.exceptions.PutFileException;
import com.hengtacc.object.storage.core.operations.ObjectOperations;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import com.hengtacc.object.storage.core.utils.ConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * bos 对象操作
 *
 * @author HaiBo Kuang 2023/11/11 17:41
 */
public class BosObjectOperations extends BaseBosOperations implements ObjectOperations {

	private final static String LOG_PREFIX = BosConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(BosObjectOperations.class);

	private static final BosAclConverter ACL_CONVERTER = BosAclConverter.INSTANCE;

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BosObjectOperations(ProviderClientManager<BosClient> clientManager) {
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
		return execute(bosClient -> {
			ArgumentsToPutObjectRequestConverter toReq = new ArgumentsToPutObjectRequestConverter();
			try {
				PutObjectResponse putObjectResponse = bosClient.putObject(toReq.convert(putObjectArguments));
				PutObjectResponseToDomainConverter toDomain = new PutObjectResponseToDomainConverter();
				PutObjectDomain domain = toDomain.convert(putObjectResponse);
				domain.setObjectName(putObjectArguments.getObjectName());
				domain.setBucketName(putObjectArguments.getBucketName());
				return domain;
			}
			catch (BceClientException bceClientException) {
				log.error("==> {} put object error:", LOG_PREFIX, bceClientException);
				throw new PutFileException(bceClientException);
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
		return execute((Function<BosClient, Boolean>) bosClient -> bosClient
			.doesObjectExist(doesObjectExistArguments.getBucketName(), doesObjectExistArguments.getObjectName()));

	}

	/**
	 * 获取对象元信息
	 * @param arguments 获取对象元信息 {@link GetObjectMetadataArguments}
	 * @return 对象元信息结果对象 {@link ObjectMetadataDomain}
	 */
	@Override
	public ObjectMetadataDomain getObjectMetadata(GetObjectMetadataArguments arguments) {
		GetObjectMetadataRequest request = new GetObjectMetadataRequest(arguments.getBucketName(),
				arguments.getObjectName());
		return execute(bosClient -> {
			try {
				ObjectMetadata objectMetadata = bosClient.getObjectMetadata(request);
				ObjectMetadataToDomainConverter toDomain = new ObjectMetadataToDomainConverter();
				ObjectMetadataDomain domain = toDomain.convert(objectMetadata);
				domain.setObjectName(arguments.getObjectName());
				domain.setBucketName(arguments.getBucketName());
				return domain;
			}
			catch (BceClientException e) {
				log.error("==> {} get object metadata error", LOG_PREFIX, e);
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
		return execute(bosClient -> {
			try {
				BosObject bosObject = bosClient.getObject(arguments.getBucketName(), arguments.getObjectName());
				GetObjectDomain domain = new GetObjectDomain();
				domain.setObjectContent(bosObject.getObjectContent());
				domain.setBucketName(bosObject.getBucketName());
				domain.setObjectName(bosObject.getKey());
				return domain;
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				ListObjectsResponse objects = bosClient
					.listObjects(ConverterUtils.toTarget(arguments, new ArgumentsToListObjectsRequestConverter()));
				return ConverterUtils.toTarget(objects, new ListObjectsResponseToDomainConverter());
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				bosClient.deleteObject(delObjectArguments.getBucketName(), delObjectArguments.getObjectName());
				return true;
			}
			catch (BceClientException e) {
				log.error("==>  {} del object error:", LOG_PREFIX, e);
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
		ArgumentsToDeleteMultipleObjectsRequestConverter toReq = new ArgumentsToDeleteMultipleObjectsRequestConverter();
		return execute(bosClient -> {
			try {
				DeleteMultipleObjectsResponse delResult = bosClient
					.deleteMultipleObjects(toReq.convert(delObjectsArguments));
				List<DelObjectDomain> domains = new ArrayList<>(delObjectsArguments.getObjects().size());
				List<String> preDelObjectNames = delObjectsArguments.getObjectNames();
				delResult.getErrors().forEach(error -> preDelObjectNames.remove(error.getKey()));
				preDelObjectNames.forEach(preDelObjectName -> domains.add(new DelObjectDomain(preDelObjectName)));
				return domains;
			}
			catch (BceClientException e) {
				log.error("==>  {} del objects error:", LOG_PREFIX, e);
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
		return execute(bosClient -> {
			try {
				bosClient.setObjectAcl(setObjectAclArguments.getBucketName(), setObjectAclArguments.getObjectName(),
						ACL_CONVERTER.convert(setObjectAclArguments.getAcl()));
				return true;
			}
			catch (BceClientException e) {
				log.error("==> {} acl set error:", LOG_PREFIX, e);
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
	 * @return true：成功
	 * @throws ObjectStorageException 拷贝过程发生的异常
	 */
	@Override
	public boolean copyObject(CopyObjectArguments arguments) throws ObjectStorageException {
		CopyObjectRequest copyObjectRequest = new CopyObjectRequest(arguments.getSource().getBucketName(),
				arguments.getSource().getObjectName(), arguments.getTarget().getBucketName(),
				arguments.getTarget().getObjectName());
		return execute(bosClient -> {
			try {
				bosClient.copyObject(copyObjectRequest);
				return true;
			}
			catch (BceClientException e) {
				log.error("==>  {} copy object error", LOG_PREFIX, e);
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
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				arguments.getBucketName(), arguments.getObjectName(),
				HttpMethodName.valueOf(arguments.getMethod().name()));
		generatePresignedUrlRequest.setContentMd5(arguments.getContentMD5());
		generatePresignedUrlRequest.setContentType(arguments.getContentType());
		// 设置过期时间
		Duration expiration = arguments.getExpiration();
		generatePresignedUrlRequest.setExpiration((int) expiration.getSeconds());
		return execute(bosClient -> {
			try {
				return bosClient.generatePresignedUrl(generatePresignedUrlRequest).toString();
			}
			catch (Exception e) {
				log.error("==> {} gen pre signed url error", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

}
