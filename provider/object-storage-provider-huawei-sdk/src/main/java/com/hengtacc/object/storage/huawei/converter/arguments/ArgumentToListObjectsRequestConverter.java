package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.object.ListObjectsArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.ListObjectsRequest;

/**
 * 列举对象请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/15 11:08
 */
public class ArgumentToListObjectsRequestConverter
		extends ArgumentsToGenericRequestConverter<ListObjectsArguments, ListObjectsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsRequest getInstance(ListObjectsArguments arguments) {
		ListObjectsRequest request = new ListObjectsRequest();
		request.setBucketName(arguments.getBucketName());
		request.setDelimiter(arguments.getDelimiter());
		request.setMarker(arguments.getMarker());
		request.setMaxKeys(arguments.getMaxKeys());
		request.setPrefix(arguments.getPrefix());
		request.setEncodingType(arguments.getEncodingType());
		return request;
	}

}
