package salthai.top.object.storage.amazon.converter;

import cn.hutool.core.map.MapUtil;
import salthai.top.object.storage.core.arguments.base.BaseArguments;
import salthai.top.object.storage.core.model.converter.BaseConverter;
import software.amazon.awssdk.awscore.AwsRequest;

/**
 * 基础参数转换
 *
 * @author Kuang HaiBo 2024/7/10 15:38
 */
public abstract class BaseArgumentsToAwsRequestConverter<Source extends BaseArguments, Target extends AwsRequest>
		implements BaseConverter<Source, Target> {

	/**
	 * 参数准备 即由来源对象的一些属性设置到目标对象
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		// 在此处理一些在请求时额外请求头参数
		instance.toBuilder().overrideConfiguration(builder -> {
			if (MapUtil.isNotEmpty(source.getExtraHeaders())) {
				source.getExtraHeaders().forEach(builder::putHeader);
			}
			if (MapUtil.isNotEmpty(source.getExtraQueryParams())) {
				source.getExtraQueryParams().forEach(builder::putRawQueryParameter);
			}
		});
	}

}
