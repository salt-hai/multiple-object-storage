package com.hengtacc.object.storage.aliyun.converter.arguments;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.model.SetObjectAclRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.aliyun.converter.acl.OssAclConvert;
import com.hengtacc.object.storage.core.arguments.object.SetObjectAclArguments;

/**
 * 设置 对象acl
 *
 * @author Kuang HaiBo 2023/11/8 14:47
 */
public class ArgumentsToSetObjectAclRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<SetObjectAclArguments, SetObjectAclRequest> {

	private final static OssAclConvert ACL_CONVERT = OssAclConvert.INSTANCE;

	/**
	 * 获取最终生成对象实例
	 * @param setObjectAclArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public SetObjectAclRequest getInstance(SetObjectAclArguments setObjectAclArguments) {
		SetObjectAclRequest request = new SetObjectAclRequest(setObjectAclArguments.getBucketName(),
				setObjectAclArguments.getObjectName());
		request.setCannedACL(ACL_CONVERT.convert(setObjectAclArguments.getAcl()));
		if (StrUtil.isNotBlank(setObjectAclArguments.getVersionId())) {
			request.setVersionId(setObjectAclArguments.getVersionId());
		}
		return request;
	}

}
