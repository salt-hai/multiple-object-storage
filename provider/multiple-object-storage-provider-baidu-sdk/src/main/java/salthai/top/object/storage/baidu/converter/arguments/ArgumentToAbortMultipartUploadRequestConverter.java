package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.AbortMultipartUploadRequest;
import salthai.top.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.AbortMultipartUploadArguments;

/**
 * 关闭分片上传装换器
 *
 * @author Kuang HaiBo 2023/12/8 10:13
 */
public class ArgumentToAbortMultipartUploadRequestConverter
		extends ArgumentsToGenericObjectRequestConverter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param abortMultipartUploadArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public AbortMultipartUploadRequest getInstance(AbortMultipartUploadArguments abortMultipartUploadArguments) {
		return new AbortMultipartUploadRequest(abortMultipartUploadArguments.getBucketName(),
				abortMultipartUploadArguments.getObjectName(), abortMultipartUploadArguments.getUploadId());
	}

}
