package salthai.top.object.storage.autoconfigure;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import salthai.top.object.storage.autoconfigure.properties.ObjectStorageProperties;
import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.enums.Provider;
import salthai.top.object.storage.core.provider.ProviderClientManager;

/**
 * 存储自动配置
 *
 * @author Kuang HaiBo 2023/11/10 11:19
 */
@Import(value = ObjectStorageAutoConfiguration.ConfigurationImportSelector.class)
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

	/**
	 * 配置文件导入选择器,导入全部的配置文件,
	 * 例如{@link salthai.top.object.storage.autoconfigure.oss.OssConfiguration}，配置文件枚举在{@link ProviderConfigurations}
	 *
	 * @author HaiBo Kuang 2023/11/11 22:56
	 */
	static class ConfigurationImportSelector implements ImportSelector {

		/**
		 * Select and return the names of which class(es) should be imported based on the
		 * {@link AnnotationMetadata} of the importing @{@link Configuration} class.
		 * @return the class names, or an empty array if none
		 */
		@NonNull
		@Override
		public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
			Provider[] types = Provider.values();
			String[] imports = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				imports[i] = ProviderConfigurations.getConfigurationClass(types[i]);
			}
			return imports;
		}

	}

}
