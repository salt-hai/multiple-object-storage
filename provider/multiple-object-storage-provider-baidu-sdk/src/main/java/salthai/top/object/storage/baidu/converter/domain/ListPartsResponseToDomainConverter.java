package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.ListPartsResponse;
import salthai.top.object.storage.core.domain.multipart.ListPartsDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;

import java.util.Objects;

/**
 * bos列举分片响应转为模型转换器
 *
 * @author Kuang HaiBo 2023/12/11 11:27
 */
public class ListPartsResponseToDomainConverter implements Converter<ListPartsResponse, ListPartsDomain> {

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
	public ListPartsDomain convert(ListPartsResponse source) {
		ListPartsDomain listPartsDomain = new ListPartsDomain();
		listPartsDomain.setMaxParts(source.getMaxParts());
		listPartsDomain.setNextPartNumberMarker(source.getNextPartNumberMarker());
		listPartsDomain.setPartNumberMarker(source.getPartNumberMarker());
		listPartsDomain.setTruncated(source.isTruncated());
		listPartsDomain.setStorageClass(source.getStorageClass());
		listPartsDomain.setUploadId(source.getUploadId());
		listPartsDomain.setBucketName(source.getBucketName());
		listPartsDomain.setObjectName(source.getKey());

		listPartsDomain.setParts(ConverterUtils.toTargets(source.getParts(), new PartSummaryToDomainConverter()));

		if (Objects.nonNull(source.getOwner())) {
			listPartsDomain.setOwner(ConverterUtils.toTarget(source.getOwner(), new UserToOwnerDomainConverter()));
		}
		return listPartsDomain;
	}

}
