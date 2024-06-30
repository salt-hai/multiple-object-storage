package com.hengtacc.object.storage.core.config;

import com.hengtacc.object.storage.core.exceptions.ObjectStorageConfigPropertyException;

/**
 * 服务商公共参数,提供访问参数读取
 * <p>
 * 其子类目前提供其细化的配置读取
 * </p>
 *
 * @author Kuang HaiBo 2023/11/2 11:29
 */
public abstract class AbstractProviderProperties {

	/**
	 * Server endpoint
	 */
	private String endpoint;

	/**
	 * Server accessKey
	 */
	private String accessKey;

	/**
	 * Server secretKey
	 */
	private String secretKey;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * 验证参数是否合规
	 * @param propertyPrefix 参数前缀
	 */
	public void verify(String propertyPrefix) {
		ObjectStorageConfigPropertyException.assertNotBlank(propertyPrefix + ".endpoint", endpoint);
		ObjectStorageConfigPropertyException.assertNotBlank(propertyPrefix + ".accessKey", accessKey);
		ObjectStorageConfigPropertyException.assertNotBlank(propertyPrefix + ".secretKey", secretKey);
	}

	@Override
	public String toString() {
		return "AbstractProviderProperties{" + "endpoint='" + endpoint + '\'' + ", accessKey='" + accessKey + '\''
				+ ", secretKey='" + secretKey + '\'' + '}';
	}

}
