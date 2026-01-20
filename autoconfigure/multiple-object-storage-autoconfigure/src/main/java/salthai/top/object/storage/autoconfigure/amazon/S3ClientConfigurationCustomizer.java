package salthai.top.object.storage.autoconfigure.amazon;

import software.amazon.awssdk.services.s3.S3ClientBuilder;

/**
 * S3客户端配置定制器,用于用户自定义配置S3客户端
 *
 * @author Kuang HaiBo 2024/1/11 11:30
 */
@FunctionalInterface
public interface S3ClientConfigurationCustomizer {

	/**
	 * 定制S3客户端配置
	 * @param s3ClientBuilder S3客户端构建器
	 */
	void customize(S3ClientBuilder s3ClientBuilder);

}
