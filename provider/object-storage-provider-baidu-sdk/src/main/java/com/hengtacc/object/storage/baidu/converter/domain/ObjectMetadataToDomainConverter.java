package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.ObjectMetadata;
import com.hengtacc.object.storage.core.domain.object.ObjectMetadataDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * bos 元数据转domain
 *
 * @author Kuang HaiBo 2023/11/14 13:53
 */
public class ObjectMetadataToDomainConverter implements Converter<ObjectMetadata, ObjectMetadataDomain> {

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
	public ObjectMetadataDomain convert(ObjectMetadata source) {
		ObjectMetadataDomain domain = new ObjectMetadataDomain();
		domain.setContentLength(source.getContentLength());
		domain.setUserMetadata(source.getUserMetadata());
		domain.setContentType(source.getContentType());
		domain.setEtag(source.getETag());
		domain.setLastModified(source.getLastModified());
		return domain;
	}

}
