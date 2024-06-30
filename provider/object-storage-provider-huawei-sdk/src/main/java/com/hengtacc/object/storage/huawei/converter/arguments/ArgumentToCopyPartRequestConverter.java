package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.multipart.UploadPartCopyArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.CopyPartRequest;

/**
 * 分片拷贝 请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 14:04
 */
public class ArgumentToCopyPartRequestConverter
		extends ArgumentsToGenericRequestConverter<UploadPartCopyArguments, CopyPartRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CopyPartRequest getInstance(UploadPartCopyArguments arguments) {
		CopyPartRequest copyPartRequest = new CopyPartRequest();
		copyPartRequest.setPartNumber(arguments.getPartNumber());
		copyPartRequest.setDestinationBucketName(arguments.getBucketName());
		copyPartRequest.setDestinationObjectKey(arguments.getObjectName());
		copyPartRequest.setBucketName(arguments.getSourceBucketName());
		copyPartRequest.setSourceObjectKey(arguments.getSourceObjectName());
		copyPartRequest.setVersionId(arguments.getSourceObjectVersionId());
		copyPartRequest.setUploadId(arguments.getUploadId());
		return copyPartRequest;
	}

}
