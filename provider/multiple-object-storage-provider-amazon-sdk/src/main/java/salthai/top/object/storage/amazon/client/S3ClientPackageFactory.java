package salthai.top.object.storage.amazon.client;

import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.utils.AttributeMap;

import java.net.URI;
import java.util.Objects;

/**
 * 用于创建{@link S3ClientPackage} 的工厂
 *
 * @author Kuang HaiBo 2024/6/26 11:24
 */
public class S3ClientPackageFactory implements ProviderClientFactory<S3ClientPackage> {

	/**
	 * 访问配置
	 */
	private final S3Properties s3Properties;

	/**
	 * s3 配置文件,自定义配置时使用{@link S3Configuration#builder()} 构建
	 */
	private final S3Configuration s3Configuration;

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

	public S3ClientPackageFactory(S3Properties s3Properties, AttributeMap asyncHttpConfig, AttributeMap syncHttpConfig,
			S3Configuration s3Configuration) {
		this.s3Properties = s3Properties;
		this.asyncHttpConfig = asyncHttpConfig;
		this.syncHttpConfig = syncHttpConfig;
		this.s3Configuration = s3Configuration;
	}

	public S3ClientPackageFactory(S3Properties s3Properties, S3Configuration s3Configuration) {
		this.s3Properties = s3Properties;
		this.s3Configuration = s3Configuration;
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
		return buildClient(s3Properties, asyncHttpConfig, syncHttpConfig, s3Configuration);
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
	 * @param s3Configuration 配置文件
	 * @param properties 访问配置
	 * @param asyncHttpConfig 异步请求http配置
	 * @param syncHttpConfig 同步请求http配置
	 * @return 客户端
	 */
	static S3ClientPackage buildClient(S3Properties properties, AttributeMap asyncHttpConfig,
			AttributeMap syncHttpConfig, S3Configuration s3Configuration) {

		// 访问配置
		StaticCredentialsProvider accessProvider = StaticCredentialsProvider
			.create(AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));

		SdkAsyncHttpClient asyncHttpClient = NettyNioAsyncHttpClient.builder().buildWithDefaults(asyncHttpConfig);

		S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
			.credentialsProvider(accessProvider)
			.serviceConfiguration(s3Configuration)
			.region(Region.of(properties.getRegion()))
			.endpointOverride(URI.create(properties.getEndpoint()))
			.httpClient(asyncHttpClient)
			.build();

		// 高级传输将使用异步 http客户端
		S3TransferManager s3TransferManager = S3TransferManager.builder().s3Client(s3AsyncClient).build();

		// 普通 http 客户端
		SdkHttpClient syncHttpClient = ApacheHttpClient.builder().buildWithDefaults(syncHttpConfig);

		S3Client s3Client = S3Client.builder()
			.credentialsProvider(accessProvider)
			.serviceConfiguration(s3Configuration)
			.region(Region.of(properties.getRegion()))
			.endpointOverride(URI.create(properties.getEndpoint()))
			.httpClient(syncHttpClient)
			.build();

		S3Presigner s3Presigner = S3Presigner.builder()
			.credentialsProvider(accessProvider)
			.serviceConfiguration(s3Configuration)
			.region(Region.of(properties.getRegion()))
			.endpointOverride(URI.create(properties.getEndpoint()))
			.build();
		return new S3ClientPackage(s3Client, s3TransferManager, s3Presigner);
	}

}
