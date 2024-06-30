package com.hengtacc.object.storage.huawei.client;

import com.hengtacc.object.storage.core.provider.factory.ProviderClientFactory;
import com.hengtacc.object.storage.huawei.config.HuaWeiObsProperties;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 用于创建obs客户端的工厂
 *
 * @author Kuang HaiBo 2024/6/21 14:33
 */
public class ObsClientFactory implements ProviderClientFactory<ObsClient> {

	private final static Logger log = LoggerFactory.getLogger(ObsClientFactory.class);

	/**
	 * 存储obs访问密钥
	 */
	private final HuaWeiObsProperties obsConfig;

	/**
	 * 用于配置obs客户端的配置
	 */
	private final ObsConfiguration obsConfiguration;

	public ObsClientFactory(HuaWeiObsProperties obsConfig, ObsConfiguration obsConfiguration) {
		this.obsConfig = obsConfig;
		this.obsConfiguration = obsConfiguration;
	}

	/**
	 * 创建一个实例，非空
	 * <p>
	 * 通过使用该接口的实例，可以创建一个新的客户端
	 * </p>
	 * @return 实例
	 */
	@Override
	public ObsClient create() {
		try {
			obsConfiguration.getEndPoint();
		}
		catch (IllegalArgumentException e) {
			// set endPoint again
			obsConfiguration.setEndPoint(obsConfig.getEndpoint());
		}
		return new ObsClient(obsConfig.getAccessKey(), obsConfig.getSecretKey(), obsConfiguration);
	}

	/**
	 * 销毁，集中销毁实例
	 * @param instance 关闭
	 */
	@Override
	public void destroy(ObsClient instance) {
		log.info("==> shutdown obs client ");
		if (Objects.nonNull(instance)) {
			try {
				log.info("==> [Obs Close] HuaWei Obs Client Close");
				instance.close();
			}
			catch (IOException e) {
				log.error("==> [Obs Close] 关闭华为obs客户端异常:", e);
			}
		}
	}

}
