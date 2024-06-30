package salthai.top.object.storage.aliyun.converter.arguments;

import cn.hutool.core.collection.CollectionUtil;
import com.aliyun.oss.model.UploadPartCopyRequest;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.UploadPartCopyArguments;

import java.util.Objects;

/**
 * 分片拷贝 转 oss 分片拷贝参数
 *
 * @author Kuang HaiBo 2023/11/17 16:34
 */
public class ArgumentsToUploadPartCopyRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<UploadPartCopyArguments, UploadPartCopyRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param uploadPartCopyArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public UploadPartCopyRequest getInstance(UploadPartCopyArguments uploadPartCopyArguments) {
		UploadPartCopyRequest request = new UploadPartCopyRequest();
		request.setUploadId(uploadPartCopyArguments.getUploadId());
		request.setPartNumber(uploadPartCopyArguments.getPartNumber());
		request.setPartSize(uploadPartCopyArguments.getPartSize());
		// 目标
		request.setBucketName(uploadPartCopyArguments.getBucketName());
		request.setKey(uploadPartCopyArguments.getObjectName());
		// 来源
		request.setSourceBucketName(uploadPartCopyArguments.getSourceBucketName());
		request.setSourceKey(uploadPartCopyArguments.getSourceObjectName());
		request.setSourceVersionId(uploadPartCopyArguments.getSourceObjectVersionId());

		if (CollectionUtil.isNotEmpty(uploadPartCopyArguments.getMatchingEtagConstraints())) {
			request.setMatchingETagConstraints(uploadPartCopyArguments.getMatchingEtagConstraints());
		}
		if (CollectionUtil.isNotEmpty(uploadPartCopyArguments.getNonMatchingEtagConstraints())) {
			request.setNonmatchingETagConstraints(uploadPartCopyArguments.getNonMatchingEtagConstraints());
		}
		if (Objects.nonNull(uploadPartCopyArguments.getModifiedSinceConstraint())) {
			request.setModifiedSinceConstraint(uploadPartCopyArguments.getModifiedSinceConstraint());
		}
		if (Objects.nonNull(uploadPartCopyArguments.getUnmodifiedSinceConstraint())) {
			request.setUnmodifiedSinceConstraint(uploadPartCopyArguments.getUnmodifiedSinceConstraint());
		}
		return request;
	}

}
