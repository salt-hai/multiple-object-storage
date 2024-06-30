package salthai.top.object.storage.baidu.converter.domain;

import com.baidubce.services.bos.model.UploadPartResponse;
import salthai.top.object.storage.core.domain.multipart.UploadPartDomain;
import salthai.top.object.storage.core.function.Converter;

/**
 * 上传分片的结果 转为 domain
 *
 * @author Kuang HaiBo 2023/12/6 17:14
 */
public class UploadPartResponseToDomainConverter implements Converter<UploadPartResponse, UploadPartDomain> {

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
	public UploadPartDomain convert(UploadPartResponse source) {
		UploadPartDomain partDomain = new UploadPartDomain();
		partDomain.setPartNumber(source.getPartNumber());
		partDomain.setEtag(source.getETag());
		return partDomain;
	}

}
