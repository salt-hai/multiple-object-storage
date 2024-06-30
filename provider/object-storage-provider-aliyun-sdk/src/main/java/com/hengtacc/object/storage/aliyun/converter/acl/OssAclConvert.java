package com.hengtacc.object.storage.aliyun.converter.acl;

import com.aliyun.oss.model.CannedAccessControlList;
import com.hengtacc.object.storage.core.acl.AccessControlList;
import com.hengtacc.object.storage.core.acl.AccessControlListConvert;

/**
 * 阿里云 oss acl转换器
 *
 * @author Kuang HaiBo 2023/11/7 17:51
 */
public class OssAclConvert implements AccessControlListConvert<CannedAccessControlList> {

	public static final OssAclConvert INSTANCE = new OssAclConvert();

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
		return CannedAccessControlList.parse(source.getCannedAclString());
	}

}
