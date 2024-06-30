package salthai.top.object.storage.aliyun.converter;

import cn.hutool.core.map.MapUtil;
import com.aliyun.oss.model.WebServiceRequest;
import salthai.top.object.storage.core.arguments.base.BaseArguments;
import salthai.top.object.storage.core.model.converter.BaseConverter;

/**
 * 一个基础参数的转换器 转为 {@link com.aliyun.oss.model.WebServiceRequest}
 *
 * @author Kuang HaiBo 2023/11/8 11:55
 */
public abstract class BaseArgumentsToWebServiceRequestConverter<Source extends BaseArguments, Target extends WebServiceRequest>
		implements BaseConverter<Source, Target> {

	/**
	 * 参数准备 即由来源参数的一些属性设置到目标 这里设置一些 通用的 信息
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		if (MapUtil.isNotEmpty(source.getExtraHeaders())) {
			source.getExtraHeaders().forEach(instance::addHeader);
		}
		if (MapUtil.isNotEmpty(source.getExtraQueryParams())) {
			source.getExtraQueryParams().forEach(instance::addParameter);
		}
	}

}
