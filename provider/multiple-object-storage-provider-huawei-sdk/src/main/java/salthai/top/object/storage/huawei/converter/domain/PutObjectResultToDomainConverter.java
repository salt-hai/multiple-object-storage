package salthai.top.object.storage.huawei.converter.domain;

import salthai.top.object.storage.core.domain.object.PutObjectDomain;
import salthai.top.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.PutObjectResult;

/**
 * 上传对象结果转换
 *
 * @author Kuang HaiBo 2023/11/15 17:26
 */
public class PutObjectResultToDomainConverter
		extends HeaderResponseToBaseDomainConverter<PutObjectResult, PutObjectDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param putObjectResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutObjectDomain getInstance(PutObjectResult putObjectResult) {
		PutObjectDomain domain = new PutObjectDomain();
		domain.setBucketName(putObjectResult.getBucketName());
		domain.setObjectName(putObjectResult.getObjectKey());
		domain.setEtag(putObjectResult.getEtag());
		domain.setVersionId(putObjectResult.getVersionId());
		return domain;
	}

}
