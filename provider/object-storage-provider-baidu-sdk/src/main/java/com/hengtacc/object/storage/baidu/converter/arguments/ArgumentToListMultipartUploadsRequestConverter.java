package com.hengtacc.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.ListMultipartUploadsRequest;
import com.hengtacc.object.storage.baidu.converter.ArgumentsToGenericBucketRequestConverter;
import com.hengtacc.object.storage.core.arguments.multipart.ListMultipartUploadsArguments;

/**
 * 业务对象转为bos列举正在分片上传请求参数
 *
 * @author Kuang HaiBo 2023/12/11 14:43
 */
public class ArgumentToListMultipartUploadsRequestConverter
		extends ArgumentsToGenericBucketRequestConverter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param listMultipartUploadsArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListMultipartUploadsRequest getInstance(ListMultipartUploadsArguments listMultipartUploadsArguments) {
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(
				listMultipartUploadsArguments.getBucketName());
		request.setPrefix(listMultipartUploadsArguments.getPrefix());
		request.setDelimiter(listMultipartUploadsArguments.getDelimiter());
		request.setMaxUploads(listMultipartUploadsArguments.getMaxUploads());
		request.setKeyMarker(listMultipartUploadsArguments.getKeyMarker());
		return request;
	}

}
