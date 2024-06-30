package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import salthai.top.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import salthai.top.object.storage.core.domain.multipart.CompleteMultipartUploadDomain;

/**
 * 完成分片 响应转换
 *
 * @author Kuang HaiBo 2023/11/17 17:37
 */
public class CompleteMultipartUploadResultToDomainConverter extends
		GenericResultToBaseResponseDomainConverter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param completeMultipartUploadResult 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CompleteMultipartUploadDomain getInstance(CompleteMultipartUploadResult completeMultipartUploadResult) {
		CompleteMultipartUploadDomain uploadDomain = new CompleteMultipartUploadDomain();
		uploadDomain.setEtag(completeMultipartUploadResult.getETag());
		uploadDomain.setObjectName(completeMultipartUploadResult.getKey());
		uploadDomain.setBucketName(completeMultipartUploadResult.getBucketName());
		uploadDomain.setVersionId(completeMultipartUploadResult.getVersionId());
		return uploadDomain;
	}

}
