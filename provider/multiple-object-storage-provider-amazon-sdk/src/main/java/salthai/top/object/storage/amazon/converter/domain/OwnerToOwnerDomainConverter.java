package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.core.domain.base.OwnerDomain;
import salthai.top.object.storage.core.function.Converter;
import software.amazon.awssdk.services.s3.model.Owner;

/**
 * aws {@link Owner} To {@link OwnerDomain}
 *
 * @author Kuang HaiBo 2024/7/10 15:21
 */
public class OwnerToOwnerDomainConverter implements Converter<Owner, OwnerDomain> {

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
		ownerDomain.setId(source.id());
		ownerDomain.setDisplayName(source.displayName());
		return ownerDomain;
	}

}
