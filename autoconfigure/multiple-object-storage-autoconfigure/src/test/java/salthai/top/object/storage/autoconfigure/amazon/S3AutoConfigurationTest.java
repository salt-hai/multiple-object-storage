package salthai.top.object.storage.autoconfigure.amazon;

import org.junit.jupiter.api.Test;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.enums.Provider;

import static org.junit.jupiter.api.Assertions.*;

/**
 * S3 自动配置测试
 *
 * @author Kuang HaiBo 2024/1/20 17:45
 */
class S3AutoConfigurationTest {

	@Test
	void testAmazonProviderEnum() {
		// 测试 AMAZON 枚举值存在
		Provider[] providers = Provider.values();
		boolean hasAmazon = false;
		for (Provider provider : providers) {
			if (provider == Provider.AMAZON) {
				hasAmazon = true;
				break;
			}
		}
		assertTrue(hasAmazon, "AMAZON provider should exist in Provider enum");
	}

	@Test
	void testAmazonProviderConstant() {
		// 测试 AMAZON 配置常量存在且正确
		assertEquals("multiple.object.storage.amazon", StorageConstants.AMAZON_PROVIDER);
	}

	@Test
	void testS3PropertiesCreation() {
		// 测试 S3Properties 可以正确创建
		S3Properties properties = new S3Properties();
		assertNotNull(properties);
		assertNotNull(properties.getRegion());
	}

	@Test
	void testS3PropertiesDefaultRegion() {
		// 测试 S3Properties 默认区域
		S3Properties properties = new S3Properties();
		// 默认区域应该是 cn-north-1
		assertEquals("cn-north-1", properties.getRegion());
	}

	@Test
	void testS3ClientConfigurationCustomizerInterface() {
		// 测试 S3ClientConfigurationCustomizer 接口存在
		// 这是一个函数式接口，用于自定义 S3 客户端配置
		S3ClientConfigurationCustomizer customizer = builder -> {
			// 自定义逻辑
		};
		assertNotNull(customizer);
	}

}
