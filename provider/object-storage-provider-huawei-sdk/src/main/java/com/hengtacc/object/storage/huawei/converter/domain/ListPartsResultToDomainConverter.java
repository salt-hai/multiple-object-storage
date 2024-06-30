package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.multipart.ListPartsDomain;
import com.hengtacc.object.storage.core.utils.ConverterUtils;
import com.hengtacc.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.ListPartsResult;

import java.util.Objects;

/**
 * 列举分片 结果转化器
 *
 * @author Kuang HaiBo 2023/12/20 17:21
 */
public class ListPartsResultToDomainConverter
		extends HeaderResponseToBaseDomainConverter<ListPartsResult, ListPartsDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param listPartsResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListPartsDomain getInstance(ListPartsResult listPartsResult) {
		ListPartsDomain partsDomain = new ListPartsDomain();
		partsDomain.setMaxParts(listPartsResult.getMaxParts());
		partsDomain.setStorageClass(listPartsResult.getObjectStorageClass().getCode());

		partsDomain.setNextPartNumberMarker(Integer.valueOf(listPartsResult.getNextPartNumberMarker()));
		partsDomain.setPartNumberMarker(Integer.valueOf(listPartsResult.getPartNumberMarker()));

		partsDomain.setTruncated(listPartsResult.isTruncated());
		partsDomain.setUploadId(listPartsResult.getUploadId());
		partsDomain
			.setParts(ConverterUtils.toTargets(listPartsResult.getMultipartList(), new MultipartToDomainConverter()));

		if (Objects.nonNull(listPartsResult.getOwner())) {
			partsDomain.setOwner(ConverterUtils.toTarget(listPartsResult.getOwner(), new OwnerToDomainConverter()));
		}
		if (Objects.nonNull(listPartsResult.getInitiator())) {
			partsDomain
				.setInitiator(ConverterUtils.toTarget(listPartsResult.getInitiator(), new OwnerToDomainConverter()));
		}
		return partsDomain;
	}

}
