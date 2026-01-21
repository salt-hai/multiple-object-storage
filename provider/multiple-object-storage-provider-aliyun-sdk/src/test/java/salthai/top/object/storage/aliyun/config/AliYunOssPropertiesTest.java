package salthai.top.object.storage.aliyun.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.constants.StorageConstants;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 阿里云 OSS 配置属性测试
 * <p>
 * 测试阿里云 OSS 配置属性的各种场景，包括临时访问授权、区域和 STS 角色配置
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 11:00
 */
@DisplayName("阿里云 OSS 配置属性测试")
class AliYunOssPropertiesTest {

	private AliYunOssProperties properties;

	private static final String TEST_PROPERTY_PREFIX = "multiple.object.storage.aliyun";

	@BeforeEach
	void setUp() {
		properties = new AliYunOssProperties();
	}

	@Test
	@DisplayName("测试默认临时访问授权为 false")
	void testDefaultEnableTempAccess() {
		assertEquals(Boolean.FALSE, properties.getEnableTempAccess(), "默认临时访问授权应该为 false");
	}

	@Test
	@DisplayName("测试设置和获取临时访问授权")
	void testSetAndGetEnableTempAccess() {
		properties.setEnableTempAccess(true);
		assertTrue(properties.getEnableTempAccess(), "临时访问授权应该为 true");

		properties.setEnableTempAccess(false);
		assertFalse(properties.getEnableTempAccess(), "临时访问授权应该为 false");
	}

	@Test
	@DisplayName("测试设置和获取区域")
	void testSetAndGetRegion() {
		String region = "oss-cn-hangzhou";
		properties.setRegion(region);

		assertEquals(region, properties.getRegion(), "区域应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置 null 区域")
	void testSetNullRegion() {
		properties.setRegion(null);
		assertNull(properties.getRegion(), "区域应该可以为 null");
	}

	@Test
	@DisplayName("测试设置和获取 STS 角色 ARN")
	void testSetAndGetStsRoleArn() {
		String stsRoleArn = "acs:ram::123456789:role/ossrole";
		properties.setStsRoleArn(stsRoleArn);

		assertEquals(stsRoleArn, properties.getStsRoleArn(), "STS 角色 ARN 应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置 null STS 角色 ARN")
	void testSetNullStsRoleArn() {
		properties.setStsRoleArn(null);
		assertNull(properties.getStsRoleArn(), "STS 角色 ARN 应该可以为 null");
	}

	@Test
	@DisplayName("测试验证完整配置 - 不使用临时访问")
	void testVerifyValidConfigurationWithoutTempAccess() {
		properties.setEndpoint("https://oss-cn-hangzhou.aliyuncs.com");
		properties.setAccessKey("test-access-key");
		properties.setSecretKey("test-secret-key");
		properties.setEnableTempAccess(false);

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX),
				"不使用临时访问的完整配置验证应该成功");
	}

	@Test
	@DisplayName("测试设置阿里云不同区域")
	void testSetDifferentAliYunRegions() {
		// 华东1（杭州）
		properties.setRegion("oss-cn-hangzhou");
		assertEquals("oss-cn-hangzhou", properties.getRegion());

		// 华北2（北京）
		properties.setRegion("oss-cn-beijing");
		assertEquals("oss-cn-beijing", properties.getRegion());

		// 华南1（深圳）
		properties.setRegion("oss-cn-shenzhen");
		assertEquals("oss-cn-shenzhen", properties.getRegion());

		// 华东2（上海）
		properties.setRegion("oss-cn-shanghai");
		assertEquals("oss-cn-shanghai", properties.getRegion());
	}

	@Test
	@DisplayName("测试 toString 方法包含所有重要信息")
	void testToStringContainsAllInformation() {
		properties.setEndpoint("https://oss-cn-hangzhou.aliyuncs.com");
		properties.setAccessKey("LTAI5tXXXXXXXXXX");
		properties.setSecretKey("XXXXXXXXXXXXXXXX");
		properties.setEnableTempAccess(true);
		properties.setRegion("oss-cn-hangzhou");
		properties.setStsRoleArn("acs:ram::123456789:role/ossrole");

		String toString = properties.toString();

		assertTrue(toString.contains("https://oss-cn-hangzhou.aliyuncs.com"), "toString 应该包含 endpoint");
		assertTrue(toString.contains("LTAI5tXXXXXXXXXX"), "toString 应该包含 accessKey");
		assertTrue(toString.contains("true"), "toString 应该包含 enableTempAccess");
		assertTrue(toString.contains("oss-cn-hangzhou"), "toString 应该包含 region");
		assertTrue(toString.contains("acs:ram::123456789:role/ossrole"), "toString 应该包含 stsRoleArn");
	}

	@Test
	@DisplayName("测试 toString 方法 - 部分配置")
	void testToStringWithPartialConfiguration() {
		properties.setEndpoint("https://oss-cn-beijing.aliyuncs.com");
		properties.setAccessKey("test-key");
		properties.setSecretKey("test-secret");
		// region 和 stsRoleArn 为 null

		String toString = properties.toString();

		assertTrue(toString.contains("https://oss-cn-beijing.aliyuncs.com"), "toString 应该包含已设置的 endpoint");
		assertTrue(toString.contains("AliYunProperties"), "toString 应该包含类名");
	}

	@Test
	@DisplayName("测试完整的阿里云 OSS 配置")
	void testCompleteAliYunConfiguration() {
		properties.setEndpoint("https://oss-cn-shenzhen.aliyuncs.com");
		properties.setAccessKey("LTAI5tTestAccessKey");
		properties.setSecretKey("TestSecretKey");
		properties.setEnableTempAccess(true);
		properties.setRegion("oss-cn-shenzhen");
		properties.setStsRoleArn("acs:ram::999999999:role/testrole");

		assertEquals("https://oss-cn-shenzhen.aliyuncs.com", properties.getEndpoint());
		assertEquals("LTAI5tTestAccessKey", properties.getAccessKey());
		assertEquals("TestSecretKey", properties.getSecretKey());
		assertTrue(properties.getEnableTempAccess());
		assertEquals("oss-cn-shenzhen", properties.getRegion());
		assertEquals("acs:ram::999999999:role/testrole", properties.getStsRoleArn());

		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试临时访问授权为 null 时的行为")
	void testEnableTempAccessWithNull() {
		// 设置为 null
		properties.setEnableTempAccess(null);
		assertNull(properties.getEnableTempAccess(), "临时访问授权应该可以为 null");
	}

	@Test
	@DisplayName("测试多次更新配置属性")
	void testUpdateConfigurationMultipleTimes() {
		// 第一次设置
		properties.setRegion("oss-cn-hangzhou");
		assertEquals("oss-cn-hangzhou", properties.getRegion());

		// 第二次更新
		properties.setRegion("oss-cn-beijing");
		assertEquals("oss-cn-beijing", properties.getRegion());

		// 第三次更新
		properties.setRegion("oss-cn-shanghai");
		assertEquals("oss-cn-shanghai", properties.getRegion());

		// STS 角色 ARN 的多次更新
		properties.setStsRoleArn("acs:ram::111:role/role1");
		assertEquals("acs:ram::111:role/role1", properties.getStsRoleArn());

		properties.setStsRoleArn("acs:ram::222:role/role2");
		assertEquals("acs:ram::222:role/role2", properties.getStsRoleArn());
	}

	@Test
	@DisplayName("测试继承的基类属性")
	void testInheritedBaseProperties() {
		String endpoint = "https://oss-cn-qingdao.aliyuncs.com";
		String accessKey = "LTAI_test";
		String secretKey = "secret_test";

		properties.setEndpoint(endpoint);
		properties.setAccessKey(accessKey);
		properties.setSecretKey(secretKey);

		assertEquals(endpoint, properties.getEndpoint());
		assertEquals(accessKey, properties.getAccessKey());
		assertEquals(secretKey, properties.getSecretKey());
	}

	@Test
	@DisplayName("测试设置包含特殊字符的 STS 角色 ARN")
	void testSetStsRoleArnWithSpecialCharacters() {
		String[] validArns = { "acs:ram::1234567890123456:role/oss-role", "acs:ram::account:role/role-with-dash",
				"acs:ram::account:role/role_with_underscore" };

		for (String arn : validArns) {
			AliYunOssProperties props = new AliYunOssProperties();
			props.setStsRoleArn(arn);
			assertEquals(arn, props.getStsRoleArn());
		}
	}

	@Test
	@DisplayName("测试布尔值切换")
	void testBooleanValueToggle() {
		// 默认为 false
		assertFalse(properties.getEnableTempAccess());

		// 切换为 true
		properties.setEnableTempAccess(true);
		assertTrue(properties.getEnableTempAccess());

		// 切换回 false
		properties.setEnableTempAccess(false);
		assertFalse(properties.getEnableTempAccess());

		// 切换为 null
		properties.setEnableTempAccess(null);
		assertNull(properties.getEnableTempAccess());
	}

	@Test
	@DisplayName("测试验证继承的必需属性")
	void testVerifyInheritedRequiredProperties() {
		// endpoint 为空应该抛出异常
		properties.setAccessKey("key");
		properties.setSecretKey("secret");
		assertThrows(Exception.class, () -> properties.verify(TEST_PROPERTY_PREFIX));

		// accessKey 为空应该抛出异常
		properties.setEndpoint("https://oss.aliyuncs.com");
		properties.setAccessKey(null);
		assertThrows(Exception.class, () -> properties.verify(TEST_PROPERTY_PREFIX));

		// secretKey 为空应该抛出异常
		properties.setAccessKey("key");
		properties.setSecretKey(null);
		assertThrows(Exception.class, () -> properties.verify(TEST_PROPERTY_PREFIX));

		// 完整配置应该通过
		properties.setSecretKey("secret");
		assertDoesNotThrow(() -> properties.verify(TEST_PROPERTY_PREFIX));
	}

	@Test
	@DisplayName("测试配置属性为空字符串")
	void testConfigurationWithEmptyStrings() {
		properties.setRegion("");
		assertEquals("", properties.getRegion());

		properties.setStsRoleArn("");
		assertEquals("", properties.getStsRoleArn());
	}

	@Test
	@DisplayName("测试使用阿里云提供者常量")
	void testAliYunProviderConstant() {
		assertEquals("multiple.object.storage.aliyun", StorageConstants.ALIYUN_PROVIDER);
	}

}
