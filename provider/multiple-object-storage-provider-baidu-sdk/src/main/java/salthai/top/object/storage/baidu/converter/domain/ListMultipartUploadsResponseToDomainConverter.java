package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.ListMultipartUploadsResponse;
import salthai.top.object.storage.core.domain.multipart.ListMultipartUploadsDomain;
import salthai.top.object.storage.core.domain.multipart.UploadDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;

/**
 * 列举出正在分片上传的结果转domain 转换器
 *
 * @author Kuang HaiBo 2023/12/13 15:23
 */
public class ListMultipartUploadsResponseToDomainConverter
		implements Converter<ListMultipartUploadsResponse, ListMultipartUploadsDomain> {

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
	public ListMultipartUploadsDomain convert(ListMultipartUploadsResponse source) {
		ListMultipartUploadsDomain uploadsDomain = new ListMultipartUploadsDomain();
		uploadsDomain
			.setMultipartUploads(ConverterUtils.toTargets(source.getMultipartUploads(), multipartUploadSummary -> {
				UploadDomain uploadDomain = new UploadDomain();
				uploadDomain.setUploadId(multipartUploadSummary.getUploadId());
				uploadDomain.setInitiated(multipartUploadSummary.getInitiated());
				uploadDomain.setStorageClass(multipartUploadSummary.getStorageClass());
				uploadDomain.setKey(multipartUploadSummary.getKey());
				uploadDomain.setOwner(
						ConverterUtils.toTarget(multipartUploadSummary.getOwner(), new UserToOwnerDomainConverter()));
				return uploadDomain;
			}));

		uploadsDomain.setMaxUploads(source.getMaxUploads());
		uploadsDomain.setBucketName(source.getBucketName());
		uploadsDomain.setCommonPrefixes(source.getCommonPrefixes());
		uploadsDomain.setTruncated(source.isTruncated());
		uploadsDomain.setNextKeyMarker(source.getNextKeyMarker());
		uploadsDomain.setDelimiter(source.getDelimiter());
		return uploadsDomain;
	}

}
