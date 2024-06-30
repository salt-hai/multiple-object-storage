package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.object.ObjectMetadataDomain;
import salthai.top.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.ObjectMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象元数据转对象转换器
 *
 * @author Kuang HaiBo 2023/12/14 15:14
 */
public class ObjectMetadataToDomainConverter
		extends HeaderResponseToBaseDomainConverter<ObjectMetadata, ObjectMetadataDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param objectMetadata 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ObjectMetadataDomain getInstance(ObjectMetadata objectMetadata) {
		ObjectMetadataDomain domain = new ObjectMetadataDomain();
		Map<String, Object> allMetadata = objectMetadata.getAllMetadata();
		Map<String, String> userMetadata = new HashMap<>(allMetadata.size());
		allMetadata.forEach((s, o) -> userMetadata.put(s, o.toString()));
		domain.setUserMetadata(userMetadata);
		domain.setLastModified(objectMetadata.getLastModified());

		domain.setContentType(objectMetadata.getContentType());
		domain.setContentLength(objectMetadata.getContentLength());
		domain.setEtag(objectMetadata.getEtag());
		return domain;
	}

}
