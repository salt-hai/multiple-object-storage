package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.multipart.ListPartsArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.ListPartsRequest;

/**
 * 列举分片参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 17:10
 */
public class ArgumentToListPartsRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<ListPartsArguments, ListPartsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param listPartsArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListPartsRequest getInstance(ListPartsArguments listPartsArguments) {
		ListPartsRequest request = new ListPartsRequest();
		request.setUploadId(listPartsArguments.getUploadId());
		request.setPartNumberMarker(listPartsArguments.getPartNumberMarker());
		request.setMaxParts(listPartsArguments.getMaxParts());
		return request;
	}

}
