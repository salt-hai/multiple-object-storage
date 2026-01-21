package salthai.top.object.storage.core.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.exceptions.ObjectStorageConfigPropertyException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 抽象提供者配置属性测试
 * <p>
 * 测试配置属性的 getter/setter、验证和 toString 方法
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 09:30
 */
@DisplayName("抽象提供者配置属性测试")
class AbstractProviderPropertiesTest {

	/**
	 * 测试用的实现类
	 */
	static class TestProviderProperties extends AbstractProviderProperties {
		// 继承所有功能
	}

	private TestProviderProperties properties;

	private static final String TEST_PROPERTY_PREFIX = "test.storage";

	@BeforeEach
	void setUp() {
		properties = new TestProviderProperties();
	}

	@Test
	@DisplayName("测试设置和获取 endpoint")
	void testSetAndGetEndpoint() {
		String endpoint = "https://s3.amazonaws.com";
		properties.setEndpoint(endpoint);

		assertEquals(endpoint, properties.getEndpoint(), "endpoint 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 accessKey")
	void testSetAndGetAccessKey() {
		String accessKey = "AKIAIOSFODNN7EXAMPLE";
		properties.setAccessKey(accessKey);

		assertEquals(accessKey, properties.getAccessKey(), "accessKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 secretKey")
	void testSetAndGetSecretKey() {
		String secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
		properties.setSecretKey(secretKey);

		assertEquals(secretKey, properties.getSecretKey(), "secretKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试验证完整配置 - 成功场景")
	void testVerifyValidConfiguration() {
		// 设置完整的配置
		properties.setEndpoint("https://oss-cn-hangzhou.aliyuncs.com");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");

		// 验证应该不抛出异常
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX),
				"完整的配置验证应该成功");
	}

	@Test
	@DisplayName("测试验证配置 - endpoint 为空应该失败")
	void testVerifyEmptyEndpoint() {
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");
		// endpoint 为 null

		ObjectStorageConfigPropertyException exception = assertThrows(
				ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"endpoint 为空时应该抛出异常");

		assertTrue(exception.getMessage().contains("endpoint"), "异常信息应该包含 endpoint");
	}

	@Test
	@DisplayName("测试验证配置 - endpoint 为空白字符串应该失败")
	void testVerifyBlankEndpoint() {
		properties.setEndpoint("   ");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");

		assertThrows(ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"endpoint 为空白字符串时应该抛出异常");
	}

	@Test
	@DisplayName("测试验证配置 - accessKey 为空应该失败")
	void testVerifyEmptyAccessKey() {
		properties.setEndpoint("https://s3.amazonaws.com");
		properties.setSecretKey("test-secret-key");
		// accessKey 为 null

		ObjectStorageConfigPropertyException exception = assertThrows(
				ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"accessKey 为空时应该抛出异常");

		assertTrue(exception.getMessage().contains("accessKey"), "异常信息应该包含 accessKey");
	}

	@Test
	@DisplayName("测试验证配置 - accessKey 为空白字符串应该失败")
	void testVerifyBlankAccessKey() {
		properties.setEndpoint("https://s3.amazonaws.com");
		properties.setAccessKey("");
		properties.setSecretKey("test-secret-key");

		assertThrows(ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"accessKey 为空白字符串时应该抛出异常");
	}

	@Test
	@DisplayName("测试验证配置 - secretKey 为空应该失败")
	void testVerifyEmptySecretKey() {
		properties.setEndpoint("https://s3.amazonaws.com");
		properties.setAccessKey("test-access-key");
		// secretKey 为 null

		ObjectStorageConfigPropertyException exception = assertThrows(
				ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"secretKey 为空时应该抛出异常");

		assertTrue(exception.getMessage().contains("secretKey"), "异常信息应该包含 secretKey");
	}

	@Test
	@DisplayName("测试验证配置 - secretKey 为空白字符串应该失败")
	void testVerifyBlankSecretKey() {
		properties.setEndpoint("https://s3.amazonaws.com");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("  ");

		assertThrows(ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"secretKey 为空白字符串时应该抛出异常");
	}

	@Test
	@DisplayName("测试验证配置 - 所有字段为空应该失败")
	void testVerifyAllFieldsEmpty() {
		// 所有字段都为 null

		ObjectStorageConfigPropertyException exception = assertThrows(
				ObjectStorageConfigPropertyException.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"所有字段为空时应该抛出异常");

		// 异常信息应该提到第一个缺失的字段
		assertTrue(exception.getMessage().contains(TEST_PROPERTY_PREFIX), "异常信息应该包含属性前缀");
	}

	@Test
	@DisplayName("测试 toString 方法 - 完整配置")
	void testToStringWithFullConfiguration() {
		properties.setEndpoint("https://obs.cn-north-4.myhuaweicloud.com");
		properties.setAccessKey("ACCESS_KEY_ID");
		properties.setSecretKey("SECRET_ACCESS_KEY");

		String toString = properties.toString();

		assertTrue(toString.contains("https://obs.cn-north-4.myhuaweicloud.com"), "toString 应该包含 endpoint");
		assertTrue(toString.contains("ACCESS_KEY_ID"), "toString 应该包含 accessKey");
		assertTrue(toString.contains("SECRET_ACCESS_KEY"), "toString 应该包含 secretKey");
	}

	@Test
	@DisplayName("测试 toString 方法 - 空配置")
	void testToStringWithEmptyConfiguration() {
		String toString = properties.toString();

		assertNotNull(toString, "toString 不应返回 null");
		assertTrue(toString.contains("AbstractProviderProperties"), "toString 应该包含类名");
	}

	@Test
	@DisplayName("测试 toString 方法 - 部分配置")
	void testToStringWithPartialConfiguration() {
		properties.setEndpoint("https://endpoint.example.com");
		// accessKey 和 secretKey 为 null

		String toString = properties.toString();

		assertTrue(toString.contains("https://endpoint.example.com"), "toString 应该包含已设置的 endpoint");
	}

	@Test
	@DisplayName("测试设置多个配置属性")
	void testSetMultipleProperties() {
		String endpoint = "https://s3.cn-north-1.amazonaws.com.cn";
		String accessKey = "MY_ACCESS_KEY";
		String secretKey = "MY_SECRET_KEY";

		properties.setEndpoint(endpoint);
		properties.setAccessKey(accessKey);
		properties.setSecretKey(secretKey);

		assertEquals(endpoint, properties.getEndpoint());
		assertEquals(accessKey, properties.getAccessKey());
		assertEquals(secretKey, properties.getSecretKey());
	}

	@Test
	@DisplayName("测试覆盖已设置的属性")
	void testOverrideExistingProperties() {
		properties.setEndpoint("https://first-endpoint.com");
		properties.setAccessKey("first-key");
		properties.setSecretKey("first-secret");

		// 覆盖设置
		properties.setEndpoint("https://second-endpoint.com");
		properties.setAccessKey("second-key");
		properties.setSecretKey("second-secret");

		assertEquals("https://second-endpoint.com", properties.getEndpoint());
		assertEquals("second-key", properties.getAccessKey());
		assertEquals("second-secret", properties.getSecretKey());
	}

	@Test
	@DisplayName("测试不同属性前缀的验证")
	void testVerifyWithDifferentPropertyPrefixes() {
		properties.setEndpoint("https://test.com");
		properties.setAccessKey("key");
		properties.setSecretKey("secret");

		// 使用不同的前缀验证
		assertDoesNotThrow(() -> properties.verify("aliyun.oss"));
		assertDoesNotThrow(() -> properties.verify("amazon.s3"));
		assertDoesNotThrow(() -> properties.verify("huawei.obs"));
	}

	@Test
	@DisplayName("测试包含特殊字符的配置值")
	void testPropertiesWithSpecialCharacters() {
		properties.setEndpoint("https://s3.cn-north-1.amazonaws.com.cn:443");
		properties.setAccessKey("AKIA/Test@Key#123");
		properties.setSecretKey("secret/key/with/slashes");

		assertEquals("https://s3.cn-north-1.amazonaws.com.cn:443", properties.getEndpoint());
		assertEquals("AKIA/Test@Key#123", properties.getAccessKey());
		assertEquals("secret/key/with/slashes", properties.getSecretKey());

		// 验证应该通过
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试非常长的配置值")
	void testPropertiesWithLongValues() {
		StringBuilder longValue = new StringBuilder();
		for (int i = 0; i < 1000; i++) {
			longValue.append("a");
		}
		String longString = longValue.toString();

		properties.setEndpoint("https://test.com");
		properties.setAccessKey(longString);
		properties.setSecretKey(longString);

		assertEquals(longString, properties.getAccessKey());
		assertEquals(longString, properties.getSecretKey());

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试多次验证同一配置对象")
	void testVerifyMultipleTimes() {
		properties.setEndpoint("https://repeat-test.com");
		properties.setAccessKey("repeat-key");
		properties.setSecretKey("repeat-secret");

		// 多次验证应该都成功
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试空字符串前缀的验证")
	void testVerifyWithEmptyPrefix() {
		properties.setEndpoint("https://test.com");
		properties.setAccessKey("key");
		properties.setSecretKey("secret");

		// 空字符串前缀也应该能工作
		assertDoesNotThrow(() -> properties.verify(""));
	}

}
