package salthai.top.object.storage.baidu.operations;

import com.baidubce.BceClientException;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListMultipartUploadsResponse;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.UploadPartCopyResponse;
import com.baidubce.services.bos.model.UploadPartResponse;
import salthai.top.object.storage.baidu.BaseBosOperations;
import salthai.top.object.storage.baidu.BosConstants;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToAbortMultipartUploadRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToCompleteMultipartUploadRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToInitiateMultipartUploadRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToListMultipartUploadsRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToListPartsRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToUploadPartCopyRequestConverter;
import salthai.top.object.storage.baidu.converter.arguments.ArgumentToUploadPartRequestConverter;
import salthai.top.object.storage.baidu.converter.domain.CompleteMultipartUploadResponseToDomainConverter;
import salthai.top.object.storage.baidu.converter.domain.InitiateMultipartUploadResponseToDomainConverter;
import salthai.top.object.storage.baidu.converter.domain.ListMultipartUploadsResponseToDomainConverter;
import salthai.top.object.storage.baidu.converter.domain.ListPartsResponseToDomainConverter;
import salthai.top.object.storage.baidu.converter.domain.UploadPartCopyResponseToDomainConverter;
import salthai.top.object.storage.baidu.converter.domain.UploadPartResponseToDomainConverter;
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
import salthai.top.object.storage.core.operations.ObjectMultipartOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 百度bos 分片操作
 *
 * @author Kuang HaiBo 2023/11/20 17:44
 */
public class BosObjectMultipartOperations extends BaseBosOperations implements ObjectMultipartOperations {

	private final static String LOG_PREFIX = BosConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(BosObjectMultipartOperations.class);

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BosObjectMultipartOperations(ProviderClientManager<BosClient> clientManager) {
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
		return execute(bosClient -> {
			try {
				InitiateMultipartUploadResponse multipartUploadResponse = bosClient.initiateMultipartUpload(
						ConverterUtils.toTarget(arguments, new ArgumentToInitiateMultipartUploadRequestConverter()));
				return ConverterUtils.toTarget(multipartUploadResponse,
						new InitiateMultipartUploadResponseToDomainConverter());
			}
			catch (BceClientException bceClientException) {
				log.error("==> {} initiate multipart upload error the cause:", LOG_PREFIX, bceClientException);
				throw new PutFileException("initiate multipart upload error the cause:", bceClientException);
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
		return execute(bosClient -> {
			try {
				UploadPartResponse uploadPartResponse = bosClient
					.uploadPart(ConverterUtils.toTarget(arguments, new ArgumentToUploadPartRequestConverter()));
				// 转为domain
				UploadPartDomain domain = ConverterUtils.toTarget(uploadPartResponse,
						new UploadPartResponseToDomainConverter());
				domain.setPartSize(arguments.getPartSize());
				return domain;
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				UploadPartCopyResponse uploadPartCopyResponse = bosClient
					.uploadPartCopy(ConverterUtils.toTarget(arguments, new ArgumentToUploadPartCopyRequestConverter()));
				UploadPartCopyDomain domain = ConverterUtils.toTarget(uploadPartCopyResponse,
						new UploadPartCopyResponseToDomainConverter());
				domain.setPartSize(arguments.getPartSize());
				return domain;
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				CompleteMultipartUploadResponse response = bosClient.completeMultipartUpload(
						ConverterUtils.toTarget(arguments, new ArgumentToCompleteMultipartUploadRequestConverter()));
				return ConverterUtils.toTarget(response, new CompleteMultipartUploadResponseToDomainConverter());
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				bosClient.abortMultipartUpload(
						ConverterUtils.toTarget(arguments, new ArgumentToAbortMultipartUploadRequestConverter()));
				AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
				domain.setUploadId(arguments.getUploadId());
				domain.setObjectName(arguments.getObjectName());
				domain.setBucketName(arguments.getBucketName());
				return domain;
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				ListPartsResponse listPartsResponse = bosClient
					.listParts(ConverterUtils.toTarget(arguments, new ArgumentToListPartsRequestConverter()));
				return ConverterUtils.toTarget(listPartsResponse, new ListPartsResponseToDomainConverter());
			}
			catch (BceClientException e) {
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
		return execute(bosClient -> {
			try {
				ListMultipartUploadsResponse listMultipartUploadsResponse = bosClient.listMultipartUploads(
						ConverterUtils.toTarget(arguments, new ArgumentToListMultipartUploadsRequestConverter()));
				return ConverterUtils.toTarget(listMultipartUploadsResponse,
						new ListMultipartUploadsResponseToDomainConverter());
			}
			catch (BceClientException e) {
				log.error("==>  {} list multipart uploads error the cause:", LOG_PREFIX, e);
				throw new GetFileException(e);
			}
		});

	}

}
