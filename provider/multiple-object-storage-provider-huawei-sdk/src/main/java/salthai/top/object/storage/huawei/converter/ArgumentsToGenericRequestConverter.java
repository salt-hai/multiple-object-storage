package salthai.top.object.storage.huawei.converter;

import cn.hutool.core.map.MapUtil;
import salthai.top.object.storage.core.arguments.base.BaseArguments;
import salthai.top.object.storage.core.model.converter.BaseConverter;
import com.obs.services.model.GenericRequest;

import java.util.HashMap;

/**
 * obs 通用请求的转换器
 *
 * @author Kuang HaiBo 2023/11/15 16:53
 */
public abstract class ArgumentsToGenericRequestConverter<Source extends BaseArguments, Target extends GenericRequest>
		implements BaseConverter<Source, Target> {

	/**
	 * 此处给obs设置一些统一参数 ，
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		// 设置额外的请求头
		if (MapUtil.isNotEmpty(source.getExtraHeaders())) {
			HashMap<String, String> userHeaders = instance.getUserHeaders();
			userHeaders.putAll(source.getExtraHeaders());
			instance.setUserHeaders(userHeaders);
		}
	}

}
