package salthai.top.object.storage.aliyun.converter.arguments;

import cn.hutool.core.map.MapUtil;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.ObjectMetadata;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.InitiateMultipartUploadArguments;

/**
 * 初始化分片请求参数装换
 *
 * @author Kuang HaiBo 2023/11/17 14:28
 */
public class ArgumentsToInitiateMultipartUploadRequestConverter extends
		BaseArgumentsToWebServiceRequestConverter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public InitiateMultipartUploadRequest getInstance(InitiateMultipartUploadArguments arguments) {
		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(arguments.getBucketName(),
				arguments.getObjectName());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arguments.getContentType());
		if (MapUtil.isNotEmpty(arguments.getMetadata())) {
			arguments.getMetadata().forEach(objectMetadata::setHeader);
		}
		request.setObjectMetadata(objectMetadata);
		return request;
	}

}
