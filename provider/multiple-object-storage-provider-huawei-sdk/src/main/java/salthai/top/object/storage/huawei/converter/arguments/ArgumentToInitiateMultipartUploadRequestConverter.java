package salthai.top.object.storage.huawei.converter.arguments;

import cn.hutool.core.map.MapUtil;
import salthai.top.object.storage.core.arguments.multipart.InitiateMultipartUploadArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.InitiateMultipartUploadRequest;
import com.obs.services.model.ObjectMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化分片请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/19 16:38
 */
public class ArgumentToInitiateMultipartUploadRequestConverter extends
		ArgumentsToBaseObjectRequestConverter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public InitiateMultipartUploadRequest getInstance(InitiateMultipartUploadArguments arguments) {
		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arguments.getContentType());
		if (MapUtil.isNotEmpty(arguments.getMetadata())) {
			Map<String, Object> obsMetadata = new HashMap<>(arguments.getMetadata());
			objectMetadata.setMetadata(obsMetadata);
		}
		request.setMetadata(objectMetadata);
		return request;
	}

}
