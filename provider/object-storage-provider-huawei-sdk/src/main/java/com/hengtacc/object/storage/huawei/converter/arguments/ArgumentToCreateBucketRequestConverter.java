package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.CreateBucketRequest;

/**
 * 创建 bucket 参数转换器
 *
 * @author Kuang HaiBo 2023/12/19 10:15
 */
public class ArgumentToCreateBucketRequestConverter
		extends ArgumentsToGenericRequestConverter<CreateBucketArguments, CreateBucketRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param createBucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CreateBucketRequest getInstance(CreateBucketArguments createBucketArguments) {
		CreateBucketRequest createBucketRequest = new CreateBucketRequest();
		createBucketRequest.setBucketName(createBucketArguments.getBucketName());
		return createBucketRequest;
	}

}
