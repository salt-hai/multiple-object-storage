package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.ListObjectsResponse;
import com.hengtacc.object.storage.core.domain.object.ListObjectsDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.hengtacc.object.storage.core.utils.ConverterUtils;

/**
 * 列举对象结果转换器
 *
 * @author HaiBo Kuang 2023/11/19 16:41
 */
public class ListObjectsResponseToDomainConverter implements Converter<ListObjectsResponse, ListObjectsDomain> {

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
	public ListObjectsDomain convert(ListObjectsResponse source) {
		ListObjectsDomain listObjectsDomain = new ListObjectsDomain();
		listObjectsDomain.setDelimiter(source.getDelimiter());
		listObjectsDomain.setBucketName(source.getBucketName());
		listObjectsDomain.setMarker(source.getMarker());
		listObjectsDomain.setMaxKeys(source.getMaxKeys());
		listObjectsDomain.setTruncated(source.isTruncated());
		listObjectsDomain.setNextMarker(source.getNextMarker());
		listObjectsDomain.setCommonPrefixes(source.getCommonPrefixes());
		listObjectsDomain
			.setSummaries(ConverterUtils.toTargets(source.getContents(), new BosObjectSummaryToDomainConverter()));
		return null;
	}

}
