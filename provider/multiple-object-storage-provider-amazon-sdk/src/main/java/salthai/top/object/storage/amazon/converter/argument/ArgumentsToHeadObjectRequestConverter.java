package salthai.top.object.storage.amazon.converter.argument;

import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.object.GetObjectMetadataArguments;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;

import java.util.Objects;

/**
 * 将获取对象元数据请求转为 aws 获取对象的请求
 *
 * @author Kuang HaiBo 2025/5/19 15:50
 */
public class ArgumentsToHeadObjectRequestConverter
		extends BaseArgumentsToAwsRequestConverter<GetObjectMetadataArguments, HeadObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param getObjectMetadataArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public HeadObjectRequest getInstance(GetObjectMetadataArguments getObjectMetadataArguments) {
		return HeadObjectRequest.builder()
			.bucket(getObjectMetadataArguments.getBucketName())
			.key(getObjectMetadataArguments.getObjectName())
			.versionId(getObjectMetadataArguments.getVersionId())
			.ifModifiedSince(Objects.nonNull(getObjectMetadataArguments.getModifiedSince())
					? getObjectMetadataArguments.getModifiedSince().toInstant() : null)
			.ifUnmodifiedSince(Objects.nonNull(getObjectMetadataArguments.getUnmodifiedSince())
					? getObjectMetadataArguments.getUnmodifiedSince().toInstant() : null)
			.build();
	}

}
