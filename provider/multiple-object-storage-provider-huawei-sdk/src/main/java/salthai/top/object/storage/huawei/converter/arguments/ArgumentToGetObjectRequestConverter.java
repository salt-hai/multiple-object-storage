package salthai.top.object.storage.huawei.converter.arguments;

import cn.hutool.core.collection.CollectionUtil;
import salthai.top.object.storage.core.arguments.object.GetObjectArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.GetObjectRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 请求参数转为 obs 获取对象请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/15 9:41
 */
public class ArgumentToGetObjectRequestConverter
		extends ArgumentsToBaseObjectRequestConverter<GetObjectArguments, GetObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectRequest getInstance(GetObjectArguments arguments) {
		GetObjectRequest request = new GetObjectRequest();
		request.setVersionId(arguments.getVersionId());
		Map<String, String> extraQueryParams = arguments.getExtraQueryParams();
		if (CollectionUtil.isNotEmpty(extraQueryParams)) {
			request.setRequestParameters(extraQueryParams);
		}
		if (Objects.nonNull(arguments.getModifiedSince())) {
			request.setIfModifiedSince(arguments.getModifiedSince());
		}
		if (Objects.nonNull(arguments.getUnmodifiedSince())) {
			request.setIfUnmodifiedSince(arguments.getUnmodifiedSince());
		}
		return request;
	}

}
