package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.PartSummary;
import com.hengtacc.object.storage.core.domain.multipart.PartSummaryDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * bos 分片详细转对象
 *
 * @author Kuang HaiBo 2023/12/11 11:34
 */
public class PartSummaryToDomainConverter implements Converter<PartSummary, PartSummaryDomain> {

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
	public PartSummaryDomain convert(PartSummary source) {
		PartSummaryDomain partSummaryDomain = new PartSummaryDomain();
		partSummaryDomain.setPartNumber(source.getPartNumber());
		partSummaryDomain.setPartSize(source.getSize());
		partSummaryDomain.setEtag(source.getETag());
		partSummaryDomain.setLastModifiedDate(source.getLastModified());
		return partSummaryDomain;
	}

}
