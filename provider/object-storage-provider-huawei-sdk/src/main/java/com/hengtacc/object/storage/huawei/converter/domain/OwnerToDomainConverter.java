package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.base.OwnerDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.obs.services.model.Owner;

/**
 * 所有者转换器
 *
 * @author Kuang HaiBo 2023/12/19 9:46
 */
public class OwnerToDomainConverter implements Converter<Owner, OwnerDomain> {

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
	public OwnerDomain convert(Owner source) {
		OwnerDomain ownerDomain = new OwnerDomain();
		ownerDomain.setDisplayName(source.getDisplayName());
		ownerDomain.setId(source.getId());
		return ownerDomain;
	}

}
