package com.hengtacc.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.SetBucketAclRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.aliyun.converter.acl.OssAclConvert;
import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;

/**
 * 转换成 oss 设置bucket acl请求
 *
 * @author Kuang HaiBo 2023/11/16 15:31
 */
public class ArgumentsToSetBucketAclRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<SetBucketAclArguments, SetBucketAclRequest> {

	private static final OssAclConvert aclConvert = OssAclConvert.INSTANCE;

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public SetBucketAclRequest getInstance(SetBucketAclArguments arguments) {
		SetBucketAclRequest setBucketAclRequest = new SetBucketAclRequest(arguments.getBucketName());
		setBucketAclRequest.setCannedACL(aclConvert.convert(arguments.getAcl()));
		return setBucketAclRequest;
	}

}
