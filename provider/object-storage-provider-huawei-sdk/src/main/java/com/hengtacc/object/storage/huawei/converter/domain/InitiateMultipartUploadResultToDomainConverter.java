package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.multipart.InitiateMultipartUploadDomain;
import com.hengtacc.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.InitiateMultipartUploadResult;

/**
 * 初始化分片上传结果转换器
 *
 * @author Kuang HaiBo 2023/12/19 16:47
 */
public class InitiateMultipartUploadResultToDomainConverter
		extends HeaderResponseToBaseDomainConverter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public InitiateMultipartUploadDomain getInstance(InitiateMultipartUploadResult uploadResult) {
		InitiateMultipartUploadDomain uploadDomain = new InitiateMultipartUploadDomain();
		uploadDomain.setUploadId(uploadResult.getUploadId());
		uploadDomain.setBucketName(uploadResult.getBucketName());
		uploadDomain.setObjectName(uploadResult.getObjectKey());
		return uploadDomain;
	}

}
