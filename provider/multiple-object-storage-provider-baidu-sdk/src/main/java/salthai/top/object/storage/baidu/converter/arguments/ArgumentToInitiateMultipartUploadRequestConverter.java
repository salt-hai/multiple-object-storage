package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.ObjectMetadata;
import salthai.top.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.InitiateMultipartUploadArguments;

/**
 * 转成bos初始化分片上传的转换器
 *
 * @author Kuang HaiBo 2023/12/6 16:38
 */
public class ArgumentToInitiateMultipartUploadRequestConverter extends
		ArgumentsToGenericObjectRequestConverter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param initiateMultipartUploadArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public InitiateMultipartUploadRequest getInstance(
			InitiateMultipartUploadArguments initiateMultipartUploadArguments) {

		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(
				initiateMultipartUploadArguments.getBucketName(), initiateMultipartUploadArguments.getObjectName());

		ObjectMetadata bosObjectMetadata = new ObjectMetadata();
		bosObjectMetadata.setContentType(initiateMultipartUploadArguments.getContentType());

		request.setObjectMetadata(bosObjectMetadata);
		return request;
	}

}
