package com.hengtacc.object.storage.core.operations;

import com.hengtacc.object.storage.core.FileWrappers;
import com.hengtacc.object.storage.core.acl.AccessControlList;
import com.hengtacc.object.storage.core.arguments.object.CopyObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.CopySourceObjectArguments;
import com.hengtacc.object.storage.core.arguments.object.CopyTargetObjectArguments;
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
import com.hengtacc.object.storage.core.enums.HttpMethod;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;
import com.hengtacc.object.storage.core.exceptions.PutFileException;
import com.hengtacc.object.storage.core.utils.PathUtils;
import com.hengtacc.object.storage.core.wrapper.FileWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 一个兼容的对象存储操作接口
 *
 * <p>
 * <li>阿里云</li>
 * <li>华为云</li>
 * <li>百度云</li>
 * </p>
 *
 * @author Kuang HaiBo 2023/10/26 10:32
 */
public interface ObjectOperations {

	/**
	 * 此方法会关闭 文件包装类中的输入流
	 * <p>
	 * 简单上传 简单的上传,用户提供文件来源 可以完成上传
	 * @param bucketName 目标桶名称
	 * @param fileWrapper 文件包装类
	 * @param objectName 对象key
	 * @return 简单对象文件
	 * @throws ObjectStorageException 上传过程中可能发生的异常
	 */
	default PutObjectDomain putObject(String bucketName, FileWrapper fileWrapper, String objectName)
			throws ObjectStorageException {
		// 不设置acl
		return putObject(bucketName, fileWrapper, objectName, null);
	}

	/**
	 * 此方法会关闭 文件包装类中的输入流
	 * <p>
	 * 上传一个文件 简单的上传,用户提供文件来源,对象以及对象acl即可以完成上传
	 * <p>
	 * 对于大文件,请使用分片上传
	 * <p>
	 * 对于任何一种上传来说,指定文件大小是个好习惯
	 * <p>
	 * 工具目前并没有采取读取流的方式来获取文件大小,直接读取流文件获取大小,如果是大文件则很容易占用巨大内存,造成oom
	 * @param bucketName 对象存放桶名
	 * @param fileWrapper 文件包装类 {@link FileWrappers}
	 * @throws ObjectStorageException 上传时发生的异常
	 * @param objectName 对象key
	 * @param acl 对象acl
	 * @return 上传结果
	 */
	default PutObjectDomain putObject(String bucketName, FileWrapper fileWrapper, String objectName,
			AccessControlList acl) throws ObjectStorageException {
		PutObjectArguments putObjectArguments = new PutObjectArguments();
		try (InputStream inputStream = fileWrapper.getInputStream()) {
			putObjectArguments.setInputStream(inputStream);
			putObjectArguments.setObjectName(objectName);
			putObjectArguments.setObjectSize(fileWrapper.getByteSize());
			putObjectArguments.setBucketName(bucketName);
			putObjectArguments.setContentType(fileWrapper.getContentType());
			return putObject(putObjectArguments, acl);
		}
		catch (IOException e) {
			throw new PutFileException(e);
		}
	}

	/**
	 * 上传一个对象并设置acl 注意:该方法不会关闭相应的输入流,
	 * @param putObjectArguments 上传参数,
	 * @throws ObjectStorageException 上传对象时发生异常
	 * @return 上传对象结果
	 */
	default PutObjectDomain putObject(PutObjectArguments putObjectArguments, AccessControlList acl)
			throws ObjectStorageException {
		PutObjectDomain result = putObject(putObjectArguments);
		Objects.requireNonNull(result, "put object result is null we cant do next operation");
		if (Objects.nonNull(acl)) {
			SetObjectAclArguments aclArguments = new SetObjectAclArguments();
			aclArguments.setAcl(acl);
			aclArguments.setVersionId(result.getVersionId());
			aclArguments.setBucketName(putObjectArguments.getBucketName());
			aclArguments.setObjectName(putObjectArguments.getObjectName());
			setObjectAcl(aclArguments);
		}
		return result;
	}

	/**
	 * 上传一个对象 注意 该方法不会关闭相应的输入流
	 * @param putObjectArguments 上传参数,
	 * @throws ObjectStorageException 上传对象时发生异常
	 * @return 上传对象结果
	 */
	PutObjectDomain putObject(PutObjectArguments putObjectArguments) throws ObjectStorageException;

	/**
	 * 创建文件夹
	 * @param path 文件夹路径 eg: example-dir/
	 * @param bucketName 桶名称
	 * @throws ObjectStorageException 创建过程发生的异常
	 * @return true：成功
	 */
	default boolean mkdir(String bucketName, String path) throws ObjectStorageException {
		PutObjectArguments putObjectArguments = new PutObjectArguments();
		putObjectArguments.setObjectName(path);
		putObjectArguments.setBucketName(bucketName);
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				"".getBytes(StandardCharsets.UTF_8))) {
			putObjectArguments.setInputStream(byteArrayInputStream);
			PutObjectDomain result = putObject(putObjectArguments);
			return Objects.nonNull(result.getObjectName());
		}
		catch (IOException e) {
			return false;
		}
	}

	/**
	 * 多级文件夹创建
	 * @param bucketName 桶名称
	 * @param firstPath 文件夹路径 eg: example-dir/
	 * @param paths 文件夹子路径 eg: example-dir-1/ example-dir-2/
	 * @return example-dir/example-dir-1/example-dir-2/
	 * @throws ObjectStorageException 创建过程发生的异常
	 */
	default String mkdir(String bucketName, String firstPath, String... paths) throws ObjectStorageException {
		if (!mkdir(bucketName, PathUtils.join(firstPath, paths))) {
			throw new PutFileException("多级文件夹创建失败");
		}
		return PathUtils.join(firstPath, paths);
	}

	/**
	 * 文件是否存在
	 * @param doesObjectExistArguments 对象是否存在
	 * @throws ObjectStorageException 检查过程中发生的异常
	 * @return ture 存在
	 */
	boolean doesObjectExist(DoesObjectExistArguments doesObjectExistArguments) throws ObjectStorageException;

	/**
	 * 文件是否存在
	 * @param bucketName 桶
	 * @param objectName 对象
	 * @return true 存在
	 * @throws ObjectStorageException 检查过程中发生的异常
	 */
	default boolean doesObjectExist(String bucketName, String objectName) throws ObjectStorageException {
		DoesObjectExistArguments arguments = new DoesObjectExistArguments();
		arguments.setObjectName(objectName);
		arguments.setBucketName(bucketName);
		return doesObjectExist(arguments);
	}

	/**
	 * 获取对象元信息
	 * @param arguments 获取对象元信息 {@link GetObjectMetadataArguments}
	 * @return 对象元信息结果对象 {@link ObjectMetadataDomain}
	 */
	ObjectMetadataDomain getObjectMetadata(GetObjectMetadataArguments arguments);

	/**
	 * 获取（下载）对象
	 * @param bucketName 存储桶名称
	 * @param objectName 对象名称
	 * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
	 */
	default GetObjectDomain getObject(String bucketName, String objectName) {
		GetObjectArguments arguments = new GetObjectArguments();
		arguments.setObjectName(objectName);
		arguments.setBucketName(bucketName);
		return getObject(arguments);
	}

	/**
	 * 获取（下载）对象
	 * @param arguments 获取（下载）对象请求参数实体 {@link GetObjectArguments}
	 * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
	 */
	GetObjectDomain getObject(GetObjectArguments arguments);

	/**
	 * 列举桶内对象
	 * @param bucketName 桶名称
	 * @return 列举对象的结果
	 */
	default ListObjectsDomain listObjects(String bucketName) {
		ListObjectsArguments arguments = new ListObjectsArguments();
		arguments.setBucketName(bucketName);
		return listObjects(arguments);
	}

	/**
	 * 列举对象
	 * @param arguments 对象查询参数
	 * @return 列举对象的结果
	 */
	ListObjectsDomain listObjects(ListObjectsArguments arguments);

	/**
	 * 删除对象
	 * @param objectName 对象名称
	 * @param bucketName 桶名称
	 * @throws ObjectStorageException 删除过程发生异常
	 * @return true:删除成功
	 */
	default boolean delObject(String bucketName, String objectName) throws ObjectStorageException {
		DelObjectArguments delObjectArguments = new DelObjectArguments();
		delObjectArguments.setObjectName(objectName);
		delObjectArguments.setBucketName(bucketName);
		return delObject(delObjectArguments);
	}

	/**
	 * 删除对象
	 * @param delObjectArguments 删除参数
	 * @return true:成功
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	boolean delObject(DelObjectArguments delObjectArguments) throws ObjectStorageException;

	/**
	 * 同时删除多个对象
	 * @param delObjectsArguments 请求参数
	 * @return 删除成功的对象集合
	 * @throws ObjectStorageException 删除过程发生异常
	 */
	List<DelObjectDomain> delObjects(DelObjectsArguments delObjectsArguments) throws ObjectStorageException;

	/**
	 * 设置对象acl
	 * @param setObjectAclArguments 设置参数
	 * @return 设置结果
	 * @throws ObjectStorageException 设置过程异常
	 */
	boolean setObjectAcl(SetObjectAclArguments setObjectAclArguments) throws ObjectStorageException;

	/**
	 * 设置对象acl
	 * @param objectName 对象名称
	 * @param bucketName 桶名称
	 * @param objectAcl 对象acl
	 * @throws ObjectStorageException 设置过程发生的异常
	 * @return true: 设置成功
	 */
	default boolean setObjectAcl(String bucketName, String objectName, AccessControlList objectAcl)
			throws ObjectStorageException {
		SetObjectAclArguments setObjectAclArguments = new SetObjectAclArguments();
		setObjectAclArguments.setObjectName(objectName);
		setObjectAclArguments.setBucketName(bucketName);
		setObjectAclArguments.setAcl(objectAcl);
		return setObjectAcl(setObjectAclArguments);
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
	boolean copyObject(CopyObjectArguments arguments) throws ObjectStorageException;

	/**
	 * <p>
	 * 拷贝对象
	 * <p>
	 * 拷贝文件时，您必须拥有源文件的读权限及目标Bucket的读写权限。
	 * <p>
	 * 拷贝文件时，您需要确保源Bucket和目标Bucket均未设置合规保留策略，否则报错The object you specified is immutable.
	 * 拷贝不允许跨区域复制
	 * </p>
	 * @param sourceName 来源
	 * @param sourceBucket 来源桶
	 * @param targetName 目标key
	 * @param targetBucket 目标桶
	 * @throws ObjectStorageException 拷贝过程发生的异常
	 * @return true：成功
	 */
	default boolean copyObject(String sourceBucket, String sourceName, String targetBucket, String targetName)
			throws ObjectStorageException {
		CopyObjectArguments copyObjectArguments = new CopyObjectArguments();
		CopySourceObjectArguments source = new CopySourceObjectArguments();
		source.setBucketName(sourceBucket);
		source.setObjectName(sourceName);
		copyObjectArguments.setSource(source);
		CopyTargetObjectArguments target = new CopyTargetObjectArguments();
		target.setBucketName(targetBucket);
		target.setObjectName(targetName);
		copyObjectArguments.setTarget(target);
		return copyObject(copyObjectArguments);
	}

	/**
	 * 生成 预签名 url
	 * @param arguments 生成参数
	 * @return url
	 */
	String genPreSignedUrl(GenPreSignedUrlArguments arguments) throws ObjectStorageException;

	/**
	 * 生成 预签名 url
	 * @param bucketName 对象桶
	 * @param expiration 多久之后过期
	 * @param httpMethod url方法
	 * @param objectName 对象名称
	 * @throws ObjectStorageException 生成过程可能发生异常
	 * @return url
	 */
	default String genPreSignedUrl(String bucketName, String objectName, HttpMethod httpMethod, Duration expiration)
			throws ObjectStorageException {
		GenPreSignedUrlArguments arguments = new GenPreSignedUrlArguments();
		arguments.setMethod(httpMethod);
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);
		arguments.setExpiration(expiration);
		return genPreSignedUrl(arguments);
	}

	/**
	 * 可供下载的预签名 url
	 * @param objectName 对象key
	 * @param bucketName 桶
	 * @param expiration url 过期具体时间
	 * @return 可供下载的预签名 url
	 * @throws ObjectStorageException 签名过程发生异常
	 */
	default String getPreSignedUrl(String bucketName, String objectName, Date expiration)
			throws ObjectStorageException {
		GenPreSignedUrlArguments arguments = new GenPreSignedUrlArguments();
		arguments.setMethod(HttpMethod.GET);
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);
		Date now = new Date();
		arguments.setExpiration(Duration.ofMillis(expiration.getTime() - now.getTime()));
		return genPreSignedUrl(arguments);
	}

	/**
	 * 可供上传的预签名 put url
	 * @param objectName 对象key
	 * @param bucketName 桶
	 * @param expiration url 过期具体时间
	 * @throws ObjectStorageException 签名过程发生异常
	 * @return 可供下载的预签名 url
	 */
	default String putPreSignedUrl(String bucketName, String objectName, Date expiration)
			throws ObjectStorageException {
		GenPreSignedUrlArguments arguments = new GenPreSignedUrlArguments();
		arguments.setMethod(HttpMethod.PUT);
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);
		Date now = new Date();
		arguments.setExpiration(Duration.ofMillis(expiration.getTime() - now.getTime()));
		return genPreSignedUrl(arguments);
	}

}
