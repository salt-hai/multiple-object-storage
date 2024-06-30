package com.hengtacc.object.storage.core.utils;

import cn.hutool.core.lang.Assert;
import com.hengtacc.object.storage.core.function.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 对象转换 工具
 *
 * @author Kuang HaiBo 2023/11/17 15:10
 */
public class ConverterUtils {

	/**
	 * 转为 目标 列表
	 * @param sourceItems 来源
	 * @param targetConverter 转domain转换器
	 * @return domain 列表
	 * @param <Source> 来源
	 * @param <Target> 目标
	 */
	public static <Source, Target> List<Target> toTargets(Collection<Source> sourceItems,
			Converter<Source, Target> targetConverter) {
		if (Objects.isNull(sourceItems) || sourceItems.isEmpty()) {
			return new ArrayList<>();
		}
		return sourceItems.stream().map(targetConverter::convert).collect(Collectors.toList());
	}

	/**
	 * 转为 目标参数
	 * @param source 来源
	 * @param targetConverter 转目标转换器
	 * @return domain
	 * @param <Source> 来源
	 * @param <Target> 目标
	 */
	public static <Source, Target> Target toTarget(Source source, Converter<Source, Target> targetConverter) {
		Assert.notNull(source, "source cant be null");
		return targetConverter.convert(source);
	}

}
