package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.PutObjectResult;
import com.hengtacc.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import com.hengtacc.object.storage.core.domain.object.PutObjectDomain;

/**
 * 阿里云上传结果toDomain上传结果转换
 *
 * @author Kuang HaiBo 2023/11/8 14:11
 */
public class PutObjectResultToPutObjectDomainConverter
		extends GenericResultToBaseResponseDomainConverter<PutObjectResult, PutObjectDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param putObjectResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutObjectDomain getInstance(PutObjectResult putObjectResult) {
		PutObjectDomain putObjectDomain = new PutObjectDomain();
		putObjectDomain.setVersionId(putObjectResult.getVersionId());
		putObjectDomain.setEtag(putObjectResult.getETag());
		return putObjectDomain;
	}

}
