package salthai.top.object.storage.aliyun.converter;

import com.aliyun.oss.model.GenericResult;
import salthai.top.object.storage.core.domain.base.BaseResponseDomain;
import salthai.top.object.storage.core.model.converter.BaseConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 阿里云 通用响应 转 base domain
 *
 * @author Kuang HaiBo 2023/11/8 14:17
 */
public abstract class GenericResultToBaseResponseDomainConverter<Source extends GenericResult, Target extends BaseResponseDomain>
		implements BaseConverter<Source, Target> {

	/**
	 * 参数准备 即由来源参数的一些属性设置到目标
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		// fix 所获取的respond 可能是为空的
		if (Objects.isNull(source.getResponse()) || Objects.isNull(source.getResponse().getHeaders())) {
			return;
		}
		// 设置响应头
		Map<String, String> ossRespondHeaders = source.getResponse().getHeaders();
		if (!ossRespondHeaders.isEmpty()) {
			Map<String, Object> doMainHeaders = new HashMap<>(ossRespondHeaders.size());
			doMainHeaders.putAll(ossRespondHeaders);
			instance.setHeader(doMainHeaders);
		}
	}

}
