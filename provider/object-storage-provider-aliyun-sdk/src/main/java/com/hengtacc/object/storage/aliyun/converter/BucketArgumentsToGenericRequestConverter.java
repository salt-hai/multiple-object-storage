package com.hengtacc.object.storage.aliyun.converter;

import com.aliyun.oss.model.GenericRequest;
import com.hengtacc.object.storage.core.arguments.base.BucketArguments;

/**
 * 桶参数转基础请求
 *
 * @author Kuang HaiBo 2023/11/16 15:14
 */
public class BucketArgumentsToGenericRequestConverter<Source extends BucketArguments>
		extends BaseArgumentsToWebServiceRequestConverter<Source, GenericRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param bucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GenericRequest getInstance(BucketArguments bucketArguments) {
		return new GenericRequest(bucketArguments.getBucketName());
	}

}
