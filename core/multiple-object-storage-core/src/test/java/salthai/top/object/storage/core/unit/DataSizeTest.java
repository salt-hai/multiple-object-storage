package salthai.top.object.storage.core.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("数据大小测试")
class DataSizeTest {

	@Test
	@DisplayName("测试创建字节数")
	void testOfBytes() {
		DataSize dataSize = DataSize.ofBytes(1024);
		assertEquals(1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试创建千字节数")
	void testOfKilobytes() {
		DataSize dataSize = DataSize.ofKilobytes(1);
		assertEquals(1024, dataSize.toBytes());
		assertEquals(1, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试创建兆字节数")
	void testOfMegabytes() {
		DataSize dataSize = DataSize.ofMegabytes(1);
		assertEquals(1024 * 1024, dataSize.toBytes());
		assertEquals(1, dataSize.toMegabytes());
	}

	@Test
	@DisplayName("测试创建吉字节数")
	void testOfGigabytes() {
		DataSize dataSize = DataSize.ofGigabytes(1);
		assertEquals(1024L * 1024 * 1024, dataSize.toBytes());
		assertEquals(1, dataSize.toGigabytes());
	}

	@Test
	@DisplayName("测试创建太字节数")
	void testOfTerabytes() {
		DataSize dataSize = DataSize.ofTerabytes(1);
		assertEquals(1024L * 1024 * 1024 * 1024, dataSize.toBytes());
		assertEquals(1, dataSize.toTerabytes());
	}

	@Test
	@DisplayName("测试使用指定单位创建")
	void testOfWithUnit() {
		DataSize dataSize = DataSize.of(5, DataUnit.MEGABYTES);
		assertEquals(5 * 1024 * 1024, dataSize.toBytes());
		assertEquals(5, dataSize.toMegabytes());
	}

	@Test
	@DisplayName("测试解析带单位的字符串 - KB")
	void testParseWithKilobytes() {
		DataSize dataSize = DataSize.parse("12KB");
		assertEquals(12, dataSize.toKilobytes());
		assertEquals(12 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析带单位的字符串 - MB")
	void testParseWithMegabytes() {
		DataSize dataSize = DataSize.parse("5MB");
		assertEquals(5, dataSize.toMegabytes());
		assertEquals(5 * 1024 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析带单位的字符串 - GB")
	void testParseWithGigabytes() {
		DataSize dataSize = DataSize.parse("2GB");
		assertEquals(2, dataSize.toGigabytes());
		assertEquals(2L * 1024 * 1024 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析带单位的字符串 - TB")
	void testParseWithTerabytes() {
		DataSize dataSize = DataSize.parse("1TB");
		assertEquals(1, dataSize.toTerabytes());
		assertEquals(1024L * 1024 * 1024 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析无单位的字符串 - 默认为字节")
	void testParseWithoutUnit() {
		DataSize dataSize = DataSize.parse("1024");
		assertEquals(1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析带默认单位的字符串")
	void testParseWithDefaultUnit() {
		DataSize dataSize = DataSize.parse("20", DataUnit.KILOBYTES);
		assertEquals(20, dataSize.toKilobytes());
		assertEquals(20 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析无效格式 - 抛出异常")
	void testParseInvalidFormat() {
		assertThrows(IllegalArgumentException.class, () -> DataSize.parse("invalid"));
	}

	@Test
	@DisplayName("测试解析负数")
	void testParseNegativeNumber() {
		DataSize dataSize = DataSize.parse("-10KB");
		assertEquals(-10, dataSize.toKilobytes());
		assertEquals(-10 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试解析正数带加号")
	void testParsePositiveWithSign() {
		DataSize dataSize = DataSize.parse("+10KB");
		assertEquals(10, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试转换为字节数")
	void testToBytes() {
		DataSize dataSize = DataSize.ofMegabytes(2);
		assertEquals(2L * 1024 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试转换为千字节数")
	void testToKilobytes() {
		DataSize dataSize = DataSize.ofMegabytes(1);
		assertEquals(1024, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试转换为兆字节数")
	void testToMegabytes() {
		DataSize dataSize = DataSize.ofGigabytes(1);
		assertEquals(1024, dataSize.toMegabytes());
	}

	@Test
	@DisplayName("测试转换为吉字节数")
	void testToGigabytes() {
		DataSize dataSize = DataSize.ofTerabytes(1);
		assertEquals(1024, dataSize.toGigabytes());
	}

	@Test
	@DisplayName("测试转换为太字节数")
	void testToTerabytes() {
		DataSize dataSize = DataSize.ofTerabytes(2);
		assertEquals(2, dataSize.toTerabytes());
	}

	@Test
	@DisplayName("测试判断是否为负数")
	void testIsNegative() {
		DataSize negativeSize = DataSize.ofKilobytes(-1);
		assertTrue(negativeSize.isNegative());

		DataSize positiveSize = DataSize.ofKilobytes(1);
		assertFalse(positiveSize.isNegative());

		DataSize zeroSize = DataSize.ofBytes(0);
		assertFalse(zeroSize.isNegative());
	}

	@Test
	@DisplayName("测试比较大小")
	void testCompareTo() {
		DataSize size1 = DataSize.ofKilobytes(1);
		DataSize size2 = DataSize.ofKilobytes(2);
		DataSize size3 = DataSize.ofKilobytes(1);

		assertTrue(size1.compareTo(size2) < 0);
		assertTrue(size2.compareTo(size1) > 0);
		assertEquals(0, size1.compareTo(size3));
	}

	@Test
	@DisplayName("测试相等性")
	void testEquals() {
		DataSize size1 = DataSize.ofKilobytes(1);
		DataSize size2 = DataSize.ofKilobytes(1);
		DataSize size3 = DataSize.ofKilobytes(2);

		assertEquals(size1, size2);
		assertNotEquals(size1, size3);
		assertNotEquals(size1, null);
		assertNotEquals(size1, "not a DataSize");
	}

	@Test
	@DisplayName("测试哈希码")
	void testHashCode() {
		DataSize size1 = DataSize.ofKilobytes(1);
		DataSize size2 = DataSize.ofKilobytes(1);

		assertEquals(size1.hashCode(), size2.hashCode());
	}

	@Test
	@DisplayName("测试toString方法")
	void testToString() {
		DataSize dataSize = DataSize.ofKilobytes(1);
		String result = dataSize.toString();
		assertTrue(result.contains("1024"));
		assertTrue(result.contains("B"));
	}

	@Test
	@DisplayName("测试大数值创建")
	void testLargeValues() {
		DataSize dataSize = DataSize.ofTerabytes(10);
		assertEquals(10L * 1024 * 1024 * 1024 * 1024, dataSize.toBytes());
	}

	@Test
	@DisplayName("测试零值")
	void testZeroValue() {
		DataSize dataSize = DataSize.ofBytes(0);
		assertEquals(0, dataSize.toBytes());
		assertEquals(0, dataSize.toKilobytes());
		assertFalse(dataSize.isNegative());
	}

	@Test
	@DisplayName("测试解析小写单位")
	void testParseLowercaseUnit() {
		DataSize dataSize = DataSize.parse("10kb");
		assertEquals(10, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试解析混合大小写单位")
	void testParseMixedCaseUnit() {
		DataSize dataSize = DataSize.parse("10Kb");
		assertEquals(10, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试精度损失 - 字节转千字节")
	void testPrecisionLossBytesToKilobytes() {
		DataSize dataSize = DataSize.ofBytes(1025);
		assertEquals(1, dataSize.toKilobytes());
	}

	@Test
	@DisplayName("测试精度损失 - 字节转兆字节")
	void testPrecisionLossBytesToMegabytes() {
		DataSize dataSize = DataSize.ofBytes(1024 * 1024 + 1);
		assertEquals(1, dataSize.toMegabytes());
	}

	@Test
	@DisplayName("测试负数比较")
	void testNegativeComparison() {
		DataSize negativeSize = DataSize.ofKilobytes(-1);
		DataSize zeroSize = DataSize.ofBytes(0);
		DataSize positiveSize = DataSize.ofKilobytes(1);

		assertTrue(negativeSize.compareTo(zeroSize) < 0);
		assertTrue(zeroSize.compareTo(positiveSize) < 0);
		assertTrue(negativeSize.compareTo(positiveSize) < 0);
	}

	@Test
	@DisplayName("测试解析带空格的字符串")
	void testParseWithSpaces() {
		assertThrows(IllegalArgumentException.class, () -> DataSize.parse("10 KB"));
	}

	@Test
	@DisplayName("测试解析空字符串")
	void testParseEmptyString() {
		assertThrows(IllegalArgumentException.class, () -> DataSize.parse(""));
	}

	@Test
	@DisplayName("测试解析只有单位没有数字")
	void testParseOnlyUnit() {
		assertThrows(IllegalArgumentException.class, () -> DataSize.parse("MB"));
	}

}
