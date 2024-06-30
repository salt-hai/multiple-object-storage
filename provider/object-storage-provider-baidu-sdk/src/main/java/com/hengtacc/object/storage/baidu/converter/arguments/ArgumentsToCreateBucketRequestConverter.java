package com.hengtacc.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.CreateBucketRequest;
import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 创建bucket 请求转换器
 *
 * @author Kuang HaiBo 2023/11/20 17:31
 */
public class ArgumentsToCreateBucketRequestConverter implements Converter<CreateBucketArguments, CreateBucketRequest> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source the source object to convert, which must be an instance of {@code S}
	 * (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public CreateBucketRequest convert(CreateBucketArguments source) {
		return new CreateBucketRequest(source.getBucketName());
	}

}
