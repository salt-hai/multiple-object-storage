package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.UploadPartCopyResult;
import com.hengtacc.object.storage.core.domain.multipart.UploadPartCopyDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 分片拷贝 响应结果转换
 *
 * @author Kuang HaiBo 2023/11/17 17:13
 */
public class UploadPartCopyResultToDomainConverter implements Converter<UploadPartCopyResult, UploadPartCopyDomain> {

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
	public UploadPartCopyDomain convert(UploadPartCopyResult source) {
		UploadPartCopyDomain domain = new UploadPartCopyDomain();
		domain.setEtag(source.getETag());
		domain.setPartNumber(source.getPartNumber());
		return domain;
	}

}
