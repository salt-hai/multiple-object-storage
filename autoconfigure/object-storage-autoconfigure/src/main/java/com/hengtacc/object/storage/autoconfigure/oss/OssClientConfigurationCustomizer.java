package com.hengtacc.object.storage.autoconfigure.oss;

import com.aliyun.oss.ClientBuilderConfiguration;

/**
 * oss 配置自定义的配置
 *
 * @author Kuang HaiBo 2024/6/3 16:52
 */
public interface OssClientConfigurationCustomizer {

	/**
	 * Customize the {@link ClientBuilderConfiguration}.,使用该配置可以自定义oss的配置
	 * @param clientBuilderConfiguration the builder to customize
	 */
	void customize(ClientBuilderConfiguration clientBuilderConfiguration);

}
