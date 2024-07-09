package salthai.top.object.storage.autoconfigure.obs;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.HttpProtocolTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import salthai.top.object.storage.autoconfigure.ProviderClientConfiguration;
import salthai.top.object.storage.autoconfigure.properties.ObjectStorageProperties;
import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.provider.DefaultProviderClientPoolingManager;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import salthai.top.object.storage.huawei.client.ObsClientFactory;
import salthai.top.object.storage.huawei.config.HuaWeiObsProperties;

import javax.annotation.PostConstruct;

/**
 * 华为obs客户端
 *
 * @author HaiBo Kuang 2023/11/11 17:25
 */
@Configuration(proxyBeanMethods = false)
class ObsClientConfiguration extends ProviderClientConfiguration {

	private final static Logger log = LoggerFactory.getLogger(ObsClientConfiguration.class);

	@PostConstruct
	void init() {
		log.info("==========[HuaWeiOss Client Start Configure]===========");
	}

	public ObsClientConfiguration(ObjectStorageProperties objectStorageProperties) {
		super(objectStorageProperties);
	}

	/**
	 * obs 配置项目
	 * @return 阿里云供应商配置项目
	 */
	@ConditionalOnMissingBean
	@Bean
	@ConfigurationProperties(prefix = StorageConstants.HUA_WEI_PROVIDER)
	public HuaWeiObsProperties huaWeiObsProperties() {
		return new HuaWeiObsProperties();
	}

	/**
	 * 创建一个obs客户端工厂
	 * @param properties 配置文件
	 * @param obsClientConfigurationCustomizers obs自定义配置增强类
	 * @return obs客户端工厂bean
	 */
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientFactory<ObsClient> providerClientFactory(HuaWeiObsProperties properties,
			ObjectProvider<ObsClientConfigurationCustomizer> obsClientConfigurationCustomizers) {
		properties.verify(StorageConstants.HUA_WEI_PROVIDER);
		ObsConfiguration emptyConfig = new ObsConfiguration();
		applyProperties(properties, emptyConfig);
		obsClientConfigurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(emptyConfig));
		return new ObsClientFactory(properties, emptyConfig);
	}

	/**
	 * obs 客户端管理器
	 * @param obsFactory obs 工厂
	 * @return obs 管理器
	 */
	@ConditionalOnBean(ProviderClientFactory.class)
	@ConditionalOnMissingBean(ProviderClientManager.class)
	@Bean
	public ProviderClientManager<ObsClient> providerClientManager(ProviderClientFactory<ObsClient> obsFactory) {
		log.info("==> Init ObsClientManager");
		if (isPoolEnabled()) {
			return new DefaultProviderClientPoolingManager<>(obsFactory, getPoolConfig());
		}
		return new DefaultProviderClientSingletonManager<>(obsFactory);
	}

	/**
	 * 是配置配置文件所定义的属性
	 * @param properties 配置文件属性
	 * @param emptyConfig 空配置
	 */
	private void applyProperties(HuaWeiObsProperties properties, ObsConfiguration emptyConfig) {
		emptyConfig.setPathStyle(isPathStyleAccessEnable());
		emptyConfig.setEndPoint(properties.getEndpoint());
		emptyConfig.setHttpProtocolType(isEnableHttps() ? HttpProtocolTypeEnum.HTTP2_0 : HttpProtocolTypeEnum.HTTP1_1);
	}

}
