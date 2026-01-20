package salthai.top.object.storage.amazon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.core.constants.StorageConstants;

/**
 * S3 配置属性测试
 *
 * @author Kuang HaiBo 2024/1/20 17:30
 */
class S3PropertiesTest {

	private S3Properties s3Properties;

	@BeforeEach
	void setUp() {
		s3Properties = new S3Properties();
	}

	@Test
	void testDefaultRegion() {
		// 测试默认区域设置
		Assertions.assertNotNull(s3Properties.getRegion());
		Assertions.assertEquals("cn-north-1", s3Properties.getRegion());
	}

	@Test
	void testCustomRegion() {
		// 测试自定义区域设置
		s3Properties.setRegion("us-west-2");
		Assertions.assertEquals("us-west-2", s3Properties.getRegion());
	}

	@Test
	void testEndpoint() {
		// 测试端点设置
		s3Properties.setEndpoint("https://s3.amazonaws.com");
		Assertions.assertEquals("https://s3.amazonaws.com", s3Properties.getEndpoint());
	}

	@Test
	void testAccessKey() {
		// 测试访问密钥设置
		s3Properties.setAccessKey("test-access-key");
		Assertions.assertEquals("test-access-key", s3Properties.getAccessKey());
	}

	@Test
	void testSecretKey() {
		// 测试秘密密钥设置
		s3Properties.setSecretKey("test-secret-key");
		Assertions.assertEquals("test-secret-key", s3Properties.getSecretKey());
	}

	@Test
	void testVerifyValidProperties() {
		// 测试验证有效的属性
		s3Properties.setEndpoint("https://s3.amazonaws.com");
		s3Properties.setAccessKey("test-access-key");
		s3Properties.setSecretKey("test-secret-key");
		Assertions.assertDoesNotThrow(() -> s3Properties.verify(StorageConstants.AMAZON_PROVIDER));
	}

	@Test
	void testVerifyInvalidProperties() {
		// 测试验证无效的属性（缺少必要字段）
		Assertions.assertThrows(Exception.class, () -> s3Properties.verify(StorageConstants.AMAZON_PROVIDER));
	}

	@Test
	void testToString() {
		// 测试 toString 方法
		s3Properties.setRegion("us-west-2");
		s3Properties.setEndpoint("https://s3.amazonaws.com");
		s3Properties.setAccessKey("test-access-key");
		s3Properties.setSecretKey("test-secret-key");

		String toString = s3Properties.toString();
		Assertions.assertTrue(toString.contains("us-west-2"));
		Assertions.assertTrue(toString.contains("https://s3.amazonaws.com"));
		Assertions.assertTrue(toString.contains("test-access-key"));
	}

}
