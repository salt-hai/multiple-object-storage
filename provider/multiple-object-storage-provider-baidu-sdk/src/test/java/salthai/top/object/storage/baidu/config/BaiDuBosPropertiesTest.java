package salthai.top.object.storage.baidu.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.constants.StorageConstants;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 百度云 BOS 配置属性测试
 * <p>
 * 测试百度云 BOS 配置属性的各种场景
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 11:45
 */
@DisplayName("百度云 BOS 配置属性测试")
class BaiDuBosPropertiesTest {

	private BaiDuBosProperties properties;

	private static final String TEST_PROPERTY_PREFIX = "multiple.object.storage.baidu";

	@BeforeEach
	void setUp() {
		properties = new BaiDuBosProperties();
	}

	@Test
	@DisplayName("测试创建百度云 BOS 配置对象")
	void testCreateProperties() {
		assertNotNull(properties, "百度云 BOS 配置对象不应为 null");
	}

	@Test
	@DisplayName("测试设置和获取 endpoint")
	void testSetAndGetEndpoint() {
		String endpoint = "https://bj.bcebos.com";
		properties.setEndpoint(endpoint);

		assertEquals(endpoint, properties.getEndpoint(), "endpoint 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 accessKey")
	void testSetAndGetAccessKey() {
		String accessKey = "BBBBBBBBBBBBBBBBBBBBBBBB";
		properties.setAccessKey(accessKey);

		assertEquals(accessKey, properties.getAccessKey(), "accessKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置和获取 secretKey")
	void testSetAndGetSecretKey() {
		String secretKey = "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS";
		properties.setSecretKey(secretKey);

		assertEquals(secretKey, properties.getSecretKey(), "secretKey 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试验证完整配置")
	void testVerifyValidConfiguration() {
		properties.setEndpoint("https://bj.bcebos.com");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX), "完整的配置验证应该成功");
	}

	@Test
	@DisplayName("测试验证缺少 accessKey 应该失败")
	void testVerifyMissingAccessKey() {
		properties.setEndpoint("https://bj.bcebos.com");
		properties.setSecretKey("test-secret-key");

		assertThrows(Exception.class, () -> properties.verify(TEST_PROPERTY_PREFIX),
				"缺少 accessKey 时验证应该失败");
	}

	@Test
	@DisplayName("测试 toString 方法")
	void testToString() {
		properties.setEndpoint("https://gz.bcebos.com");
		properties.setAccessKey("BD_ACCESS_KEY");
		properties.setSecretKey("BD_SECRET_KEY");

		String toString = properties.toString();

		assertTrue(toString.contains("https://gz.bcebos.com"), "toString 应该包含 endpoint");
		assertTrue(toString.contains("BD_ACCESS_KEY"), "toString 应该包含 accessKey");
		assertTrue(toString.contains("BD_SECRET_KEY"), "toString 应该包含 secretKey");
	}

	@Test
	@DisplayName("测试百度云不同区域的 endpoint")
	void testDifferentBaiDuRegions() {
		String[] endpoints = { "https://bj.bcebos.com", // 北京
				"https://gz.bcebos.com", // 广州
				"https://su.bcebos.com", // 苏州
				"https://fwh.bcebos.com" // 佛山
		};

		for (String endpoint : endpoints) {
			BaiDuBosProperties props = new BaiDuBosProperties();
			props.setEndpoint(endpoint);
			assertEquals(endpoint, props.getEndpoint());
		}
	}

	@Test
	@DisplayName("测试百度云提供者常量")
	void testBaiDuProviderConstant() {
		assertEquals("multiple.object.storage.baidu", StorageConstants.BAIDU_PROVIDER);
	}

	@Test
	@DisplayName("测试完整百度云 BOS 配置")
	void testCompleteConfiguration() {
		properties.setEndpoint("https://bj.bcebos.com");
		properties.setAccessKey("BAIDU_ACCESS_KEY_ID");
		properties.setSecretKey("BAIDU_SECRET_ACCESS_KEY");

		assertEquals("https://bj.bcebos.com", properties.getEndpoint());
		assertEquals("BAIDU_ACCESS_KEY_ID", properties.getAccessKey());
		assertEquals("BAIDU_SECRET_ACCESS_KEY", properties.getSecretKey());

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试多次更新配置属性")
	void testUpdatePropertiesMultipleTimes() {
		properties.setEndpoint("https://region1.bcebos.com");
		assertEquals("https://region1.bcebos.com", properties.getEndpoint());

		properties.setEndpoint("https://region2.bcebos.com");
		assertEquals("https://region2.bcebos.com", properties.getEndpoint());

		properties.setAccessKey("key1");
		assertEquals("key1", properties.getAccessKey());

		properties.setAccessKey("key2");
		assertEquals("key2", properties.getAccessKey());
	}

	@Test
	@DisplayName("测试百度云 BOS 配置为空字符串")
	void testConfigurationWithEmptyStrings() {
		properties.setEndpoint("");
		properties.setAccessKey("");
		properties.setSecretKey("");

		assertEquals("", properties.getEndpoint());
		assertEquals("", properties.getAccessKey());
		assertEquals("", properties.getSecretKey());
	}

}
