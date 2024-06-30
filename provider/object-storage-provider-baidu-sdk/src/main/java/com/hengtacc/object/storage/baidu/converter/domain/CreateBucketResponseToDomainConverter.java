package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.CreateBucketResponse;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 创建bucket 响应转换器
 *
 * @author Kuang HaiBo 2023/11/20 17:37
 */
public class CreateBucketResponseToDomainConverter implements Converter<CreateBucketResponse, BucketDomain> {

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
	public BucketDomain convert(CreateBucketResponse source) {
		BucketDomain bucketDomain = new BucketDomain();
		bucketDomain.setBucketName(source.getName());
		return bucketDomain;
	}

}
