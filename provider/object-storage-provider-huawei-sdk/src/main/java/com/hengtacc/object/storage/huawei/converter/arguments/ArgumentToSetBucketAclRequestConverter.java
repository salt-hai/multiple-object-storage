package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;
import com.hengtacc.object.storage.huawei.acl.ObsAclConvert;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.SetBucketAclRequest;

/**
 * 设置 bucket acl 请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/19 10:33
 */
public class ArgumentToSetBucketAclRequestConverter
		extends ArgumentsToGenericRequestConverter<SetBucketAclArguments, SetBucketAclRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param aclArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public SetBucketAclRequest getInstance(SetBucketAclArguments aclArguments) {
		SetBucketAclRequest aclRequest = new SetBucketAclRequest();
		aclRequest.setBucketName(aclArguments.getBucketName());
		aclRequest.setAcl(ObsAclConvert.INSTANCE.convert(aclArguments.getAcl()));
		return aclRequest;
	}

}
