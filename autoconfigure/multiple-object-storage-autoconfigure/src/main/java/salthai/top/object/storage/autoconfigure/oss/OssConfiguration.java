package salthai.top.object.storage.autoconfigure.oss;

import com.aliyun.oss.OSS;
import salthai.top.object.storage.aliyun.operations.OssBucketOperations;
import salthai.top.object.storage.aliyun.operations.OssObjectMultipartOperations;
import salthai.top.object.storage.aliyun.operations.OssObjectOperations;
import salthai.top.object.storage.autoconfigure.ProviderCondition;
import salthai.top.object.storage.core.provider.ProviderClientManager;
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
 * 阿里云的配置
 *
 * @author Kuang HaiBo 2023/11/3 10:34
 */
@ConditionalOnMissingBean(ProviderClientManager.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = { OSS.class, ProviderClientManager.class })
@Import({ OssClientConfiguration.class })
@ComponentScan(basePackages = { "salthai.top.object.storage.aliyun.service" })
@Conditional(value = { ProviderCondition.class })
public class OssConfiguration {

	private final static Logger log = LoggerFactory.getLogger(OssConfiguration.class);

	@PostConstruct
	void init() {
		log.info("==========[AliYunOss Auto Configure]===========");
	}

	/**
	 * 对象操作
	 * @param client 客户端
	 * @return 对象操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	OssObjectOperations objectOperations(ProviderClientManager<OSS> client) {
		return new OssObjectOperations(client);
	}

	/**
	 * 对象桶操作
	 * @param client 客户端
	 * @return 对象桶操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	OssBucketOperations bucketOperation(ProviderClientManager<OSS> client) {
		return new OssBucketOperations(client);
	}

	/**
	 * 对象分片操作
	 * @param client 客户端
	 * @return 对象分片操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	OssObjectMultipartOperations objectMultipartOperations(ProviderClientManager<OSS> client) {
		return new OssObjectMultipartOperations(client);
	}

}
