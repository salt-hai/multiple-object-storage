package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.ObjectMetadata;
import salthai.top.object.storage.core.domain.object.ObjectMetadataDomain;
import salthai.top.object.storage.core.function.Converter;

/**
 * 对象元数据 转领域模型
 *
 * @author Kuang HaiBo 2023/11/8 16:23
 */
public class ObjectMetadataToDomainConverter implements Converter<ObjectMetadata, ObjectMetadataDomain> {

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
	public ObjectMetadataDomain convert(ObjectMetadata source) {
		ObjectMetadataDomain domain = new ObjectMetadataDomain();
		domain.setContentLength(source.getContentLength());
		domain.setContentType(source.getContentType());
		domain.setLastModified(source.getLastModified());
		domain.setUserMetadata(source.getUserMetadata());
		domain.setEtag(source.getETag());
		domain.setVersionId(source.getVersionId());
		return domain;
	}

}
