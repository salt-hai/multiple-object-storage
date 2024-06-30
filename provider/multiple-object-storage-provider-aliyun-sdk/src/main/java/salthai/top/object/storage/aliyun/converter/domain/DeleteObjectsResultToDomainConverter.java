package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.DeleteObjectsResult;
import salthai.top.object.storage.core.domain.object.DelObjectDomain;
import salthai.top.object.storage.core.function.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除结果转换器
 *
 * @author Kuang HaiBo 2023/11/9 14:35
 */
public class DeleteObjectsResultToDomainConverter implements Converter<DeleteObjectsResult, List<DelObjectDomain>> {

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
	public List<DelObjectDomain> convert(DeleteObjectsResult source) {
		List<String> deletedObjects = source.getDeletedObjects();
		if (deletedObjects.isEmpty()) {
			return new ArrayList<>();
		}
		List<DelObjectDomain> delObjectDomains = new ArrayList<>(deletedObjects.size());
		deletedObjects.forEach(key -> delObjectDomains.add(new DelObjectDomain(key)));
		return delObjectDomains;
	}

}
