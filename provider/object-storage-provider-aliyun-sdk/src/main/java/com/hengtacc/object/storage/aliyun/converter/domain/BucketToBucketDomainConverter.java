package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.Bucket;
import com.hengtacc.object.storage.core.domain.base.OwnerDomain;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.function.Converter;

import java.util.Objects;

/**
 * 桶对象转 桶domain
 *
 * @author Kuang HaiBo 2023/11/16 13:48
 */
public class BucketToBucketDomainConverter implements Converter<Bucket, BucketDomain> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param ossBucket the source object to convert, which must be an instance of
	 * {@code S} (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public BucketDomain convert(Bucket ossBucket) {
		BucketDomain bucketDomain = new BucketDomain();
		bucketDomain.setBucketName(ossBucket.getName());
		bucketDomain.setCreationDate(ossBucket.getCreationDate());
		if (Objects.nonNull(ossBucket.getOwner())) {
			OwnerDomain ownerDomain = new OwnerDomain();
			ownerDomain.setDisplayName(ossBucket.getOwner().getDisplayName());
			ownerDomain.setId(ossBucket.getOwner().getId());
			bucketDomain.setOwnerAttribute(ownerDomain);
		}
		return bucketDomain;
	}

}
