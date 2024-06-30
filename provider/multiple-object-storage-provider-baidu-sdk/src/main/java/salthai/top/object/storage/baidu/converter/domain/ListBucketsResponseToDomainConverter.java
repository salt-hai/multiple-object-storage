package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.ListBucketsResponse;
import salthai.top.object.storage.core.domain.base.OwnerDomain;
import salthai.top.object.storage.core.domain.bucket.BucketDomain;
import salthai.top.object.storage.core.function.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 列举百度bos桶响应转换器
 *
 * @author Kuang HaiBo 2023/11/20 17:20
 */
public class ListBucketsResponseToDomainConverter implements Converter<ListBucketsResponse, List<BucketDomain>> {

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
	public List<BucketDomain> convert(ListBucketsResponse source) {
		List<BucketSummary> buckets = source.getBuckets();
		if (buckets.isEmpty()) {
			return new ArrayList<>();
		}
		OwnerDomain commonOwner = new OwnerDomain();
		if (Objects.nonNull(source.getOwner())) {
			commonOwner.setId(source.getOwner().getId());
			commonOwner.setDisplayName(source.getOwner().getDisplayName());
		}
		List<BucketDomain> domainList = new ArrayList<>(source.getBuckets().size());
		buckets.forEach(bucketSummary -> {
			BucketDomain bucketDomain = new BucketDomain();
			bucketDomain.setBucketName(bucketSummary.getName());
			bucketDomain.setCreationDate(bucketSummary.getCreationDate());
			bucketDomain.setOwnerAttribute(commonOwner);
			domainList.add(bucketDomain);
		});
		return domainList;
	}

}
