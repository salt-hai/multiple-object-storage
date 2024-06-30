package com.hengtacc.object.storage.baidu.converter;

import com.baidubce.services.bos.model.GenericObjectRequest;
import com.hengtacc.object.storage.core.arguments.base.ObjectArguments;

import java.util.Objects;

/**
 * 基础的 对象通用参数转换
 *
 * @author Kuang HaiBo 2023/11/13 15:43
 */
public abstract class ArgumentsToGenericObjectRequestConverter<Source extends ObjectArguments, Target extends GenericObjectRequest>
		extends ArgumentsToGenericBucketRequestConverter<Source, Target> {

	/**
	 * 参数准备 即由来源参数的一些属性设置到目标
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		if (Objects.isNull(instance.getKey())) {
			// 统一设置了 对象key
			instance.setKey(source.getObjectName());
		}
		super.prepare(source, instance);
	}

}
