package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.multipart.ListMultipartUploadsArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.ListMultipartUploadsRequest;

/**
 * 列出正在进行的分片上传参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 17:32
 */
public class ArgumentToListMultipartUploadsRequestConverter
		extends ArgumentsToGenericRequestConverter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListMultipartUploadsRequest getInstance(ListMultipartUploadsArguments arguments) {
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest();
		request.setDelimiter(arguments.getDelimiter());
		request.setMaxUploads(arguments.getMaxUploads());
		request.setPrefix(arguments.getPrefix());
		request.setUploadIdMarker(arguments.getUploadIdMarker());
		request.setEncodingType(arguments.getEncodingType());
		request.setKeyMarker(arguments.getKeyMarker());
		request.setBucketName(arguments.getBucketName());
		return request;
	}

}
