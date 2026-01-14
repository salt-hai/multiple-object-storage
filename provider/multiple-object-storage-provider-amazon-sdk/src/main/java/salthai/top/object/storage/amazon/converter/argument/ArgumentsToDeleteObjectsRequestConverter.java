package salthai.top.object.storage.amazon.converter.argument;

import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.object.DelObjectsArguments;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换为 S3 DeleteObjectsRequest
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class ArgumentsToDeleteObjectsRequestConverter
		extends BaseArgumentsToAwsRequestConverter<DelObjectsArguments, DeleteObjectsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteObjectsRequest getInstance(DelObjectsArguments arguments) {
		// 构建要删除的对象标识列表
		List<ObjectIdentifier> objectIdentifiers = arguments.getObjectNames()
			.stream()
			.map(objectName -> ObjectIdentifier.builder().key(objectName).build())
			.collect(Collectors.toList());

		// 构建删除请求
		Delete delete = Delete.builder().objects(objectIdentifiers).build();

		return DeleteObjectsRequest.builder()
			.bucket(arguments.getBucketName())
			.delete(delete)
			.quiet(false) // 返回所有删除结果
			.build();
	}

}
