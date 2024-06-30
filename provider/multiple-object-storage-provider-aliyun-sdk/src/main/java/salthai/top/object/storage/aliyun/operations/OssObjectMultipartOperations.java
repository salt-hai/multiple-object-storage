package salthai.top.object.storage.aliyun.operations;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.UploadPartCopyResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import salthai.top.object.storage.aliyun.BaseOssOperations;
import salthai.top.object.storage.aliyun.OssConstants;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToAbortMultipartUploadRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToCompleteMultipartUploadRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToInitiateMultipartUploadRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToListMultipartUploadsRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToListPartsRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToUploadPartCopyRequestConverter;
import salthai.top.object.storage.aliyun.converter.arguments.ArgumentsToUploadPartRequestConverter;
import salthai.top.object.storage.aliyun.converter.domain.CompleteMultipartUploadResultToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.InitiateMultipartUploadResultToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.MultipartUploadListingToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.PartListingToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.UploadPartCopyResultToDomainConverter;
import salthai.top.object.storage.aliyun.converter.domain.UploadPartResultToDomainConverter;
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
import salthai.top.object.storage.core.domain.multipart.UploadPartCopyDomain;
import salthai.top.object.storage.core.domain.multipart.UploadPartDomain;
import salthai.top.object.storage.core.exceptions.GetFileException;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;
import salthai.top.object.storage.core.exceptions.PutFileException;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.operations.ObjectMultipartOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * oss 分片操作
 *
 * @author Kuang HaiBo 2023/11/2 17:00
 */
public class OssObjectMultipartOperations extends BaseOssOperations implements ObjectMultipartOperations {

	private static final String LOG_PREFIX = OssConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(OssObjectMultipartOperations.class);

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public OssObjectMultipartOperations(ProviderClientManager<OSS> clientManager) {
		super(clientManager);
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
	@Override
	public InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments)
			throws ObjectStorageException {
		return execute(oss -> {
			ArgumentsToInitiateMultipartUploadRequestConverter toReq = new ArgumentsToInitiateMultipartUploadRequestConverter();
			try {
				InitiateMultipartUploadResult result = oss.initiateMultipartUpload(toReq.convert(arguments));
				return new InitiateMultipartUploadResultToDomainConverter().convert(result);
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} initiate multipart upload error the cause:", LOG_PREFIX, e);
				throw new PutFileException("initiate multipart upload error the cause:", e);
			}
		});
	}

	/**
	 * 在分片上传中上传一个部分。必须先启动分片上传，然后才能上传任何部分。
	 * <p>
	 * 方法不会自动关闭流
	 * <p>
	 * 您的 UploadPart请求必须包括上传 ID、分片号和分片尺寸。上传ID是 服务商 在响应您的Initiate Multipart upload请求时返回的ID。
	 * 分片号可以是介于1和10000之间的任何数字，包括1和10000。分片号唯一标识分片，还定义其在上载对象中的位置。
	 * 如果使用与上载上一个分片时指定的分片号相同的分片号上载新分片，则会覆盖先前上载的分片
	 * @param arguments 部分上传请求参数实体
	 * {@link salthai.top.object.storage.core.arguments.multipart.UploadPartArguments}
	 * @return 部分上传复制结果域对象 {@link UploadPartDomain}
	 * @throws ObjectStorageException 上传过程异常
	 */
	@Override
	public UploadPartDomain uploadPart(UploadPartArguments arguments) throws ObjectStorageException {
		return execute(oss -> {
			Converter<UploadPartArguments, UploadPartRequest> toReq = new ArgumentsToUploadPartRequestConverter();
			try {
				UploadPartResult result = oss.uploadPart(toReq.convert(arguments));
				return ConverterUtils.toTarget(result, new UploadPartResultToDomainConverter());
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} upload part error the cause:", LOG_PREFIX, e);
				throw new PutFileException("upload part error the cause:", e);
			}
		});
	}

	/**
	 * 将源对象复制到分片上传的一部分
	 * @param arguments 部分上传复制请求参数实体 {@link UploadPartCopyArguments}
	 * @return 部分上传复制结果域对象 {@link UploadPartCopyDomain}
	 * @throws ObjectStorageException 上传分片拷贝异常
	 */
	@Override
	public UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) throws ObjectStorageException {
		return execute(oss -> {
			try {
				UploadPartCopyResult result = oss.uploadPartCopy(
						ConverterUtils.toTarget(arguments, new ArgumentsToUploadPartCopyRequestConverter()));
				return ConverterUtils.toTarget(result, new UploadPartCopyResultToDomainConverter());
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} upload part copy error the cause:", LOG_PREFIX, e);
				throw new PutFileException(e);
			}
		});
	}

	/**
	 * 通过组装以前上传的部分来完成分片上传。
	 * @param arguments 完成分片上传请求参数实体 {@link CompleteMultipartUploadArguments}
	 * @return 完成分片上传域对象 {@link CompleteMultipartUploadDomain}
	 * @throws ObjectStorageException 完成分片上传异常
	 */
	@Override
	public CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments)
			throws ObjectStorageException {
		return execute(oss -> {
			try {
				CompleteMultipartUploadResult result = oss.completeMultipartUpload(
						ConverterUtils.toTarget(arguments, new ArgumentsToCompleteMultipartUploadRequestConverter()));
				return ConverterUtils.toTarget(result, new CompleteMultipartUploadResultToDomainConverter());
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} complete multipart upload error the cause:", LOG_PREFIX, e);
				throw new PutFileException(e);
			}
		});
	}

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
	@Override
	public AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments)
			throws ObjectStorageException {
		return execute(oss -> {
			try {
				oss.abortMultipartUpload(
						ConverterUtils.toTarget(arguments, new ArgumentsToAbortMultipartUploadRequestConverter()));
				AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
				domain.setUploadId(arguments.getUploadId());
				domain.setObjectName(arguments.getObjectName());
				domain.setBucketName(arguments.getBucketName());
				return domain;
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} abort multipart upload error the cause:", LOG_PREFIX, e);
				throw new PutFileException(e);
			}
		});
	}

	/**
	 * 获取分片列表
	 * @param arguments 获取分片列表请求参数实体 {@link ListPartsArguments}
	 * @return 分片列表结果 {@link ListPartsDomain}
	 * @throws ObjectStorageException 罗列分片列表异常
	 */
	@Override
	public ListPartsDomain listParts(ListPartsArguments arguments) {
		return execute(oss -> {
			try {
				PartListing result = oss
					.listParts(ConverterUtils.toTarget(arguments, new ArgumentsToListPartsRequestConverter()));
				return ConverterUtils.toTarget(result, new PartListingToDomainConverter());
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} list parts error the cause:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

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
	@Override
	public ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments)
			throws ObjectStorageException {
		return execute(oss -> {
			try {
				MultipartUploadListing result = oss.listMultipartUploads(
						ConverterUtils.toTarget(arguments, new ArgumentsToListMultipartUploadsRequestConverter()));
				return ConverterUtils.toTarget(result, new MultipartUploadListingToDomainConverter());
			}
			catch (OSSException | ClientException e) {
				log.error("==>  {} list multipart uploads error the cause:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});
	}

}
