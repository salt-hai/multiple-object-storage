package com.hengtacc.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.UploadPartCopyRequest;
import com.hengtacc.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import com.hengtacc.object.storage.core.arguments.multipart.UploadPartCopyArguments;

/**
 * 转为分片拷贝
 *
 * @author Kuang HaiBo 2023/12/6 17:34
 */
public class ArgumentToUploadPartCopyRequestConverter
		extends ArgumentsToGenericObjectRequestConverter<UploadPartCopyArguments, UploadPartCopyRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadPartCopyArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public UploadPartCopyRequest getInstance(UploadPartCopyArguments uploadPartCopyArguments) {
		UploadPartCopyRequest copyRequest = new UploadPartCopyRequest();

		copyRequest.setUploadId(uploadPartCopyArguments.getUploadId());
		copyRequest.setPartNumber(uploadPartCopyArguments.getPartNumber());
		copyRequest.setPartSize(uploadPartCopyArguments.getPartSize());
		// 目标
		copyRequest.setBucketName(uploadPartCopyArguments.getBucketName());
		copyRequest.setKey(uploadPartCopyArguments.getObjectName());

		// 来源
		copyRequest.setSourceBucketName(uploadPartCopyArguments.getSourceBucketName());
		copyRequest.setSourceKey(uploadPartCopyArguments.getSourceObjectName());

		return copyRequest;
	}

}
