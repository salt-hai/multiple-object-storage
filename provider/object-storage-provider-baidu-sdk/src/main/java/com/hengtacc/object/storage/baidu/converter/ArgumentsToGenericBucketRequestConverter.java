package com.hengtacc.object.storage.baidu.converter;

import com.baidubce.services.bos.model.GenericBucketRequest;
import com.hengtacc.object.storage.core.arguments.base.BucketArguments;
import com.hengtacc.object.storage.core.model.converter.BaseConverter;

import java.util.Objects;

/**
 * 继承 BucketArguments 的参数都会在这里设置 bucket name
 *
 * @author Kuang HaiBo 2023/11/13 15:30
 */
public abstract class ArgumentsToGenericBucketRequestConverter<Source extends BucketArguments, Target extends GenericBucketRequest>
		implements BaseConverter<Source, Target> {

	/**
	 * 参数准备 即由来源参数的一些属性设置到目标
	 * @param source 源对象
	 * @param instance 转换后的对象
	 */
	@Override
	public void prepare(Source source, Target instance) {
		if (Objects.isNull(instance.getBucketName())) {
			instance.setBucketName(source.getBucketName());
		}
	}

}
