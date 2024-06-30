package com.hengtacc.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.ListObjectsRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.core.arguments.object.ListObjectsArguments;

/**
 * 转为 list 请求参数
 *
 * @author Kuang HaiBo 2023/11/15 10:15
 */
public class ArgumentsToListObjectsRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<ListObjectsArguments, ListObjectsRequest> {

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
		request.setMaxKeys(arguments.getMaxKeys());
		request.setPrefix(arguments.getPrefix());
		request.setEncodingType(arguments.getEncodingType());
		return request;
	}

}
