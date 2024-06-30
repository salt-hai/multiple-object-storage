package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.PutObjectResponse;
import com.hengtacc.object.storage.core.domain.object.PutObjectDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 上传对象结果 转domain
 *
 * @author Kuang HaiBo 2023/11/14 10:28
 */
public class PutObjectResponseToDomainConverter implements Converter<PutObjectResponse, PutObjectDomain> {

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
	public PutObjectDomain convert(PutObjectResponse source) {
		PutObjectDomain putObjectDomain = new PutObjectDomain();
		putObjectDomain.setEtag(source.getETag());
		return putObjectDomain;
	}

}
