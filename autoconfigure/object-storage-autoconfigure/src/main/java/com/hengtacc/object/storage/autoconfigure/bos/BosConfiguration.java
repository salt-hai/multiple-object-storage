package com.hengtacc.object.storage.autoconfigure.bos;

import com.baidubce.services.bos.BosClient;
import com.hengtacc.object.storage.autoconfigure.ProviderCondition;
import com.hengtacc.object.storage.baidu.operations.BosBucketOperations;
import com.hengtacc.object.storage.baidu.operations.BosObjectMultipartOperations;
import com.hengtacc.object.storage.baidu.operations.BosObjectOperations;
import com.hengtacc.object.storage.core.operations.BucketOperation;
import com.hengtacc.object.storage.core.operations.ObjectMultipartOperations;
import com.hengtacc.object.storage.core.operations.ObjectOperations;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * 百度 bos 自动配置
 *
 * @author HaiBo Kuang 2023/11/11 17:46
 */
@ConditionalOnMissingBean(ProviderClientManager.class)
@ConditionalOnClass(value = { BosClient.class, ProviderClientManager.class })
@Configuration(proxyBeanMethods = false)
@Import({ BosClientConfiguration.class })
@ComponentScan(basePackages = { "com.hengtacc.object.storage.baidu.service" })
@Conditional(ProviderCondition.class)
public class BosConfiguration {

	private final static Logger log = LoggerFactory.getLogger(BosConfiguration.class);

	@PostConstruct
	void init() {
		log.info("==========[BaiDuBos Auto Configure]===========");
	}

	/**
	 * 对象操作
	 * @param client 客户端
	 * @return 对象操作实例
	 */
	@ConditionalOnMissingBean(ObjectOperations.class)
	@Bean
	public BosObjectOperations objectOperations(ProviderClientManager<BosClient> client) {
		return new BosObjectOperations(client);
	}

	/**
	 * bos bucket 操作
	 * @param client 客户端
	 * @return bucket 操作实例
	 */
	@ConditionalOnMissingBean(BucketOperation.class)
	@Bean
	public BosBucketOperations bucketOperations(ProviderClientManager<BosClient> client) {
		return new BosBucketOperations(client);
	}

	/**
	 * bos 兼容的分片操作
	 * @param client 客户端
	 * @return 分片操作实例
	 */
	@ConditionalOnMissingBean(ObjectMultipartOperations.class)
	@Bean
	public BosObjectMultipartOperations multipartOperations(ProviderClientManager<BosClient> client) {
		return new BosObjectMultipartOperations(client);
	}

}
