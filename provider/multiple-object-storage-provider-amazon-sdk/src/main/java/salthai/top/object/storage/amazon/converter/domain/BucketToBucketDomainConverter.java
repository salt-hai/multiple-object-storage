package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.core.domain.bucket.BucketDomain;
import salthai.top.object.storage.core.function.Converter;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.Date;

/**
 * aws {@link Bucket} To {@link BucketDomain}
 *
 * @author Kuang HaiBo 2024/7/10 15:14
 */
public class BucketToBucketDomainConverter implements Converter<Bucket, BucketDomain> {

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
	public BucketDomain convert(Bucket source) {
		BucketDomain bucketDomain = new BucketDomain();
		bucketDomain.setBucketName(source.name());
		bucketDomain.setCreationDate(Date.from(source.creationDate()));
		return bucketDomain;
	}

}
