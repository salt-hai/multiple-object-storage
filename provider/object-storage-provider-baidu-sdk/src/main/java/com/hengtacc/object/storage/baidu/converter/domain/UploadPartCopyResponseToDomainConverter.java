package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.UploadPartCopyResponse;
import com.hengtacc.object.storage.core.domain.multipart.UploadPartCopyDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 分片拷贝转domain
 *
 * @author Kuang HaiBo 2023/12/7 16:43
 */
public class UploadPartCopyResponseToDomainConverter
		implements Converter<UploadPartCopyResponse, UploadPartCopyDomain> {

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
	public UploadPartCopyDomain convert(UploadPartCopyResponse source) {
		UploadPartCopyDomain domain = new UploadPartCopyDomain();
		domain.setEtag(source.getETag());
		domain.setPartNumber(source.getPartNumber());
		return domain;
	}

}
