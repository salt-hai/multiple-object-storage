package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.object.GetObjectDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.obs.services.model.ObsObject;

import java.util.Map;

/**
 * obs对象 转领域对象转换器
 *
 * @author Kuang HaiBo 2023/12/15 10:59
 */
public class ObsObjectToGetObjectDomainConverter implements Converter<ObsObject, GetObjectDomain> {

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
	public GetObjectDomain convert(ObsObject source) {
		GetObjectDomain domain = new GetObjectDomain();
		domain.setObjectName(source.getObjectKey());
		domain.setBucketName(source.getObjectKey());
		domain.setObjectContent(source.getObjectContent());
		Map<String, Object> responseHeaders = source.getMetadata().getResponseHeaders();
		domain.setHeader(responseHeaders);
		return domain;
	}

}
