package salthai.top.object.storage.core.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("供应商枚举测试")
class ProviderTest {

	@Test
	@DisplayName("测试所有枚举值是否存在")
	void testAllEnumValuesExist() {
		Provider[] providers = Provider.values();
		assertEquals(4, providers.length, "应该有4个供应商");
	}

	@Test
	@DisplayName("测试阿里云供应商枚举")
	void testAliyunProvider() {
		Provider provider = Provider.ALIYUN;
		assertEquals("ALIYUN", provider.name());
	}

	@Test
	@DisplayName("测试华为供应商枚举")
	void testHuaweiProvider() {
		Provider provider = Provider.HUAWEI;
		assertEquals("HUAWEI", provider.name());
	}

	@Test
	@DisplayName("测试百度供应商枚举")
	void testBaiDuProvider() {
		Provider provider = Provider.BaiDu;
		assertEquals("BaiDu", provider.name());
	}

	@Test
	@DisplayName("测试亚马逊供应商枚举")
	void testAmazonProvider() {
		Provider provider = Provider.AMAZON;
		assertEquals("AMAZON", provider.name());
	}

	@Test
	@DisplayName("测试valueOf方法 - 阿里云")
	void testValueOfAliyun() {
		Provider provider = Provider.valueOf("ALIYUN");
		assertEquals(Provider.ALIYUN, provider);
	}

	@Test
	@DisplayName("测试valueOf方法 - 华为")
	void testValueOfHuawei() {
		Provider provider = Provider.valueOf("HUAWEI");
		assertEquals(Provider.HUAWEI, provider);
	}

	@Test
	@DisplayName("测试valueOf方法 - 百度")
	void testValueOfBaiDu() {
		Provider provider = Provider.valueOf("BaiDu");
		assertEquals(Provider.BaiDu, provider);
	}

	@Test
	@DisplayName("测试valueOf方法 - 亚马逊")
	void testValueOfAmazon() {
		Provider provider = Provider.valueOf("AMAZON");
		assertEquals(Provider.AMAZON, provider);
	}

	@Test
	@DisplayName("测试valueOf方法 - 无效名称应该抛出异常")
	void testValueOfInvalidName() {
		assertThrows(IllegalArgumentException.class, () -> Provider.valueOf("INVALID"));
	}

	@Test
	@DisplayName("测试枚举相等性")
	void testEnumEquality() {
		Provider provider1 = Provider.ALIYUN;
		Provider provider2 = Provider.ALIYUN;
		Provider provider3 = Provider.AMAZON;

		assertEquals(provider1, provider2);
		assertNotEquals(provider1, provider3);
	}

	@Test
	@DisplayName("测试枚举顺序")
	void testEnumOrder() {
		Provider[] providers = Provider.values();
		assertEquals(Provider.ALIYUN, providers[0]);
		assertEquals(Provider.HUAWEI, providers[1]);
		assertEquals(Provider.BaiDu, providers[2]);
		assertEquals(Provider.AMAZON, providers[3]);
	}

	@Test
	@DisplayName("测试枚举序数")
	void testEnumOrdinal() {
		assertEquals(0, Provider.ALIYUN.ordinal());
		assertEquals(1, Provider.HUAWEI.ordinal());
		assertEquals(2, Provider.BaiDu.ordinal());
		assertEquals(3, Provider.AMAZON.ordinal());
	}

	@Test
	@DisplayName("测试枚举toString方法")
	void testEnumToString() {
		assertEquals("ALIYUN", Provider.ALIYUN.toString());
		assertEquals("HUAWEI", Provider.HUAWEI.toString());
		assertEquals("BaiDu", Provider.BaiDu.toString());
		assertEquals("AMAZON", Provider.AMAZON.toString());
	}

	@Test
	@DisplayName("测试枚举在switch语句中使用")
	void testEnumInSwitch() {
		Provider provider = Provider.ALIYUN;
		String result = "";

		switch (provider) {
			case ALIYUN:
				result = "Aliyun OSS";
				break;
			case HUAWEI:
				result = "Huawei OBS";
				break;
			case BaiDu:
				result = "Baidu BOS";
				break;
			case AMAZON:
				result = "Amazon S3";
				break;
		}

		assertEquals("Aliyun OSS", result);
	}

	@Test
	@DisplayName("测试枚举Class类型")
	void testEnumClassType() {
		assertTrue(Provider.ALIYUN instanceof Enum);
		assertEquals(Provider.class, Provider.ALIYUN.getDeclaringClass());
	}

	@Test
	@DisplayName("测试枚举比较")
	void testEnumCompareTo() {
		assertTrue(Provider.ALIYUN.compareTo(Provider.HUAWEI) < 0);
		assertTrue(Provider.AMAZON.compareTo(Provider.HUAWEI) > 0);
		assertEquals(0, Provider.ALIYUN.compareTo(Provider.ALIYUN));
	}

	@Test
	@DisplayName("测试枚举getDeclaringClass")
	void testGetDeclaringClass() {
		assertEquals(Provider.class, Provider.ALIYUN.getDeclaringClass());
		assertEquals(Provider.class, Provider.AMAZON.getDeclaringClass());
	}

}
