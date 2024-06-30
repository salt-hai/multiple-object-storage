package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.model.User;
import com.hengtacc.object.storage.core.domain.base.OwnerDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * bos user 转为对象所有者
 *
 * @author Kuang HaiBo 2023/12/11 11:37
 */
public class UserToOwnerDomainConverter implements Converter<User, OwnerDomain> {

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
	public OwnerDomain convert(User source) {
		OwnerDomain ownerDomain = new OwnerDomain();
		ownerDomain.setId(source.getId());
		ownerDomain.setDisplayName(source.getDisplayName());
		return ownerDomain;
	}

}
