package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.object.ListObjectsDomain;
import com.hengtacc.object.storage.core.domain.object.ObjectDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.hengtacc.object.storage.core.utils.ConverterUtils;
import com.hengtacc.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;

/**
 * 列举对象结果转换器
 *
 * @author Kuang HaiBo 2023/12/15 11:16
 */
public class ObjectListingToDomainConverter
		extends HeaderResponseToBaseDomainConverter<ObjectListing, ListObjectsDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param objectListing 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsDomain getInstance(ObjectListing objectListing) {
		ListObjectsDomain domain = new ListObjectsDomain();
		domain.setBucketName(objectListing.getBucketName());
		domain.setTruncated(objectListing.isTruncated());
		domain.setCommonPrefixes(objectListing.getCommonPrefixes());
		domain.setPrefix(objectListing.getPrefix());
		domain.setMarker(objectListing.getMarker());
		domain.setDelimiter(objectListing.getDelimiter());
		domain.setMaxKeys(objectListing.getMaxKeys());
		domain.setNextMarker(objectListing.getNextMarker());
		domain.setSummaries(ConverterUtils.toTargets(objectListing.getObjects(), new ObsObjectToDomainConverter()));
		return domain;
	}

}
