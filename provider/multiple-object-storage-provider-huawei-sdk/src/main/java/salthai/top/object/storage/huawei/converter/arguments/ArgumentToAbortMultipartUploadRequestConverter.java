package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.multipart.AbortMultipartUploadArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.AbortMultipartUploadRequest;

/**
 * 终止分片请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 16:19
 */
public class ArgumentToAbortMultipartUploadRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public AbortMultipartUploadRequest getInstance(AbortMultipartUploadArguments uploadArguments) {
		AbortMultipartUploadRequest request = new AbortMultipartUploadRequest();
		request.setUploadId(uploadArguments.getUploadId());
		return request;
	}

}
