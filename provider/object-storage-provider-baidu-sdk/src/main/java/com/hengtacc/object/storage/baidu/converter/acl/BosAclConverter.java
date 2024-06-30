package com.hengtacc.object.storage.baidu.converter.acl;

import com.baidubce.services.bos.model.CannedAccessControlList;
import com.hengtacc.object.storage.core.acl.AccessControlList;
import com.hengtacc.object.storage.core.acl.AccessControlListConvert;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度通用acl 转换
 *
 * @author Kuang HaiBo 2023/11/13 15:03
 */
public class BosAclConverter implements AccessControlListConvert<CannedAccessControlList> {

	public final static BosAclConverter INSTANCE = new BosAclConverter();
	static final Map<String, CannedAccessControlList> BOS_ACL_MAPPINGS;

	static {
		BOS_ACL_MAPPINGS = new HashMap<>();
		BOS_ACL_MAPPINGS.put(AccessControlList.PublicRead.getCannedAclString(), CannedAccessControlList.PublicRead);
		BOS_ACL_MAPPINGS.put(AccessControlList.Private.getCannedAclString(), CannedAccessControlList.Private);
		BOS_ACL_MAPPINGS.put(AccessControlList.PublicReadWrite.getCannedAclString(),
				CannedAccessControlList.PublicReadWrite);
	}

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
	public CannedAccessControlList convert(AccessControlList source) {
		return BOS_ACL_MAPPINGS.get(source.getCannedAclString());
	}

}
