package salthai.top.object.storage.autoconfigure.amazon;

import salthai.top.object.storage.amazon.operations.S3BucketOperations;
import salthai.top.object.storage.amazon.operations.S3ObjectMultipartOperations;
import salthai.top.object.storage.amazon.operations.S3ObjectOperations;
import salthai.top.object.storage.autoconfigure.ProviderCondition;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import software.amazon.awssdk.services.s3.S3Client;
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
 * AWS S3 配置
 *
 * @author Kuang HaiBo 2024/1/11 11:35
 */
@ConditionalOnMissingBean(ProviderClientManager.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = { S3Client.class, ProviderClientManager.class })
@Import({ S3ClientConfiguration.class })
@ComponentScan(basePackages = { "salthai.top.object.storage.amazon.service" })
@Conditional(value = { ProviderCondition.class })
public class S3Configuration {

	private final static Logger log = LoggerFactory.getLogger(S3Configuration.class);

	@PostConstruct
	void init() {
		log.info("==========[Amazon S3 Auto Configure]===========");
	}

	/**
	 * 对象操作
	 * @param client 客户端
	 * @return 对象操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	S3ObjectOperations objectOperations(ProviderClientManager<?> client) {
		return new S3ObjectOperations(client);
	}

	/**
	 * 对象桶操作
	 * @param client 客户端
	 * @return 对象桶操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	S3BucketOperations bucketOperation(ProviderClientManager<?> client) {
		return new S3BucketOperations(client);
	}

	/**
	 * 对象分片操作
	 * @param client 客户端
	 * @return 对象分片操作实例
	 */
	@ConditionalOnMissingBean
	@Bean
	S3ObjectMultipartOperations objectMultipartOperations(ProviderClientManager<?> client) {
		return new S3ObjectMultipartOperations(client);
	}

}
