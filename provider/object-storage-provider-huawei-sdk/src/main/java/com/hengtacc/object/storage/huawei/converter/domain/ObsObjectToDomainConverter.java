package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.object.ObjectDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.obs.services.model.ObsObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * obs 对象装换方法
 *
 * @author Kuang HaiBo 2023/12/15 13:58
 */
public class ObsObjectToDomainConverter implements Converter<ObsObject, ObjectDomain> {

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
	public ObjectDomain convert(ObsObject source) {
		try (InputStream objectContent = source.getObjectContent()) {
			ObjectDomain objectDomain = new ObjectDomain();
			objectDomain.setObjectName(source.getObjectKey());
			objectDomain.setBucketName(source.getBucketName());
			objectDomain.setETag(source.getMetadata().getEtag());
			objectDomain.setStorageClass(source.getMetadata().getObjectStorageClass().getCode());
			objectDomain.setSize(source.getMetadata().getContentLength());
			return objectDomain;
		}
		catch (IOException e) {
			return null;
		}
	}

}
