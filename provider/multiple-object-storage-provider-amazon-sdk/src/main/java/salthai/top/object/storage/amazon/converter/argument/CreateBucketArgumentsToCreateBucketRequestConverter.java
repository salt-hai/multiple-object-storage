package salthai.top.object.storage.amazon.converter.argument;

import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.core.arguments.bucket.CreateBucketArguments;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

/**
 * 创建bucket参数转换
 *
 * @author Kuang HaiBo 2024/7/10 16:13
 */
public class CreateBucketArgumentsToCreateBucketRequestConverter
		extends BaseArgumentsToAwsRequestConverter<CreateBucketArguments, CreateBucketRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param createBucketArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CreateBucketRequest getInstance(CreateBucketArguments createBucketArguments) {
		return CreateBucketRequest.builder().bucket(createBucketArguments.getBucketName()).build();
	}

}
