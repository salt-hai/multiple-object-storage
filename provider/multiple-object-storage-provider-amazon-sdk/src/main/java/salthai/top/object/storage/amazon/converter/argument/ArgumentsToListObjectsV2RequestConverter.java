package salthai.top.object.storage.amazon.converter.argument;

import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.object.ListObjectsArguments;

import java.util.Objects;

/**
 * 转换为 S3 ListObjectsV2Request
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class ArgumentsToListObjectsV2RequestConverter
		extends BaseArgumentsToAwsRequestConverter<ListObjectsArguments, ListObjectsV2Request> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsV2Request getInstance(ListObjectsArguments arguments) {
		ListObjectsV2Request.Builder builder = ListObjectsV2Request.builder().bucket(arguments.getBucketName());

		// 设置前缀
		if (StringUtils.isNotBlank(arguments.getPrefix())) {
			builder.prefix(arguments.getPrefix());
		}

		// 设置分隔符
		if (StringUtils.isNotBlank(arguments.getDelimiter())) {
			builder.delimiter(arguments.getDelimiter());
		}

		// 设置最大返回数量
		if (Objects.nonNull(arguments.getMaxKeys())) {
			builder.maxKeys(arguments.getMaxKeys());
		}

		// 设置编码类型
		if (StringUtils.isNotBlank(arguments.getEncodingType())) {
			builder.encodingType(arguments.getEncodingType());
		}

		// 设置分页标记
		if (StringUtils.isNotBlank(arguments.getMarker())) {
			builder.continuationToken(arguments.getMarker());
		}

		return builder.build();
	}

}
