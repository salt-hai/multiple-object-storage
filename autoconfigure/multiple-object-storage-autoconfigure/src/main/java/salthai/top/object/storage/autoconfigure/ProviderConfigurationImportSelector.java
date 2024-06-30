package salthai.top.object.storage.autoconfigure;

import salthai.top.object.storage.core.enums.Provider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * 配置文件导入选择器,导入全部的配置文件,
 * 例如{@link ObjectStorageAutoConfiguration}，配置文件枚举在{@link ProviderConfigurations}
 *
 * @author HaiBo Kuang 2023/11/11 22:56
 */
class ProviderConfigurationImportSelector implements ImportSelector {

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
