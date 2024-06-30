package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.bucket.DelBucketArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.BaseBucketRequest;

/**
 * 删除bucket请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/19 10:59
 */
public class ArgumentToDeleteBucketRequestConverter
		extends ArgumentsToGenericRequestConverter<DelBucketArguments, BaseBucketRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param delBucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public BaseBucketRequest getInstance(DelBucketArguments delBucketArguments) {
		BaseBucketRequest baseBucketRequest = new BaseBucketRequest();
		baseBucketRequest.setBucketName(delBucketArguments.getBucketName());
		return baseBucketRequest;
	}

}
