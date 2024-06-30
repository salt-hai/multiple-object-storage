package com.hengtacc.object.storage.huawei.converter.arguments;

import com.hengtacc.object.storage.core.arguments.multipart.UploadPartArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.UploadPartRequest;

/**
 * 上传分片请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 10:23
 */
public class ArgumentToUploadPartRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<UploadPartArguments, UploadPartRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public UploadPartRequest getInstance(UploadPartArguments arguments) {
		UploadPartRequest request = new UploadPartRequest();
		request.setUploadId(arguments.getUploadId());
		request.setPartNumber(arguments.getPartNumber());
		request.setPartSize(arguments.getPartSize());
		request.setInput(arguments.getInputStream());
		request.setContentMd5(arguments.getMd5Digest());
		// 注意 接口的入口是不关闭输入流的， 流的生命周期由调用方控制,这里设置为不关闭
		request.setAutoClose(false);
		return request;
	}

}
