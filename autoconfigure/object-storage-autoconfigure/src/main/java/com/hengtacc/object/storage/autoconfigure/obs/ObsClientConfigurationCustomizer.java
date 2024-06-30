package com.hengtacc.object.storage.autoconfigure.obs;

import com.obs.services.ObsConfiguration;

/**
 * 用于自定义obs的配置
 *
 * @author Kuang HaiBo 2024/6/21 14:46
 */
public interface ObsClientConfigurationCustomizer {

	/**
	 * Customize the {@link ObsConfiguration}.,使用该配置可以自定义obs的配置
	 * @param obsConfiguration the builder to customize
	 */
	void customize(ObsConfiguration obsConfiguration);

}
