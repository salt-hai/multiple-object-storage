package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.Owner;
import salthai.top.object.storage.core.domain.base.OwnerDomain;
import salthai.top.object.storage.core.domain.object.ObjectDomain;
import salthai.top.object.storage.core.function.Converter;

import java.util.Objects;

/**
 * 对象 信息 转 domain
 *
 * @author Kuang HaiBo 2023/11/15 10:43
 */
public class OssObjectSummaryToDomainConverter implements Converter<OSSObjectSummary, ObjectDomain> {

	/**
	 * 转为 目标对象
	 * @param source 来源
	 * @return 对象领域模型
	 */
	@Override
	public ObjectDomain convert(OSSObjectSummary source) {
		ObjectDomain objectDomain = new ObjectDomain();
		objectDomain.setBucketName(source.getBucketName());
		objectDomain.setObjectName(source.getKey());
		objectDomain.setETag(source.getETag());
		objectDomain.setSize(source.getSize());
		objectDomain.setLastModified(source.getLastModified());
		objectDomain.setStorageClass(source.getStorageClass());
		if (Objects.nonNull(source.getOwner())) {
			Owner ossOwner = source.getOwner();
			OwnerDomain ownerAttributeDomain = new OwnerDomain();
			ownerAttributeDomain.setId(ossOwner.getId());
			ownerAttributeDomain.setDisplayName(ossOwner.getDisplayName());
			objectDomain.setOwnerAttribute(ownerAttributeDomain);
		}
		return objectDomain;
	}

}
