package salthai.top.object.storage.autoconfigure.bos;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import jakarta.annotation.PostConstruct;
import salthai.top.object.storage.autoconfigure.ProviderClientConfiguration;
import salthai.top.object.storage.autoconfigure.properties.ObjectStorageProperties;
import salthai.top.object.storage.baidu.client.BosClientFactory;
import salthai.top.object.storage.baidu.config.BaiDuBosProperties;
import salthai.top.object.storage.core.config.AbstractProviderProperties;
import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.provider.DefaultProviderClientPoolingManager;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bos 客户端配置
 *
 * @author HaiBo Kuang 2023/11/11 17:43
 */
@Configuration(proxyBeanMethods = false)
class BosClientConfiguration extends ProviderClientConfiguration {

	private final static Logger log = LoggerFactory.getLogger(BosClientConfiguration.class);

	public BosClientConfiguration(ObjectStorageProperties objectStorageProperties) {
		super(objectStorageProperties);
	}

	@PostConstruct
	void init() {
		log.info("==========[BaiDuBos Client Start Configure]===========");
	}

	/**
	 * bos 配置项目
	 * @return 阿里云供应商配置项目
	 */
	@ConditionalOnMissingBean(AbstractProviderProperties.class)
	@Bean
	@ConfigurationProperties(prefix = StorageConstants.BAI_DU_PROVIDER)
	public BaiDuBosProperties baiDuBosProperties() {
		return new BaiDuBosProperties();
	}

	/**
	 * 创建一个obs客户端工厂
	 * @param properties 配置文件
	 * @param configurationCustomizers obs自定义配置增强类
	 * @return obs客户端工厂bean
	 */
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientFactory<BosClient> providerClientFactory(BaiDuBosProperties properties,
			ObjectProvider<BosClientConfigurationCustomizer> configurationCustomizers) {
		properties.verify(StorageConstants.BAI_DU_PROVIDER);
		com.baidubce.services.bos.BosClientConfiguration configuration = new com.baidubce.services.bos.BosClientConfiguration();
		// 应用相应属性
		applyProperties(properties, configuration);
		// 对百度配置文件自定义配置
		configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
		return new BosClientFactory(properties, configuration);
	}

	/**
	 * bos 客户端管理器
	 * @param clientFactory 创建客户端的工厂
	 * @return obs 管理器
	 */
	@ConditionalOnBean(ProviderClientFactory.class)
	@ConditionalOnMissingBean(ProviderClientManager.class)
	@Bean
	public ProviderClientManager<BosClient> providerClientManager(ProviderClientFactory<BosClient> clientFactory) {
		log.info("==> Init BosClientManager");
		if (isPoolEnabled()) {
			return new DefaultProviderClientPoolingManager<>(clientFactory, getPoolConfig());
		}
		return new DefaultProviderClientSingletonManager<>(clientFactory);
	}

	/**
	 * 适配配置属性转为百度配置文件
	 * @param configuration 百度bos配置
	 * @param properties 百度配置属性
	 */
	void applyProperties(BaiDuBosProperties properties,
			com.baidubce.services.bos.BosClientConfiguration configuration) {
		configuration.setCredentials(new DefaultBceCredentials(properties.getAccessKey(), properties.getSecretKey()));
		configuration.setEndpoint(properties.getEndpoint());
		configuration.setPathStyleAccessEnable(properties.getPathStyleAccessEnable());
		configuration.setEnableHttpAsyncPut(properties.getEnableHttpAsyncPut());
		configuration.setProtocol(isEnableHttps() ? Protocol.HTTPS : Protocol.HTTP);
	}

}
