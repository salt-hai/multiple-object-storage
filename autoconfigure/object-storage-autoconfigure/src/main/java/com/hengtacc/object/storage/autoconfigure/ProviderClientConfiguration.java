package com.hengtacc.object.storage.autoconfigure;

import com.hengtacc.object.storage.autoconfigure.properties.ObjectStorageProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.ClassUtils;

/**
 * 供应商客户端配置,用于继承，提供一些通用方法,
 *
 * @author Kuang HaiBo 2024/6/4 10:23
 */
public abstract class ProviderClientConfiguration {

	private static final boolean COMMONS_POOL2_AVAILABLE = ClassUtils.isPresent("org.apache.commons.pool2.ObjectPool",
			ProviderClientConfiguration.class.getClassLoader());

	private final ObjectStorageProperties objectStorageProperties;

	public ProviderClientConfiguration(ObjectStorageProperties objectStorageProperties) {
		this.objectStorageProperties = objectStorageProperties;
	}

	/**
	 * 开启了池化配置
	 * @return true 开启了
	 */
	protected boolean isPoolEnabled() {
		Boolean enabled = this.objectStorageProperties.getPool().getEnabled();
		return (enabled != null) ? enabled : COMMONS_POOL2_AVAILABLE;
	}

	/**
	 * 开启 https
	 * @return true 启用
	 */
	protected boolean isEnableHttps() {
		if (objectStorageProperties.haveHttpProtocolConfig()) {
			return false;
		}
		return objectStorageProperties.isEnableHttps();
	}

	/**
	 * 获取池化配置
	 * @return 池配置
	 */
	protected GenericObjectPoolConfig<?> getPoolConfig() {
		ObjectStorageProperties.Pool properties = objectStorageProperties.getPool();
		GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
		config.setMaxTotal(properties.getMaxActive());
		config.setMaxIdle(properties.getMaxIdle());
		config.setMinIdle(properties.getMinIdle());
		if (properties.getTimeBetweenEvictionRuns() != null) {
			config.setTimeBetweenEvictionRuns(properties.getTimeBetweenEvictionRuns());
		}
		if (properties.getMaxWait() != null) {
			config.setMaxWait(properties.getMaxWait());
		}
		return config;
	}

	/**
	 * 获取配置文件
	 * @return 配置
	 */
	protected ObjectStorageProperties getObjectStorageProperties() {
		return objectStorageProperties;
	}

}
