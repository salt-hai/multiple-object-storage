package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.object.DelObjectArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.DeleteObjectRequest;

/**
 * 对象删除请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/15 14:25
 */
public class ArgumentToDeleteObjectRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<DelObjectArguments, DeleteObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param delObjectArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteObjectRequest getInstance(DelObjectArguments delObjectArguments) {
		DeleteObjectRequest request = new DeleteObjectRequest();
		request.setBucketName(delObjectArguments.getBucketName());
		request.setObjectKey(delObjectArguments.getObjectName());
		request.setVersionId(delObjectArguments.getVersionId());
		return request;
	}

}
