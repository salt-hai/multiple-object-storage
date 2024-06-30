package com.hengtacc.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectRequest;
import com.hengtacc.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import com.hengtacc.object.storage.core.arguments.object.PutObjectArguments;

/**
 * 转成 bos 上传请求
 *
 * @author Kuang HaiBo 2023/11/13 15:45
 */
public class ArgumentsToPutObjectRequestConverter
		extends ArgumentsToGenericObjectRequestConverter<PutObjectArguments, PutObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param putObjectArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutObjectRequest getInstance(PutObjectArguments putObjectArguments) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(putObjectArguments.getBucketName(),
				putObjectArguments.getObjectName(), putObjectArguments.getInputStream());

		ObjectMetadata objectMetadata = new ObjectMetadata();

		objectMetadata.setContentType(putObjectArguments.getContentType());
		objectMetadata.setContentLength(putObjectArguments.getObjectSize());

		putObjectRequest.setObjectMetadata(objectMetadata);

		return putObjectRequest;
	}

}
