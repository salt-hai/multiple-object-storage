package salthai.top.object.storage.autoconfigure;

import salthai.top.object.storage.autoconfigure.bos.BosConfiguration;
import salthai.top.object.storage.autoconfigure.obs.ObsConfiguration;
import salthai.top.object.storage.autoconfigure.oss.OssConfiguration;
import salthai.top.object.storage.core.enums.Provider;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * 映射了 配置类名称 以及 供应商类型
 *
 * @author HaiBo Kuang 2023/11/11 19:28
 */
final class ProviderConfigurations {

	private static final Map<Provider, String> MAPPINGS;
	static {
		Map<Provider, String> mappings = new EnumMap<>(Provider.class);
		mappings.put(Provider.ALIYUN, OssConfiguration.class.getName());
		mappings.put(Provider.HUAWEI, ObsConfiguration.class.getName());
		mappings.put(Provider.BaiDu, BosConfiguration.class.getName());
		MAPPINGS = Collections.unmodifiableMap(mappings);
	}

	private ProviderConfigurations() {
	}

	/**
	 * 获取供应商相应的配置配置类
	 * @param provider 供应商
	 * @return 配置类名称
	 */
	static String getConfigurationClass(Provider provider) {
		String configurationClassName = MAPPINGS.get(provider);
		Assert.state(configurationClassName != null, () -> "Unknown provider type " + provider);
		return configurationClassName;
	}

	/**
	 * 用配置文件配置类确认 供应商
	 * @param configurationClassName 配置类名称
	 * @return 供应商
	 */
	static Provider getType(String configurationClassName) {
		for (Map.Entry<Provider, String> entry : MAPPINGS.entrySet()) {
			if (entry.getValue().equals(configurationClassName)) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException("Unknown configuration class " + configurationClassName);
	}

}
