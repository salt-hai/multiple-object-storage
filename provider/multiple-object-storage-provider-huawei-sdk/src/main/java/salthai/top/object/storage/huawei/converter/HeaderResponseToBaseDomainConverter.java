package salthai.top.object.storage.huawei.converter;

import salthai.top.object.storage.core.domain.base.BaseResponseDomain;
import salthai.top.object.storage.core.model.converter.BaseConverter;
import com.obs.services.model.HeaderResponse;

import java.util.Map;
import java.util.Objects;

/**
 * 对obs 统一的头部返回值进行 处理
 *
 * @author Kuang HaiBo 2023/11/15 17:21
 */
public abstract class HeaderResponseToBaseDomainConverter<Source extends HeaderResponse, Target extends BaseResponseDomain>
		implements BaseConverter<Source, Target> {

	/**
	 * 参数准备 即由来源对象的一些属性设置到目标对象
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		Map<String, Object> responseHeaders = source.getResponseHeaders();
		if (Objects.nonNull(responseHeaders) && !responseHeaders.isEmpty()) {
			instance.setHeader(responseHeaders);
		}
	}

}
