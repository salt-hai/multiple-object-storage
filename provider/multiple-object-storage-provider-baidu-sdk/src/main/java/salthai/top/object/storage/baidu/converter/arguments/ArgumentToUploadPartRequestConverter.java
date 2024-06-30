package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.UploadPartRequest;
import salthai.top.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.UploadPartArguments;

/**
 * 转换成 bos 分片上传请求
 *
 * @author Kuang HaiBo 2023/12/6 17:03
 */
public class ArgumentToUploadPartRequestConverter
		extends ArgumentsToGenericObjectRequestConverter<UploadPartArguments, UploadPartRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadPartArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public UploadPartRequest getInstance(UploadPartArguments uploadPartArguments) {
		UploadPartRequest request = new UploadPartRequest();
		request.setUploadId(uploadPartArguments.getUploadId());
		request.setPartNumber(uploadPartArguments.getPartNumber());
		request.setPartSize(uploadPartArguments.getPartSize());
		request.setInputStream(uploadPartArguments.getInputStream());
		request.setMd5Digest(uploadPartArguments.getMd5Digest());
		request.setBucketName(uploadPartArguments.getBucketName());
		request.setKey(uploadPartArguments.getObjectName());
		return request;
	}

}
