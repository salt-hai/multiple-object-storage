package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.object.DoesObjectExistArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.GetObjectMetadataRequest;

/**
 * 对象是否存在请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/14 15:02
 */
public class ArgumentToObjectDoesExistRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<DoesObjectExistArguments, GetObjectMetadataRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param doesObjectExistArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectMetadataRequest getInstance(DoesObjectExistArguments doesObjectExistArguments) {
		GetObjectMetadataRequest request = new GetObjectMetadataRequest();
		request.setVersionId(doesObjectExistArguments.getVersionId());
		return request;
	}

}
