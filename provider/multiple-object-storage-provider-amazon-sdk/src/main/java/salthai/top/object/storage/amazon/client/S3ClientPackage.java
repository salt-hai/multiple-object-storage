package salthai.top.object.storage.amazon.client;

import salthai.top.object.storage.amazon.config.S3ConnectionProperties;
import salthai.top.object.storage.amazon.config.S3Properties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.Protocol;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.SdkHttpConfigurationOption;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.utils.AttributeMap;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

/**
 * S3 客户端
 * <P>
 * AWS SDK for Java 2.x 提供了更高级的传输管理器
 * 使用该客户端{@link software.amazon.awssdk.services.s3.S3AsyncClient }进行构建
 * {@link software.amazon.awssdk.transfer.s3.S3TransferManager }, 部分上传将使用更高级的api
 * </P>
 *
 * @author Kuang HaiBo 2024/1/10 9:57
 */
public class S3ClientPackage implements AutoCloseable {

	/**
	 * 基础的 S3客户端用于 普通处理
	 */
	private final S3Client s3Client;

	/**
	 * 提供的高级文件传输工具， 部分上传使用
	 */
	private final S3TransferManager s3TransferManager;

	/**
	 * 预签名工具 生成可以 get put 或其他操作的url
	 */
	private final S3Presigner s3Presigner;

	/**
	 * 通过配置文件构建
	 * @param s3Properties 存储配置
	 * @param s3ConnectionProperties 链接参数
	 */
	public S3ClientPackage(S3Properties s3Properties, S3ConnectionProperties s3ConnectionProperties) {
		Protocol httpProtocol = Protocol.HTTP1_1;
		// 异步客户端 配置链接参数
		SdkAsyncHttpClient asyncHttpClient = NettyNioAsyncHttpClient.builder()
			.connectionTimeout(Duration.ofMillis(s3ConnectionProperties.getConnectionTimeout()))
			.maxConcurrency(s3ConnectionProperties.getMaxConnections())
			.protocol(httpProtocol)
			.build();

		S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
			.credentialsProvider(StaticCredentialsProvider
				.create(AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
			.serviceConfiguration(S3Configuration.builder()
				.pathStyleAccessEnabled(s3Properties.getPathStyleAccess())
				.chunkedEncodingEnabled(s3Properties.getChunkedEncoding())
				.build())
			.region(Region.of(s3Properties.getRegion()))
			.endpointOverride(URI.create(s3Properties.getEndpoint()))
			.httpClient(asyncHttpClient)
			.build();
		// 高级传输将使用异步 http客户端
		s3TransferManager = S3TransferManager.builder().s3Client(s3AsyncClient).build();

		// 普通 http 客户端
		SdkHttpClient syncHttpClient = ApacheHttpClient.builder()
			.connectionTimeout(Duration.ofMillis(s3ConnectionProperties.getConnectionTimeout()))
			.maxConnections(s3ConnectionProperties.getMaxConnections())
			.buildWithDefaults(AttributeMap.builder().put(SdkHttpConfigurationOption.PROTOCOL, httpProtocol).build());

		s3Client = S3Client.builder()
			.credentialsProvider(StaticCredentialsProvider
				.create(AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
			.serviceConfiguration(S3Configuration.builder()
				.pathStyleAccessEnabled(s3Properties.getPathStyleAccess())
				.chunkedEncodingEnabled(s3Properties.getChunkedEncoding())
				.build())
			.region(Region.of(s3Properties.getRegion()))
			.endpointOverride(URI.create(s3Properties.getEndpoint()))
			.httpClient(syncHttpClient)
			.build();

		s3Presigner = S3Presigner.builder()
			.credentialsProvider(StaticCredentialsProvider
				.create(AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
			.serviceConfiguration(S3Configuration.builder()
				.pathStyleAccessEnabled(s3Properties.getPathStyleAccess())
				.chunkedEncodingEnabled(s3Properties.getChunkedEncoding())
				.build())
			.region(Region.of(s3Properties.getRegion()))
			.endpointOverride(URI.create(s3Properties.getEndpoint()))
			.build();
	}

	public S3Client getS3Client() {
		return s3Client;
	}

	public S3TransferManager getS3TransferManager() {
		return s3TransferManager;
	}

	public S3Presigner getS3Presigner() {
		return s3Presigner;
	}

	/**
	 * 自动关闭
	 * @throws Exception 关闭过程异常
	 */
	@Override
	public void close() throws Exception {
		if (Objects.nonNull(s3Client)) {
			s3Client.close();
		}
		if (Objects.nonNull(s3Presigner)) {
			s3Presigner.close();
		}
		if (Objects.nonNull(s3TransferManager)) {
			s3TransferManager.close();
		}
	}

}
