package com.hengtacc.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.CreateBucketRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;

/**
 * 创建bucket 请求转换器
 *
 * @author Kuang HaiBo 2023/11/16 11:38
 */
public class ArgumentsToCreateBucketRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<CreateBucketArguments, CreateBucketRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param createBucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CreateBucketRequest getInstance(CreateBucketArguments createBucketArguments) {
		return new CreateBucketRequest(createBucketArguments.getBucketName());
	}

}
