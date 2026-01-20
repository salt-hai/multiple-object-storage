package salthai.top.object.storage.amazon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.client.S3ClientPackageFactory;
import salthai.top.object.storage.amazon.config.S3Properties;
import software.amazon.awssdk.services.s3.S3Configuration;

/**
 * S3 客户端工厂测试
 *
 * @author Kuang HaiBo 2024/1/20 17:35
 */
class S3ClientPackageFactoryTest {

	private S3Properties s3Properties;

	private S3Configuration s3Configuration;

	@BeforeEach
	void setUp() {
		s3Properties = new S3Properties();
		s3Properties.setEndpoint("https://s3.amazonaws.com");
		s3Properties.setAccessKey("test-access-key");
		s3Properties.setSecretKey("test-secret-key");
		s3Properties.setRegion("us-east-1");

		s3Configuration = S3Configuration.builder().pathStyleAccessEnabled(true).build();
	}

	@Test
	void testCreateClientPackageFactory() {
		// 测试创建 S3ClientPackageFactory
		S3ClientPackageFactory factory = new S3ClientPackageFactory(s3Properties, s3Configuration);
		Assertions.assertNotNull(factory);
	}

	@Test
	void testCreateClientPackage() {
		// 测试创建 S3ClientPackage 实例
		S3ClientPackageFactory factory = new S3ClientPackageFactory(s3Properties, s3Configuration);
		S3ClientPackage clientPackage = factory.create();

		Assertions.assertNotNull(clientPackage);
		Assertions.assertNotNull(clientPackage.getS3Client());
		Assertions.assertNotNull(clientPackage.getS3Presigner());
		Assertions.assertNotNull(clientPackage.getS3TransferManager());
	}

	@Test
	void testDestroyClientPackage() {
		// 测试销毁 S3ClientPackage 实例
		S3ClientPackageFactory factory = new S3ClientPackageFactory(s3Properties, s3Configuration);
		S3ClientPackage clientPackage = factory.create();

		Assertions.assertDoesNotThrow(() -> factory.destroy(clientPackage));
	}

	@Test
	void testDestroyNullClientPackage() {
		// 测试销毁 null 实例（不应抛出异常）
		S3ClientPackageFactory factory = new S3ClientPackageFactory(s3Properties, s3Configuration);
		Assertions.assertDoesNotThrow(() -> factory.destroy(null));
	}

	@Test
	void testCreateWithCustomConfiguration() {
		// 测试使用自定义配置创建客户端
		S3Configuration customConfig = S3Configuration.builder()
			.pathStyleAccessEnabled(false)
			.checksumValidationEnabled(true)
			.build();

		S3ClientPackageFactory factory = new S3ClientPackageFactory(s3Properties, customConfig);
		S3ClientPackage clientPackage = factory.create();

		Assertions.assertNotNull(clientPackage);
		Assertions.assertNotNull(clientPackage.getS3Client());
	}

}
