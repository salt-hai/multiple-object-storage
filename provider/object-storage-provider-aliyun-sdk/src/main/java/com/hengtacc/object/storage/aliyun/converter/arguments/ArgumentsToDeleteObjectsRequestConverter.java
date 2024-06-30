package com.hengtacc.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.DeleteObjectsRequest;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.core.arguments.object.DelObjectPreArguments;
import com.hengtacc.object.storage.core.arguments.object.DelObjectsArguments;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 批量删除 请求参数转换
 *
 * @author Kuang HaiBo 2023/11/8 17:20
 */
public class ArgumentsToDeleteObjectsRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<DelObjectsArguments, DeleteObjectsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteObjectsRequest getInstance(DelObjectsArguments arguments) {
		List<DelObjectPreArguments> targetKeys = arguments.getObjects();
		if (targetKeys.isEmpty()) {
			throw new IllegalArgumentException("object name to delete must be specified");
		}
		List<String> versionIds = targetKeys.stream()
			.map(DelObjectPreArguments::getVersionId)
			.distinct()
			.collect(Collectors.toList());
		if (versionIds.size() != 1) {
			throw new IllegalArgumentException("目前阿里云批量删除key时不允许设置不同的对象版本");
		}
		DeleteObjectsRequest request = new DeleteObjectsRequest(arguments.getBucketName());
		request.setQuiet(arguments.getQuiet());
		request.setVersionId(targetKeys.get(0).getVersionId());
		request.setKeys(targetKeys.stream().map(DelObjectPreArguments::getVersionId).collect(Collectors.toList()));
		return request;
	}

}
