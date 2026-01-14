package salthai.top.object.storage.amazon.converter.argument;

import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.object.DelObjectArguments;

/**
 * 转换为 S3 DeleteObjectRequest
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class ArgumentsToDeleteObjectRequestConverter
		extends BaseArgumentsToAwsRequestConverter<DelObjectArguments, DeleteObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteObjectRequest getInstance(DelObjectArguments arguments) {
		DeleteObjectRequest.Builder builder = DeleteObjectRequest.builder()
			.bucket(arguments.getBucketName())
			.key(arguments.getObjectName());

		// 设置版本ID
		if (StringUtils.isNotBlank(arguments.getVersionId())) {
			builder.versionId(arguments.getVersionId());
		}

		return builder.build();
	}

}
