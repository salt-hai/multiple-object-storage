package com.hengtacc.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.hengtacc.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import com.hengtacc.object.storage.core.domain.multipart.InitiateMultipartUploadDomain;

/**
 * 初始化分片 响应转换
 *
 * @author Kuang HaiBo 2023/11/17 14:51
 */
public class InitiateMultipartUploadResultToDomainConverter extends
		GenericResultToBaseResponseDomainConverter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param initiateMultipartUploadResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public InitiateMultipartUploadDomain getInstance(InitiateMultipartUploadResult initiateMultipartUploadResult) {
		InitiateMultipartUploadDomain domain = new InitiateMultipartUploadDomain();
		domain.setUploadId(initiateMultipartUploadResult.getUploadId());
		domain.setBucketName(initiateMultipartUploadResult.getBucketName());
		domain.setObjectName(initiateMultipartUploadResult.getKey());
		return domain;
	}

}
