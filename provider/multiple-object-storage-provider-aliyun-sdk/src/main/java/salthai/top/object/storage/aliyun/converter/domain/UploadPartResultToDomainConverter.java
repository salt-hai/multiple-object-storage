package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.UploadPartResult;
import salthai.top.object.storage.core.domain.multipart.UploadPartDomain;
import salthai.top.object.storage.core.function.Converter;

/**
 * 转为分片上传领域
 *
 * @author Kuang HaiBo 2023/11/17 15:30
 */
public class UploadPartResultToDomainConverter implements Converter<UploadPartResult, UploadPartDomain> {

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
	public UploadPartDomain convert(UploadPartResult source) {
		UploadPartDomain partDomain = new UploadPartDomain();
		partDomain.setPartSize(source.getPartSize());
		partDomain.setEtag(source.getETag());
		partDomain.setPartNumber(source.getPartNumber());
		return partDomain;
	}

}
