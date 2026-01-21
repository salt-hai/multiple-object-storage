package salthai.top.object.storage.huawei.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.constants.StorageConstants;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 华为云 OBS 配置属性测试
 * <p>
 * 测试华为云 OBS 配置属性的各种场景
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 11:30
 */
@DisplayName("华为云 OBS 配置属性测试")
class HuaWeiObsPropertiesTest {

	private HuaWeiObsProperties properties;

	private static final String TEST_PROPERTY_PREFIX = "multiple.object.storage.huawei";

	@BeforeEach
	void setUp() {
		properties = new HuaWeiObsProperties();
	}

	@Test
	@DisplayName("测试创建华为云 OBS 配置对象")
	void testCreateProperties() {
		assertNotNull(properties, "华为云 OBS 配置对象不应为 null");
	}

	@Test
	@DisplayName("测试设置和获取 endpoint")
	void testSetAndGetEndpoint() {
		String endpoint = "https://obs.cn-north-4.myhuaweicloud.com";
		properties.setEndpoint(endpoint);

		assertEquals(endpoint, properties.getEndpoint(), "endpoint 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 accessKey")
	void testSetAndGetAccessKey() {
		String accessKey = "AAAAAAAAAAAAAAAAAAAAAA";
		properties.setAccessKey(accessKey);

		assertEquals(accessKey, properties.getAccessKey(), "accessKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 secretKey")
	void testSetAndGetSecretKey() {
		String secretKey = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
		properties.setSecretKey(secretKey);

		assertEquals(secretKey, properties.getSecretKey(), "secretKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试验证完整配置")
	void testVerifyValidConfiguration() {
		properties.setEndpoint("https://obs.cn-south-1.myhuaweicloud.com");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX), "完整的配置验证应该成功");
	}

	@Test
	@DisplayName("测试验证缺少 endpoint 应该失败")
	void testVerifyMissingEndpoint() {
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");

		assertThrows(Exception.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"缺少 endpoint 时验证应该失败");
	}

	@Test
	@DisplayName("测试 toString 方法")
	void testToString() {
		properties.setEndpoint("https://obs.cn-east-3.myhuaweicloud.com");
		properties.setAccessKey("HW_ACCESS_KEY");
		properties.setSecretKey("HW_SECRET_KEY");

		String toString = properties.toString();

		assertTrue(toString.contains("https://obs.cn-east-3.myhuaweicloud.com"), "toString 应该包含 endpoint");
		assertTrue(toString.contains("HW_ACCESS_KEY"), "toString 应该包含 accessKey");
		assertTrue(toString.contains("HW_SECRET_KEY"), "toString 应该包含 secretKey");
	}

	@Test
	@DisplayName("测试华为云不同区域的 endpoint")
	void testDifferentHuaweiRegions() {
		String[] endpoints = { "https://obs.cn-north-4.myhuaweicloud.com",
				"https://obs.cn-south-1.myhuaweicloud.com", "https://obs.cn-east-3.myhuaweicloud.com",
				"https://obs.ap-southeast-1.myhuaweicloud.com" };

		for (String endpoint : endpoints) {
			HuaWeiObsProperties props = new HuaWeiObsProperties();
			props.setEndpoint(endpoint);
			assertEquals(endpoint, props.getEndpoint());
		}
	}

	@Test
	@DisplayName("测试华为云提供者常量")
	void testHuaweiProviderConstant() {
		assertEquals("multiple.object.storage.huawei", StorageConstants.HUAWEI_PROVIDER);
	}

	@Test
	@DisplayName("测试完整华为云 OBS 配置")
	void testCompleteConfiguration() {
		properties.setEndpoint("https://obs.cn-north-4.myhuaweicloud.com");
		properties.setAccessKey("HUAWEI_ACCESS_KEY_ID");
		properties.setSecretKey("HUAWEI_SECRET_ACCESS_KEY");

		assertEquals("https://obs.cn-north-4.myhuaweicloud.com", properties.getEndpoint());
		assertEquals("HUAWEI_ACCESS_KEY_ID", properties.getAccessKey());
		assertEquals("HUAWEI_SECRET_ACCESS_KEY", properties.getSecretKey());

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试多次更新配置属性")
	void testUpdatePropertiesMultipleTimes() {
		properties.setEndpoint("https://obs1.myhuaweicloud.com");
		assertEquals("https://obs1.myhuaweicloud.com", properties.getEndpoint());

		properties.setEndpoint("https://obs2.myhuaweicloud.com");
		assertEquals("https://obs2.myhuaweicloud.com", properties.getEndpoint());

		properties.setAccessKey("key1");
		assertEquals("key1", properties.getAccessKey());

		properties.setAccessKey("key2");
		assertEquals("key2", properties.getAccessKey());
	}

}
