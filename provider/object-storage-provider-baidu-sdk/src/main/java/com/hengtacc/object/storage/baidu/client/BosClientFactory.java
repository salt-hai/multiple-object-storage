package com.hengtacc.object.storage.baidu.client;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.hengtacc.object.storage.baidu.config.BaiDuBosProperties;
import com.hengtacc.object.storage.core.provider.factory.ProviderClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 用于创建bos客户端的工厂
 *
 * @author Kuang HaiBo 2024/6/21 16:49
 */
public class BosClientFactory implements ProviderClientFactory<BosClient> {

	private final static Logger log = LoggerFactory.getLogger(BosClientFactory.class);

	private final BaiDuBosProperties properties;

	private final BosClientConfiguration bosClientConfiguration;

	public BosClientFactory(BaiDuBosProperties properties, BosClientConfiguration bosClientConfiguration) {
		this.bosClientConfiguration = bosClientConfiguration;
		this.properties = properties;
	}

	/**
	 * 创建一个实例，非空
	 * <p>
	 * 通过使用该接口的实例，可以创建一个新的客户端
	 * </p>
	 * @return 实例
	 */
	@Override
	public BosClient create() {
		return buildClient(properties, bosClientConfiguration);
	}

	/**
	 * 销毁，集中销毁实例
	 * @param instance 关闭
	 */
	@Override
	public void destroy(BosClient instance) {
		log.info("==> shutdown bos client");
		if (Objects.nonNull(instance)) {
			instance.shutdown();
		}
	}

	/**
	 * build http client
	 * @param properties bos access config properties
	 * @param bosClientConfiguration customer provide client config
	 * @return bos client
	 */
	BosClient buildClient(BaiDuBosProperties properties, BosClientConfiguration bosClientConfiguration) {
		// double check
		if (Objects.isNull(bosClientConfiguration.getCredentials())) {
			bosClientConfiguration
				.setCredentials(new DefaultBceCredentials(properties.getAccessKey(), properties.getSecretKey()));
		}
		if (Objects.isNull(bosClientConfiguration.getEndpoint())) {
			bosClientConfiguration.setEndpoint(properties.getEndpoint());
		}
		return new BosClient(bosClientConfiguration);
	}

}
