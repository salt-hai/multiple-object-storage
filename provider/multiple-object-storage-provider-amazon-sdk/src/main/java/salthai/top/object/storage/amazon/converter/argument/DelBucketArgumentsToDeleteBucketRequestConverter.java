package salthai.top.object.storage.amazon.converter.argument;

import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.bucket.DelBucketArguments;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;

/**
 * 删除 bucket 参数转换器
 *
 * @author Kuang HaiBo 2024/7/10 17:21
 */
public class DelBucketArgumentsToDeleteBucketRequestConverter
		extends BaseArgumentsToAwsRequestConverter<DelBucketArguments, DeleteBucketRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param delBucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DeleteBucketRequest getInstance(DelBucketArguments delBucketArguments) {
		return DeleteBucketRequest.builder().bucket(delBucketArguments.getBucketName()).build();
	}

}
