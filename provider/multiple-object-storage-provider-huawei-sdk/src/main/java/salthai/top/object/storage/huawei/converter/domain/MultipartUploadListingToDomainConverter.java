package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.multipart.ListMultipartUploadsDomain;
import salthai.top.object.storage.core.function.Converter;
import salthai.top.object.storage.core.utils.ConverterUtils;
import com.obs.services.model.MultipartUploadListing;

import java.util.Arrays;

/**
 * 列出正在进行的分片上传结果转换器
 *
 * @author Kuang HaiBo 2023/12/20 17:35
 */
public class MultipartUploadListingToDomainConverter
		implements Converter<MultipartUploadListing, ListMultipartUploadsDomain> {

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
	public ListMultipartUploadsDomain convert(MultipartUploadListing source) {
		ListMultipartUploadsDomain uploadsDomain = new ListMultipartUploadsDomain();
		uploadsDomain.setMaxUploads(source.getMaxUploads());
		uploadsDomain.setBucketName(source.getBucketName());
		uploadsDomain.setUploadIdMarker(source.getUploadIdMarker());
		uploadsDomain.setMultipartUploads(
				ConverterUtils.toTargets(source.getMultipartTaskList(), new MultipartUploadToDomainConverter()));
		uploadsDomain.setDelimiter(source.getDelimiter());
		uploadsDomain.setCommonPrefixes(Arrays.asList(source.getCommonPrefixes()));
		uploadsDomain.setKeyMarker(source.getKeyMarker());
		uploadsDomain.setNextKeyMarker(source.getNextKeyMarker());
		uploadsDomain.setNextUploadIdMarker(source.getNextUploadIdMarker());
		uploadsDomain.setPrefix(source.getPrefix());
		uploadsDomain.setTruncated(source.isTruncated());
		return uploadsDomain;
	}

}
