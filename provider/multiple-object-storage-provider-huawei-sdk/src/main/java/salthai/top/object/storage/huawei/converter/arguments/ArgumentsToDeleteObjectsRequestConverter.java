package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.object.DelObjectPreArguments;
import salthai.top.object.storage.core.arguments.object.DelObjectsArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.DeleteObjectsRequest;

import java.util.List;

/**
 * 批量删除 请求参数转换
 *
 * @author Kuang HaiBo 2023/11/8 17:20
 */
public class ArgumentsToDeleteObjectsRequestConverter
		extends ArgumentsToGenericRequestConverter<DelObjectsArguments, DeleteObjectsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteObjectsRequest getInstance(DelObjectsArguments arguments) {
		List<DelObjectPreArguments> targetKeys = arguments.getObjects();
		if (targetKeys.isEmpty()) {
			throw new IllegalArgumentException("keys to delete must be specified");
		}
		DeleteObjectsRequest request = new DeleteObjectsRequest(arguments.getBucketName());
		request.setQuiet(arguments.getQuiet());
		targetKeys.forEach(
				targetObject -> request.addKeyAndVersion(targetObject.getObjectName(), targetObject.getVersionId()));
		return request;
	}

}
