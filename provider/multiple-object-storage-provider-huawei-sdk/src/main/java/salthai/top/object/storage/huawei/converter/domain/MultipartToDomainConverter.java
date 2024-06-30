package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;
import salthai.top.object.storage.core.function.Converter;
import com.obs.services.model.Multipart;

/**
 * obs分片对象转换器
 *
 * @author Kuang HaiBo 2023/12/20 17:29
 */
public class MultipartToDomainConverter implements Converter<Multipart, PartSummaryDomain> {

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
	public PartSummaryDomain convert(Multipart source) {
		PartSummaryDomain partSummaryDomain = new PartSummaryDomain();
		partSummaryDomain.setEtag(source.getEtag());
		partSummaryDomain.setPartSize(source.getSize());
		partSummaryDomain.setPartNumber(source.getPartNumber());
		partSummaryDomain.setLastModifiedDate(source.getLastModified());
		return partSummaryDomain;
	}

}
