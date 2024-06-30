package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.OSSObject;
import salthai.top.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import salthai.top.object.storage.core.domain.object.GetObjectDomain;

/**
 * oss 对象转 带有流信息 的 object domain
 *
 * @author Kuang HaiBo 2023/11/8 16:58
 */
public class OssObjectToGetObjectDomainConverter
		extends GenericResultToBaseResponseDomainConverter<OSSObject, GetObjectDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param ossObject 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectDomain getInstance(OSSObject ossObject) {
		GetObjectDomain domain = new GetObjectDomain();
		domain.setObjectName(ossObject.getKey());
		domain.setBucketName(ossObject.getBucketName());
		domain.setObjectContent(ossObject.getObjectContent());
		return domain;
	}

}
