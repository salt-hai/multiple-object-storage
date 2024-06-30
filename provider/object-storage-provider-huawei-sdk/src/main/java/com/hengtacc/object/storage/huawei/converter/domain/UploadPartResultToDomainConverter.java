package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.multipart.UploadPartDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.obs.services.model.UploadPartResult;

/**
 * 分片上传结果转换器
 *
 * @author Kuang HaiBo 2023/12/20 10:31
 */
public class UploadPartResultToDomainConverter implements Converter<UploadPartResult, UploadPartDomain> {

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
	public UploadPartDomain convert(UploadPartResult source) {
		UploadPartDomain uploadPartDomain = new UploadPartDomain();
		uploadPartDomain.setPartNumber(source.getPartNumber());
		uploadPartDomain.setEtag(source.getEtag());
		return uploadPartDomain;
	}

}
