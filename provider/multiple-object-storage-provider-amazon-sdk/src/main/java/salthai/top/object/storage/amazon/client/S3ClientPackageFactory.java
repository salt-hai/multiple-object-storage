package salthai.top.object.storage.amazon.client;

import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import software.amazon.awssdk.utils.AttributeMap;

import java.util.Objects;

/**
 * 用于创建{@link S3ClientPackage} 的工厂
 *
 * @author Kuang HaiBo 2024/6/26 11:24
 */
public class S3ClientPackageFactory implements ProviderClientFactory<S3ClientPackage> {

	private final S3Properties s3Properties;

	/**
	 * 用于创建异步http请求客户端的配置文件,可使用{@link software.amazon.awssdk.http.SdkHttpConfigurationOption}中的属性
	 * 自行定义
	 */
	private AttributeMap asyncHttpConfig = AttributeMap.empty();

	/**
	 * 用于创建同步http请求客户端的配置文件,可使用{@link software.amazon.awssdk.http.SdkHttpConfigurationOption}中的属性
	 * 自行定义
	 */
	private AttributeMap syncHttpConfig = AttributeMap.empty();

	public S3ClientPackageFactory(S3Properties s3Properties, AttributeMap asyncHttpConfig,
			AttributeMap syncHttpConfig) {
		this.s3Properties = s3Properties;
		this.asyncHttpConfig = asyncHttpConfig;
		this.syncHttpConfig = syncHttpConfig;
	}

	public S3ClientPackageFactory(S3Properties s3Properties) {
		this.s3Properties = s3Properties;
	}

	/**
	 * 创建一个实例，非空
	 * <p>
	 * 通过使用该接口的实例，可以创建一个新的客户端
	 * </p>
	 * @return 实例
	 */
	@Override
	public S3ClientPackage create() {
		return null;
	}

	/**
	 * 销毁，集中销毁实例
	 * @param instance 关闭
	 */
	@Override
	public void destroy(S3ClientPackage instance) {
		if (Objects.nonNull(instance)) {
			try {
				instance.close();
			}
			catch (Exception e) {
				// nothing
			}
		}
	}

	/**
	 * 构建所需客户端
	 * @param properties
	 * @param asyncHttpConfig
	 * @param syncHttpConfig
	 * @return
	 */
	S3ClientPackage buildClient(S3Properties properties, AttributeMap asyncHttpConfig, AttributeMap syncHttpConfig) {
		return null;
	}

}
