package com.hengtacc.object.storage.autoconfigure;

import com.hengtacc.object.storage.autoconfigure.properties.ObjectStorageProperties;
import com.hengtacc.object.storage.core.constants.StorageConstants;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

/**
 * 存储自动配置
 *
 * @author Kuang HaiBo 2023/11/10 11:19
 */
@Import(value = ProviderConfigurationImportSelector.class)
@AutoConfiguration
@EnableConfigurationProperties(ObjectStorageProperties.class)
@ConditionalOnProperty(prefix = StorageConstants.PROPERTY_PREFIX, value = "enable", havingValue = "true",
		matchIfMissing = true)
public class ObjectStorageAutoConfiguration {

	// ============
	// 验证
	// ============
	@Bean
	ObjectStorageAutoConfigValidator objectStorageAutoConfigValidator(ObjectStorageProperties objectStorageProperties,
			ObjectProvider<ProviderClientManager<?>> providerClientManagers) {
		return new ObjectStorageAutoConfigValidator(objectStorageProperties, providerClientManagers);
	}

	/**
	 * 前置验证
	 */
	static class ObjectStorageAutoConfigValidator implements InitializingBean {

		private final ObjectStorageProperties objectStorageProperties;

		private final ObjectProvider<ProviderClientManager<?>> providerClientManagers;

		public ObjectStorageAutoConfigValidator(ObjectStorageProperties objectStorageProperties,
				ObjectProvider<ProviderClientManager<?>> providerClientManagers) {
			this.objectStorageProperties = objectStorageProperties;
			this.providerClientManagers = providerClientManagers;
		}

		/**
		 * Invoked by the containing {@code BeanFactory} after it has set all bean
		 * properties and satisfied {@link BeanFactoryAware},
		 * {@code ApplicationContextAware} etc.
		 * <p>
		 * This method allows the bean instance to perform validation of its overall
		 * configuration and final initialization when all bean properties have been set.
		 */
		@Override
		public void afterPropertiesSet() {
			Assert.notNull(providerClientManagers.getIfAvailable(),
					() -> "no object storage client could be auto-configured,check your configuration (storage provider type is'"
							+ this.objectStorageProperties.getProvider() + "') or your project dependency");
		}

	}

}
