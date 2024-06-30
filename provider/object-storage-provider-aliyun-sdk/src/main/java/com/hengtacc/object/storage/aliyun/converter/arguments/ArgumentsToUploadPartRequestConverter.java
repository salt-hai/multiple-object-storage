package com.hengtacc.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.UploadPartRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.core.arguments.multipart.UploadPartArguments;

/**
 * 统一分片上传参数转 oss 上传参数
 *
 * @author Kuang HaiBo 2023/11/17 15:21
 */
public class ArgumentsToUploadPartRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<UploadPartArguments, UploadPartRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadPartArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public UploadPartRequest getInstance(UploadPartArguments uploadPartArguments) {
		UploadPartRequest request = new UploadPartRequest(uploadPartArguments.getBucketName(),
				uploadPartArguments.getObjectName());
		request.setUploadId(uploadPartArguments.getUploadId());
		request.setPartNumber(uploadPartArguments.getPartNumber());
		request.setPartSize(uploadPartArguments.getPartSize());
		request.setInputStream(uploadPartArguments.getInputStream());
		request.setMd5Digest(uploadPartArguments.getMd5Digest());
		return request;
	}

}
