package salthai.top.object.storage.amazon.converter.argument;

import salthai.top.object.storage.amazon.converter.BaseArgumentsToAwsRequestConverter;
import salthai.top.object.storage.amazon.converter.acl.S3AclConverter;
import salthai.top.object.storage.core.arguments.bucket.SetBucketAclArguments;
import salthai.top.object.storage.core.utils.ConverterUtils;
import software.amazon.awssdk.services.s3.model.PutBucketAclRequest;

/**
 * 设置 bucket acl
 *
 * @author Kuang HaiBo 2024/7/10 17:06
 */
public class SetBucketAclArgumentsToPutBucketAclRequestConverter
		extends BaseArgumentsToAwsRequestConverter<SetBucketAclArguments, PutBucketAclRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param setBucketAclArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutBucketAclRequest getInstance(SetBucketAclArguments setBucketAclArguments) {
		return PutBucketAclRequest.builder()
			.bucket(setBucketAclArguments.getBucketName())
			.acl(ConverterUtils.toTarget(setBucketAclArguments.getAcl(), S3AclConverter.INSTANCE))
			.build();
	}

}
