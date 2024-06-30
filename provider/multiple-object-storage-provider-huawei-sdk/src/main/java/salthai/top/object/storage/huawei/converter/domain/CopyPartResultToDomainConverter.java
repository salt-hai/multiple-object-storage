package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.multipart.UploadPartCopyDomain;
import salthai.top.object.storage.core.function.Converter;
import com.obs.services.model.CopyPartResult;

/**
 * 分片拷贝结果转换器
 *
 * @author Kuang HaiBo 2023/12/20 14:11
 */
public class CopyPartResultToDomainConverter implements Converter<CopyPartResult, UploadPartCopyDomain> {

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
	public UploadPartCopyDomain convert(CopyPartResult source) {
		UploadPartCopyDomain copyDomain = new UploadPartCopyDomain();
		copyDomain.setEtag(source.getEtag());
		copyDomain.setPartNumber(source.getPartNumber());
		return copyDomain;
	}

}
