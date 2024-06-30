package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.bucket.BucketDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;
import com.obs.services.model.ObsBucket;

import java.util.Objects;

/**
 * obs 桶对象转换器
 *
 * @author Kuang HaiBo 2023/12/18 16:43
 */
public class ObsBucketToDomainConverter implements Converter<ObsBucket, BucketDomain> {

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
	public BucketDomain convert(ObsBucket source) {
		BucketDomain bucketDomain = new BucketDomain();
		bucketDomain.setBucketName(source.getBucketName());
		bucketDomain.setCreationDate(source.getCreationDate());
		if (Objects.nonNull(source.getOwner())) {
			bucketDomain.setOwnerAttribute(ConverterUtils.toTarget(source.getOwner(), new OwnerToDomainConverter()));
		}
		return bucketDomain;
	}

}
