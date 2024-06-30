package salthai.top.object.storage.core.operations;

import salthai.top.object.storage.core.arguments.multipart.AbortMultipartUploadArguments;
import salthai.top.object.storage.core.arguments.multipart.CompleteMultipartUploadArguments;
import salthai.top.object.storage.core.arguments.multipart.InitiateMultipartUploadArguments;
import salthai.top.object.storage.core.arguments.multipart.ListMultipartUploadsArguments;
import salthai.top.object.storage.core.arguments.multipart.ListPartsArguments;
import salthai.top.object.storage.core.arguments.multipart.UploadPartArguments;
import salthai.top.object.storage.core.arguments.multipart.UploadPartCopyArguments;
import salthai.top.object.storage.core.domain.multipart.AbortMultipartUploadDomain;
import salthai.top.object.storage.core.domain.multipart.CompleteMultipartUploadDomain;
import salthai.top.object.storage.core.domain.multipart.InitiateMultipartUploadDomain;
import salthai.top.object.storage.core.domain.multipart.ListMultipartUploadsDomain;
import salthai.top.object.storage.core.domain.multipart.ListPartsDomain;
import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;
import salthai.top.object.storage.core.domain.multipart.UploadPartCopyDomain;
import salthai.top.object.storage.core.domain.multipart.UploadPartDomain;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;

import java.util.List;

/**
 * 兼容的分片操作接口
 *
 * <p>
 * 分片上传
 * <li>1.初始化分片{@link #initiateMultipartUpload(InitiateMultipartUploadArguments)}
 * <li>2.执行上传{@link #uploadPart(UploadPartArguments)}
 * <li>3.完成分片上传{@link #completeMultipartUpload(CompleteMultipartUploadArguments)}
 * <li>4.有错误可以取消分片{@link #abortMultipartUpload(AbortMultipartUploadArguments)}
 * <p>
 * 分片拷贝
 * <li>1.初始化分片{@link #initiateMultipartUpload(InitiateMultipartUploadArguments)}
 * <li>2.执行拷贝{@link #uploadPartCopy(UploadPartCopyArguments)} }
 * <li>3.完成分片拷贝{@link #completeMultipartUpload(CompleteMultipartUploadArguments)}
 * <li>4.有错误可以取消分片{@link #abortMultipartUpload(AbortMultipartUploadArguments)}
 * </p>
 *
 * @author Kuang HaiBo 2023/11/2 16:33
 */
public interface ObjectMultipartOperations {

	/**
	 * 创建分片上传请求, 返回 UploadId
	 * @param bucketName 存储桶名称
	 * @param objectName 对象名称
	 * @return 分片上传唯一 uploadId
	 * @throws ObjectStorageException 分片上传异常
	 */
	default String initiateMultipartUpload(String bucketName, String objectName) throws ObjectStorageException {
		InitiateMultipartUploadArguments arguments = new InitiateMultipartUploadArguments();
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);
		InitiateMultipartUploadDomain domain = initiateMultipartUpload(arguments);
		return domain.getUploadId();
	}

	/**
	 * 创建分片上传请求, 返回 UploadId
	 * <p>
	 * 启动一个分片上传并返回一个包含 UploadId 的 InitiateMultipartUploadResult。
	 * 该UploadId将特定上传中的所有部分关联起来，并在您随后的每个
	 * uploadPart（UploadPartRequest）请求中使用。您还可以在最终请求中包含此UploadId，以完成或中止分片上载请求。
	 * @param arguments 创建分片上传请求参数实体 {@link InitiateMultipartUploadArguments}
	 * @return 创建分片上传结果 {@link InitiateMultipartUploadDomain}
	 * @throws ObjectStorageException 分片上传异常
	 */
	InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments)
			throws ObjectStorageException;

	/**
	 * 在分片上传中上传一个部分。必须先启动分片上传，然后才能上传任何部分。
	 * <p>
	 * 方法不会自动关闭流
	 * <p>
	 * 您的 UploadPart请求必须包括上传 ID、分片号和分片尺寸。上传ID是 服务商 在响应您的Initiate Multipart upload请求时返回的ID。
	 * 分片号可以是介于1和10000之间的任何数字，包括1和10000。分片号唯一标识分片，还定义其在上载对象中的位置。
	 * 如果使用与上载上一个分片时指定的分片号相同的分片号上载新分片，则会覆盖先前上载的分片
	 * @param arguments 部分上传请求参数实体 {@link UploadPartArguments}
	 * @return 部分上传复制结果域对象 {@link UploadPartDomain}
	 * @throws ObjectStorageException 上传过程异常
	 */
	UploadPartDomain uploadPart(UploadPartArguments arguments) throws ObjectStorageException;

	/**
	 * 将源对象复制到分片上传的一部分
	 * @param arguments 部分上传复制请求参数实体 {@link UploadPartCopyArguments}
	 * @return 部分上传复制结果域对象 {@link UploadPartCopyDomain}
	 * @throws ObjectStorageException 上传分片拷贝异常
	 */
	UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) throws ObjectStorageException;

	/**
	 * 通过组装以前上传的部分来完成分片上传
	 * @param bucketName 存储桶名称
	 * @param objectName 对象名称
	 * @param uploadId 上传ID
	 * @param parts 所有上传分片
	 * @return 完成分片上传域对象 {@link CompleteMultipartUploadDomain}
	 * @throws ObjectStorageException 完成分片上传异常
	 */
	default CompleteMultipartUploadDomain completeMultipartUpload(String bucketName, String objectName, String uploadId,
			List<PartSummaryDomain> parts) throws ObjectStorageException {
		CompleteMultipartUploadArguments arguments = new CompleteMultipartUploadArguments();
		arguments.setParts(parts);
		arguments.setUploadId(uploadId);
		arguments.setObjectName(objectName);
		arguments.setBucketName(bucketName);
		return completeMultipartUpload(arguments);
	}

	/**
	 * 通过组装以前上传的部分来完成分片上传。
	 * @param arguments 完成分片上传请求参数实体 {@link CompleteMultipartUploadArguments}
	 * @return 完成分片上传域对象 {@link CompleteMultipartUploadDomain}
	 * @throws ObjectStorageException 完成分片上传异常
	 */
	CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments)
			throws ObjectStorageException;

	/**
	 * <p>
	 * 中止分片上载。中止分片上传后，无法使用该上传ID上传任何其他部分。之前上传的任何部分所消耗的存储空间都将被释放。
	 * <p>
	 * 但是，如果当前正在进行任何分片上载，则这些分片上载可能成功，也可能不成功。
	 * <p>
	 * 因此，可能需要多次中止给定的分片上传，以便完全释放所有部分消耗的所有存储。
	 * @param arguments 终止分片上传请求参数实体 {@link AbortMultipartUploadArguments}
	 * @return 终止分片上传域对象 {@link AbortMultipartUploadDomain}
	 * @throws ObjectStorageException 终止分片上传异常
	 */
	AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments)
			throws ObjectStorageException;

	/**
	 * 获取分片列表
	 * @param bucketName 存储桶名称
	 * @param objectName 对象名称
	 * @param uploadId 上传ID
	 * @return 分片列表结果 {@link ListPartsDomain}
	 * @throws ObjectStorageException 罗列分片列表异常
	 */
	default ListPartsDomain listParts(String bucketName, String objectName, String uploadId)
			throws ObjectStorageException {
		ListPartsArguments arguments = new ListPartsArguments();
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);
		arguments.setUploadId(uploadId);
		return listParts(arguments);
	}

	/**
	 * 获取分片列表
	 * @param arguments 获取分片列表请求参数实体 {@link ListPartsArguments}
	 * @return 分片列表结果 {@link ListPartsDomain}
	 * @throws ObjectStorageException 罗列分片列表异常
	 */
	ListPartsDomain listParts(ListPartsArguments arguments);

	/**
	 * <p>
	 * 列出正在进行的分片上传。进行中的分片上传是指已使用{@link #initiateMultipartUpload(InitiateMultipartUploadArguments)}
	 * <p>
	 * 请求启动但尚未完成或中止的分片上传
	 * <p>
	 * 默认情况下，此操作在响应中最多返回1000个分片上传。可以使用请求参数上的MaxUploads属性进一步限制分片上传的数量。
	 * 如果有其他满足列表条件的分片上传，则响应{@link ListMultipartUploadsDomain}将包含一个值设置为true的IsTruncated属性。
	 * 要列出额外的分片上传，请在请求参数上使用KeyMarker和UploadIdMarker属性。
	 * </p>
	 * @param arguments 列出正在进行的分片上传请求参数实体 {@link ListMultipartUploadsArguments}
	 * @return 列出正在进行的分片上传结果 {@link ListMultipartUploadsDomain}
	 * @throws ObjectStorageException 罗列分片上传列表 异常
	 */
	ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments)
			throws ObjectStorageException;

}
