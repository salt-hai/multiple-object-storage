package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import salthai.top.object.storage.core.domain.multipart.CompleteMultipartUploadDomain;
import salthai.top.object.storage.core.function.Converter;

/**
 * 完成分片上传 请求响应装换domain 转换器
 *
 * @author Kuang HaiBo 2023/12/7 17:25
 */
public class CompleteMultipartUploadResponseToDomainConverter
		implements Converter<CompleteMultipartUploadResponse, CompleteMultipartUploadDomain> {

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
	public CompleteMultipartUploadDomain convert(CompleteMultipartUploadResponse source) {
		CompleteMultipartUploadDomain uploadDomain = new CompleteMultipartUploadDomain();
		uploadDomain.setEtag(source.getETag());
		uploadDomain.setBucketName(source.getBucketName());
		uploadDomain.setObjectName(source.getKey());
		return uploadDomain;
	}

}
