package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.multipart.UploadDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;
import com.obs.services.model.MultipartUpload;

import java.util.Objects;

/**
 * 分片对象转换器
 *
 * @author Kuang HaiBo 2023/12/20 17:38
 */
public class MultipartUploadToDomainConverter implements Converter<MultipartUpload, UploadDomain> {

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
	public UploadDomain convert(MultipartUpload source) {
		UploadDomain uploadDomain = new UploadDomain();
		uploadDomain.setUploadId(source.getUploadId());
		uploadDomain.setInitiated(source.getInitiatedDate());
		if (Objects.nonNull(source.getInitiator())) {
			uploadDomain.setInitiator(ConverterUtils.toTarget(source.getInitiator(), new OwnerToDomainConverter()));
		}
		if (Objects.nonNull(source.getOwner())) {
			uploadDomain.setOwner(ConverterUtils.toTarget(source.getOwner(), new OwnerToDomainConverter()));
		}
		uploadDomain.setKey(source.getObjectKey());
		uploadDomain.setStorageClass(source.getObjectStorageClass().getCode());
		return uploadDomain;
	}

}
