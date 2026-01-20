package salthai.top.object.storage.autoconfigure.amazon;

import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.client.S3ClientPackageFactory;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.autoconfigure.ProviderClientConfiguration;
import salthai.top.object.storage.autoconfigure.properties.ObjectStorageProperties;
import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.provider.DefaultProviderClientPoolingManager;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import software.amazon.awssdk.services.s3.S3Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * S3 客户端配置
 *
 * @author Kuang HaiBo 2024/1/11 11:32
 */
@Configuration(proxyBeanMethods = false)
class S3ClientConfiguration extends ProviderClientConfiguration {

	private final static Logger log = LoggerFactory.getLogger(S3ClientConfiguration.class);

	public S3ClientConfiguration(ObjectStorageProperties objectStorageProperties) {
		super(objectStorageProperties);
	}

	@PostConstruct
	void init() {
		log.info("==========[Amazon S3 Client Start Configure]===========");
	}

	/**
	 * S3 配置属性
	 * @return S3 配置属性
	 */
	@ConditionalOnMissingBean
	@Bean
	@ConfigurationProperties(prefix = StorageConstants.AMAZON_PROVIDER)
	public S3Properties s3Properties() {
		return new S3Properties();
	}

	/**
	 * 创建一个创建 S3 客户端的工厂 bean
	 * @param s3Properties 配置文件
	 * @param configurationCustomizers 配置定制器
	 * @return 创建客户端的工厂
	 */
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientFactory<S3ClientPackage> providerClientFactory(S3Properties s3Properties,
			ObjectProvider<S3ClientConfigurationCustomizer> configurationCustomizers) {
		s3Properties.verify(StorageConstants.AMAZON_PROVIDER);
		S3Configuration s3Configuration = S3Configuration.builder()
			.pathStyleAccessEnabled(isPathStyleAccessEnable())
			.build();
		configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(s3Configuration));
		return new S3ClientPackageFactory(s3Properties, s3Configuration);
	}

	/**
	 * S3 客户端管理器
	 * @param clientFactory 客户端工厂
	 * @return S3 管理器
	 */
	@ConditionalOnBean(ProviderClientFactory.class)
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientManager<S3ClientPackage> providerClientManager(
			ProviderClientFactory<S3ClientPackage> clientFactory) {
		log.info("==> Init S3ClientManager");
		if (isPoolEnabled()) {
			return new DefaultProviderClientPoolingManager<>(clientFactory, getPoolConfig());
		}
		return new DefaultProviderClientSingletonManager<>(clientFactory);
	}

}
