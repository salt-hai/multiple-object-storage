package salthai.top.object.storage.autoconfigure.obs;

import jakarta.annotation.PostConstruct;
import salthai.top.object.storage.autoconfigure.ProviderCondition;
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.huawei.operations.ObsBucketOperation;
import salthai.top.object.storage.huawei.operations.ObsObjectMultipartOperations;
import salthai.top.object.storage.huawei.operations.ObsObjectOperations;
import com.obs.services.ObsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * obs 配置
 *
 * @author HaiBo Kuang 2023/11/11 17:30
 */
@ConditionalOnMissingBean(ProviderClientManager.class)
@ConditionalOnClass(value = { ObsClient.class, ProviderClientManager.class })
@Conditional(ProviderCondition.class)
@Configuration(proxyBeanMethods = false)
@Import({ ObsClientConfiguration.class })
@ComponentScan(basePackages = { "salthai.top.object.storage.huawei.service" })
public class ObsConfiguration {

	private final static Logger log = LoggerFactory.getLogger(ObsConfiguration.class);

	@PostConstruct
	void init() {
		log.info("==========[HuaWeiObs Auto Configure]===========");
	}

	/**
	 * 对象操作
	 * @param client 客户端
	 * @return 对象操作实例
	 */
	@ConditionalOnMissingBean(ObjectOperations.class)
	@Bean
	public ObsObjectOperations objectOperations(ProviderClientManager<ObsClient> client) {
		return new ObsObjectOperations(client);
	}

	/**
	 * 桶操作
	 * @param client 客户端
	 * @return 桶操作实例
	 */
	@ConditionalOnMissingBean(ObsBucketOperation.class)
	@Bean
	public ObsBucketOperation obsBucketOperation(ProviderClientManager<ObsClient> client) {
		return new ObsBucketOperation(client);
	}

	/**
	 * 对象分片操作
	 * @param client 客户端管理
	 * @return 分片操作
	 */
	@ConditionalOnMissingBean
	@Bean
	public ObsObjectMultipartOperations obsObjectMultipartOperations(ProviderClientManager<ObsClient> client) {
		return new ObsObjectMultipartOperations(client);
	}

}
