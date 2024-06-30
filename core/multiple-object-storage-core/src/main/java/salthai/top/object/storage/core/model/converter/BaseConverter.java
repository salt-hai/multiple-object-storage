package salthai.top.object.storage.core.model.converter;

import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.model.arguments.ObjectStorageArguments;

/**
 * 基础转换，可以实现该类 或者 实现 {@link Converter} 最终调用{@link #convert(Object)} 得到的都是 目标对象
 *
 * @author Kuang HaiBo 2023/11/7 10:10
 */
public interface BaseConverter<Source, Target> extends Converter<Source, Target> {

	/**
	 * 获取最终生成对象实例
	 * @param source 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	Target getInstance(Source source);

	/**
	 * 参数准备 即由来源对象的一些属性设置到目标对象
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	void prepare(Source source, Target instance);

	/**
	 * 实体转换
	 * @param source 统一定义请求参数 {@link ObjectStorageArguments} (never {@code null})
	 * @return 转换后的对象实例
	 */
	@Override
	default Target convert(Source source) {
		Target instance = getInstance(source);
		prepare(source, instance);
		return instance;
	}

}
