package salthai.top.object.storage.amazon.converter.argument;

import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.object.GetObjectArguments;

import java.util.Objects;

/**
 * 转换为 S3 GetObjectRequest
 *
 * @author Kuang HaiBo 2025/5/20 11:12
 */
public class ArgumentsToGetObjectRequestConverter
		extends BaseArgumentsToAwsRequestConverter<GetObjectArguments, GetObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectRequest getInstance(GetObjectArguments arguments) {
		GetObjectRequest.Builder builder = GetObjectRequest.builder()
			.bucket(arguments.getBucketName())
			.key(arguments.getObjectName());

		// 设置版本ID
		if (StringUtils.isNotBlank(arguments.getVersionId())) {
			builder.versionId(arguments.getVersionId());
		}

		// 设置条件参数
		if (Objects.nonNull(arguments.getModifiedSince())) {
			builder.ifModifiedSince(arguments.getModifiedSince());
		}
		if (Objects.nonNull(arguments.getUnmodifiedSince())) {
			builder.ifUnmodifiedSince(arguments.getUnmodifiedSince());
		}
		if (StringUtils.isNotBlank(arguments.getMatchEtag())) {
			builder.ifMatch(arguments.getMatchEtag());
		}
		if (StringUtils.isNotBlank(arguments.getNotMatchEtag())) {
			builder.ifNoneMatch(arguments.getNotMatchEtag());
		}

		// 设置范围读取
		if (Objects.nonNull(arguments.getRange())) {
			builder.range(arguments.getRange());
		}

		return builder.build();
	}

}
