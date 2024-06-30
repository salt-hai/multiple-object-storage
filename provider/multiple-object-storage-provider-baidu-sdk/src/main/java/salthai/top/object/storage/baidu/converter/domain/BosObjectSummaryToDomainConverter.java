package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.BosObjectSummary;
import salthai.top.object.storage.core.domain.object.ObjectDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;

import java.util.Objects;

/**
 * bos 对象信息转对象领域
 *
 * @author HaiBo Kuang 2023/11/19 16:59
 */
public class BosObjectSummaryToDomainConverter implements Converter<BosObjectSummary, ObjectDomain> {

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
	public ObjectDomain convert(BosObjectSummary source) {
		ObjectDomain objectDomain = new ObjectDomain();
		objectDomain.setBucketName(source.getBucketName());
		objectDomain.setObjectName(source.getKey());
		objectDomain.setETag(source.getETag());
		objectDomain.setSize(source.getSize());
		objectDomain.setLastModified(source.getLastModified());
		objectDomain.setStorageClass(source.getStorageClass());
		if (Objects.nonNull(source.getOwner())) {
			objectDomain
				.setOwnerAttribute(ConverterUtils.toTarget(source.getOwner(), new UserToOwnerDomainConverter()));
		}
		return objectDomain;
	}

}
