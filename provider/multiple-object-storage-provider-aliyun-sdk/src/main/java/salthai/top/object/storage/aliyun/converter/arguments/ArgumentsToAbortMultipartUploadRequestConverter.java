package salthai.top.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.AbortMultipartUploadRequest;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.AbortMultipartUploadArguments;

/**
 * 取消分片请求参数参数转为oss 取消分片参数
 *
 * @author HaiBo Kuang 2023/11/19 14:24
 */
public class ArgumentsToAbortMultipartUploadRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public AbortMultipartUploadRequest getInstance(AbortMultipartUploadArguments arguments) {
		return new AbortMultipartUploadRequest(arguments.getBucketName(), arguments.getBucketName(),
				arguments.getUploadId());
	}

}
