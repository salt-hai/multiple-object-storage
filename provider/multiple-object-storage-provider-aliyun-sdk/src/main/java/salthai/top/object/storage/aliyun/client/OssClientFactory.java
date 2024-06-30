package salthai.top.object.storage.aliyun.client;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyuncs.exceptions.ClientException;
import salthai.top.object.storage.aliyun.config.AliYunOssProperties;
import salthai.top.object.storage.core.exceptions.ObjectClientException;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 用于创建oss客户端的工厂
 *
 * @author Kuang HaiBo 2024/6/4 11:35
 */
public class OssClientFactory implements ProviderClientFactory<OSS> {

	private final static Logger log = LoggerFactory.getLogger(OssClientFactory.class);

	/**
	 * 阿里云访问配置文件
	 */
	private final AliYunOssProperties aliYunOssProperties;

	/**
	 * 可自定义的一些配置属性
	 */
	private final ClientBuilderConfiguration builderConfiguration;

	public OssClientFactory(AliYunOssProperties aliYunOssProperties, ClientBuilderConfiguration builderConfiguration) {
		this.aliYunOssProperties = aliYunOssProperties;
		this.builderConfiguration = builderConfiguration;
	}

	/**
	 * 创建一个实例，非空
	 * <p>
	 * 通过使用该接口的实例，可以创建一个新的客户端
	 * </p>
	 * @return 实例
	 */
	@Override
	public OSS create() {
		return buildNewOss(aliYunOssProperties, builderConfiguration);
	}

	/**
	 * 销毁，集中销毁实例
	 * @param instance 关闭
	 */
	@Override
	public void destroy(OSS instance) {
		log.info("==> shutdown oss client");
		if (Objects.nonNull(instance)) {
			instance.shutdown();
		}
	}

	/**
	 * 构建一个Oss对象
	 * @param aliYunOssProperties oss配置文件
	 * @param builderConfiguration 创建客户端配置属性
	 * @return OssClient
	 */
	OSS buildNewOss(AliYunOssProperties aliYunOssProperties, ClientBuilderConfiguration builderConfiguration) {
		CredentialsProvider credentialsProvider;
		if (aliYunOssProperties.getEnableTempAccess()) {
			Objects.requireNonNull(aliYunOssProperties.getStsRoleArn(), "stsRoleArn cant be null");
			Objects.requireNonNull(aliYunOssProperties.getRegion(), "region cant be null");
			try {
				credentialsProvider = CredentialsProviderFactory.newSTSAssumeRoleSessionCredentialsProvider(
						aliYunOssProperties.getRegion(), aliYunOssProperties.getAccessKey(),
						aliYunOssProperties.getSecretKey(), aliYunOssProperties.getStsRoleArn());
			}
			catch (ClientException e) {
				log.error("==> build  oss client error :", e);
				throw new ObjectClientException(e);
			}
		}
		else {
			credentialsProvider = CredentialsProviderFactory
				.newDefaultCredentialProvider(aliYunOssProperties.getAccessKey(), aliYunOssProperties.getSecretKey());
		}
		// 创建OSSClient实例。
		return new OSSClientBuilder().build(aliYunOssProperties.getEndpoint(), credentialsProvider,
				builderConfiguration);
	}

}
