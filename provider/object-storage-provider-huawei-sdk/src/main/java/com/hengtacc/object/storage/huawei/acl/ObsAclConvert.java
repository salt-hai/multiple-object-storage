package com.hengtacc.object.storage.huawei.acl;

import com.hengtacc.object.storage.core.acl.AccessControlListConvert;
import com.obs.services.internal.IConvertor;
import com.obs.services.internal.ObsConvertor;
import com.obs.services.model.AccessControlList;

/**
 * 华为 obs acl 转换器
 *
 * @author Kuang HaiBo 2023/11/7 17:54
 */
public class ObsAclConvert implements AccessControlListConvert<AccessControlList> {

	public static final ObsAclConvert INSTANCE = new ObsAclConvert();

	/**
	 * obs 自身转换
	 */
	private static final IConvertor OBS_CONVERTOR = ObsConvertor.getInstance();

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source the source object to convert, which must be an instance of {@code S}
	 * (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public AccessControlList convert(com.hengtacc.object.storage.core.acl.AccessControlList source) {
		return OBS_CONVERTOR.transCannedAcl(source.getCannedAclString());
	}

}
