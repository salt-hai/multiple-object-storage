package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.MultipartUploadListing;
import com.hengtacc.object.storage.core.domain.multipart.ListMultipartUploadsDomain;
import com.hengtacc.object.storage.core.domain.multipart.UploadDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.hengtacc.object.storage.core.utils.ConverterUtils;

/**
 * 列出正在进行的分片上传结果转换器
 *
 * @author HaiBo Kuang 2023/11/19 15:39
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
		uploadsDomain.setBucketName(source.getBucketName());
		uploadsDomain.setMaxUploads(source.getMaxUploads());
		uploadsDomain.setDelimiter(source.getDelimiter());
		uploadsDomain.setUploadIdMarker(source.getUploadIdMarker());
		uploadsDomain.setNextKeyMarker(source.getNextKeyMarker());
		uploadsDomain.setKeyMarker(source.getKeyMarker());
		uploadsDomain.setPrefix(source.getPrefix());
		uploadsDomain.setTruncated(source.isTruncated());
		uploadsDomain.setCommonPrefixes(source.getCommonPrefixes());
		uploadsDomain.setMultipartUploads(ConverterUtils.toTargets(source.getMultipartUploads(), multipartUpload -> {
			UploadDomain uploadDomain = new UploadDomain();
			uploadDomain.setUploadId(multipartUpload.getUploadId());
			uploadDomain.setInitiated(multipartUpload.getInitiated());
			uploadDomain.setStorageClass(multipartUpload.getStorageClass());
			uploadDomain.setKey(multipartUpload.getKey());
			return uploadDomain;
		}));
		return uploadsDomain;
	}

}
