package com.hengtacc.object.storage.autoconfigure.oss;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.comm.Protocol;
import com.hengtacc.object.storage.aliyun.client.OssClientFactory;
import com.hengtacc.object.storage.aliyun.config.AliYunOssProperties;
import com.hengtacc.object.storage.autoconfigure.ProviderClientConfiguration;
import com.hengtacc.object.storage.autoconfigure.properties.ObjectStorageProperties;
import com.hengtacc.object.storage.core.constants.StorageConstants;
import com.hengtacc.object.storage.core.provider.DefaultProviderClientPoolingManager;
import com.hengtacc.object.storage.core.provider.DefaultProviderClientSingletonManager;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import com.hengtacc.object.storage.core.provider.factory.ProviderClientFactory;
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
 * 阿里云客户端配置
 *
 * @author Kuang HaiBo 2023/11/3 14:47
 */
@Configuration(proxyBeanMethods = false)
class OssClientConfiguration extends ProviderClientConfiguration {

	private final static Logger log = LoggerFactory.getLogger(OssClientConfiguration.class);

	public OssClientConfiguration(ObjectStorageProperties objectStorageProperties) {
		super(objectStorageProperties);
	}

	@PostConstruct
	void init() {
		log.info("==========[AliYunOss Client Start Configure]===========");
	}

	/**
	 * 阿里云 配置项目
	 * @return 阿里云供应商配置项目
	 */
	@ConditionalOnMissingBean
	@Bean
	@ConfigurationProperties(prefix = StorageConstants.ALI_YUN_PROVIDER)
	public AliYunOssProperties aliYunOssProperties() {
		return new AliYunOssProperties();
	}

	/**
	 * 创建一个创建oss客户端的工厂 bean
	 * @param aliYunOssProperties 配置文件
	 * @param configurationCustomizers 配置定制器
	 * @return 创建客户端的工厂
	 */
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientFactory<OSS> providerClientFactory(AliYunOssProperties aliYunOssProperties,
			ObjectProvider<OssClientConfigurationCustomizer> configurationCustomizers) {
		aliYunOssProperties.verify(StorageConstants.ALI_YUN_PROVIDER);
		ClientBuilderConfiguration emptyConfig = new ClientBuilderConfiguration();
		applyProperties(emptyConfig);
		configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(emptyConfig));
		return new OssClientFactory(aliYunOssProperties, emptyConfig);
	}

	/**
	 * 阿里云 客户端管理器
	 * @param clientFactory 客户端工厂
	 * @return oss 管理器
	 */
	@ConditionalOnBean(ProviderClientFactory.class)
	@ConditionalOnMissingBean
	@Bean
	public ProviderClientManager<OSS> providerClientManager(ProviderClientFactory<OSS> clientFactory) {
		log.info("==> Init OssClientManager");
		if (isPoolEnabled()) {
			return new DefaultProviderClientPoolingManager<>(clientFactory, getPoolConfig());
		}
		return new DefaultProviderClientSingletonManager<>(clientFactory);
	}

	/**
	 * 为空配置应用相应属性
	 * @param emptyConfig 空配置
	 */
	void applyProperties(ClientBuilderConfiguration emptyConfig) {
		emptyConfig.setProtocol(isEnableHttps() ? Protocol.HTTP : Protocol.HTTPS);
	}

}
