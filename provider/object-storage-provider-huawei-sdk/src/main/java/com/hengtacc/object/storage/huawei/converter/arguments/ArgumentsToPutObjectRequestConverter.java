package com.hengtacc.object.storage.huawei.converter.arguments;

import cn.hutool.core.map.MapUtil;
import com.hengtacc.object.storage.core.arguments.object.PutObjectArguments;
import com.hengtacc.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象上传转换
 *
 * @author Kuang HaiBo 2023/11/15 17:05
 */
public class ArgumentsToPutObjectRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<PutObjectArguments, PutObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param putObjectArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutObjectRequest getInstance(PutObjectArguments putObjectArguments) {
		// 继承的类中已经设置了对象信息 这里直接new 即可
		PutObjectRequest putObjectRequest = new PutObjectRequest();
		// 注意 接口的入口是不关闭输入流的， 流的生命周期由调用方控制,这里设置为不关闭
		putObjectRequest.setAutoClose(false);
		putObjectRequest.setInput(putObjectArguments.getInputStream());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(putObjectArguments.getContentType());
		objectMetadata.setContentLength(putObjectArguments.getObjectSize());
		if (MapUtil.isNotEmpty(putObjectArguments.getMetadata())) {
			Map<String, Object> mapMetadata = new HashMap<>(putObjectArguments.getMetadata());
			objectMetadata.setMetadata(mapMetadata);
		}
		putObjectRequest.setMetadata(objectMetadata);
		return putObjectRequest;
	}

}
