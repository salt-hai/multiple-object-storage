package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.object.DelObjectDomain;
import com.hengtacc.object.storage.core.function.Converter;
import com.obs.services.model.DeleteObjectsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量删除结果转换器
 *
 * @author Kuang
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
		List<DeleteObjectsResult.DeleteObjectResult> deletedObjects = source.getDeletedObjectResults();
		if (deletedObjects.isEmpty()) {
			return new ArrayList<>();
		}
		List<DelObjectDomain> delObjectDomains = new ArrayList<>(deletedObjects.size());
		deletedObjects.forEach(key -> {
			DelObjectDomain delObjectDomain = new DelObjectDomain(key.getObjectKey());
			delObjectDomain.setVersionId(key.getVersion());
			delObjectDomains.add(delObjectDomain);
		});
		return delObjectDomains;
	}

}
