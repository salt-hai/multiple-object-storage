package com.hengtacc.object.storage.huawei.converter.domain;

import com.hengtacc.object.storage.core.domain.multipart.CompleteMultipartUploadDomain;
import com.hengtacc.object.storage.huawei.converter.HeaderResponseToBaseDomainConverter;
import com.obs.services.model.CompleteMultipartUploadResult;

/**
 * 完成分片上传 结果转换器
 *
 * @author Kuang HaiBo 2023/12/20 14:49
 */
public class CompleteMultipartUploadResultToDomainConverter
		extends HeaderResponseToBaseDomainConverter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param completeMultipartUploadResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CompleteMultipartUploadDomain getInstance(CompleteMultipartUploadResult completeMultipartUploadResult) {
		CompleteMultipartUploadDomain multipartUploadDomain = new CompleteMultipartUploadDomain();
		multipartUploadDomain.setEtag(completeMultipartUploadResult.getEtag());
		multipartUploadDomain.setBucketName(completeMultipartUploadResult.getBucketName());
		multipartUploadDomain.setObjectName(completeMultipartUploadResult.getObjectKey());
		multipartUploadDomain.setVersionId(completeMultipartUploadResult.getVersionId());
		return multipartUploadDomain;
	}

}
