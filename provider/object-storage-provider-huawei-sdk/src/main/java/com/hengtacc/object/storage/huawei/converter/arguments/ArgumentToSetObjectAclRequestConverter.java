package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.object.SetObjectAclArguments;
import com.hengtacc.object.storage.huawei.acl.ObsAclConvert;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.SetObjectAclRequest;

/**
 * 设置acl请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/15 14:54
 */
public class ArgumentToSetObjectAclRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<SetObjectAclArguments, SetObjectAclRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param setObjectAclArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public SetObjectAclRequest getInstance(SetObjectAclArguments setObjectAclArguments) {
		SetObjectAclRequest request = new SetObjectAclRequest(setObjectAclArguments.getBucketName(),
				setObjectAclArguments.getObjectName(), ObsAclConvert.INSTANCE.convert(setObjectAclArguments.getAcl()));
		request.setVersionId(setObjectAclArguments.getVersionId());
		return request;
	}

}
