package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.PartSummary;
import com.hengtacc.object.storage.core.domain.multipart.PartSummaryDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 分片信息转分片领域
 *
 * @author HaiBo Kuang 2023/11/19 15:24
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
		PartSummaryDomain summaryDomain = new PartSummaryDomain();
		summaryDomain.setEtag(source.getETag());
		summaryDomain.setPartNumber(source.getPartNumber());
		summaryDomain.setPartSize(source.getSize());
		summaryDomain.setLastModifiedDate(source.getLastModified());
		return summaryDomain;
	}

}
