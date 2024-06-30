package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.PartListing;
import com.hengtacc.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import com.hengtacc.object.storage.core.domain.multipart.ListPartsDomain;
import com.hengtacc.object.storage.core.utils.ConverterUtils;

/**
 * 分片列表结果转换
 *
 * @author HaiBo Kuang 2023/11/19 15:06
 */
public class PartListingToDomainConverter
		extends GenericResultToBaseResponseDomainConverter<PartListing, ListPartsDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param partListing 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListPartsDomain getInstance(PartListing partListing) {
		ListPartsDomain listPartsDomain = new ListPartsDomain();
		listPartsDomain.setBucketName(partListing.getBucketName());
		listPartsDomain.setMaxParts(partListing.getMaxParts());
		listPartsDomain.setPartNumberMarker(partListing.getPartNumberMarker());
		listPartsDomain.setNextPartNumberMarker(partListing.getNextPartNumberMarker());
		listPartsDomain.setStorageClass(partListing.getStorageClass());
		listPartsDomain.setObjectName(partListing.getKey());
		listPartsDomain.setTruncated(partListing.isTruncated());
		listPartsDomain.setUploadId(partListing.getUploadId());
		listPartsDomain.setParts(ConverterUtils.toTargets(partListing.getParts(), new PartSummaryToDomainConverter()));
		return listPartsDomain;

	}

}
