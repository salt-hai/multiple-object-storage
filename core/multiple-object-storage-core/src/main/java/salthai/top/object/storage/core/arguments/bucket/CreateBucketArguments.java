package salthai.top.object.storage.core.arguments.bucket;

import salthai.top.object.storage.core.arguments.base.BucketArguments;

/**
 * 创建bucket 统一参数
 *
 * @author Kuang HaiBo 2023/11/16 10:15
 */
public class CreateBucketArguments extends BucketArguments {

	public CreateBucketArguments() {
	}

	public CreateBucketArguments(String bucketName) {
		setBucketName(bucketName);
	}

	@Override
	public String toString() {
		return "CreateBucketArguments{} " + super.toString();
	}

}
