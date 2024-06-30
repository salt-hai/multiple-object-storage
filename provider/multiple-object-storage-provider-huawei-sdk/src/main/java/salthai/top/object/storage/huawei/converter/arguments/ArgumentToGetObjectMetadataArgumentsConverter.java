package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.object.GetObjectMetadataArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.GetObjectMetadataRequest;

/**
 * obs 获取对象元数据参数转换器
 *
 * @author Kuang HaiBo 2023/12/14 15:10
 */
public class ArgumentToGetObjectMetadataArgumentsConverter
		extends ArgumentsToBaseObjectRequestConverter<GetObjectMetadataArguments, GetObjectMetadataRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectMetadataRequest getInstance(GetObjectMetadataArguments arguments) {
		GetObjectMetadataRequest request = new GetObjectMetadataRequest();
		request.setVersionId(arguments.getVersionId());
		return request;
	}

}
