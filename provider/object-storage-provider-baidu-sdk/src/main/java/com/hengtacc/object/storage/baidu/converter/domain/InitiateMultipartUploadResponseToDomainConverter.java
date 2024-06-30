package com.hengtacc.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.hengtacc.object.storage.core.domain.multipart.InitiateMultipartUploadDomain;
import com.hengtacc.object.storage.core.function.Converter;

/**
 * 初始化分片上传响应转domain
 *
 * @author Kuang HaiBo 2023/12/6 16:48
 */
public class InitiateMultipartUploadResponseToDomainConverter
		implements Converter<InitiateMultipartUploadResponse, InitiateMultipartUploadDomain> {

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
	public InitiateMultipartUploadDomain convert(InitiateMultipartUploadResponse source) {
		InitiateMultipartUploadDomain domain = new InitiateMultipartUploadDomain();
		domain.setUploadId(source.getUploadId());
		domain.setBucketName(source.getBucketName());
		domain.setObjectName(source.getKey());
		return domain;
	}

}
