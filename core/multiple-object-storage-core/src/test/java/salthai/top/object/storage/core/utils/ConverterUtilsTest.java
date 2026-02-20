package salthai.top.object.storage.core.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.function.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("对象转换工具测试")
class ConverterUtilsTest {

	@Test
	@DisplayName("测试转换为单个目标对象")
	void testToTarget() {
		String source = "test-value";
		Converter<String, Integer> converter = Integer::parseInt;

		Integer result = ConverterUtils.toTarget(source, converter);

		assertEquals(0, result, "应该正确转换字符串为整数");
	}

	@Test
	@DisplayName("测试转换为单个目标对象 - 复杂转换")
	void testToTargetWithComplexConversion() {
		Person source = new Person("John", 30);
		Converter<Person, PersonDto> converter = person -> new PersonDto(person.name, person.age);

		PersonDto result = ConverterUtils.toTarget(source, converter);

		assertEquals("John", result.name);
		assertEquals(30, result.age);
	}

	@Test
	@DisplayName("测试转换为单个目标对象 - source为null应该抛出异常")
	void testToTargetWithNullSource() {
		Converter<String, Integer> converter = Integer::parseInt;

		assertThrows(IllegalArgumentException.class, () -> ConverterUtils.toTarget(null, converter),
				"source为null时应该抛出IllegalArgumentException");
	}

	@Test
	@DisplayName("测试转换为单个目标对象 - converter为null应该抛出异常")
	void testToTargetWithNullConverter() {
		String source = "123";

		assertThrows(NullPointerException.class, () -> ConverterUtils.toTarget(source, null),
				"converter为null时应该抛出NullPointerException");
	}

	@Test
	@DisplayName("测试转换为空列表")
	void testToTargetsWithEmptyCollection() {
		List<String> sourceItems = Collections.emptyList();
		Converter<String, Integer> converter = Integer::parseInt;

		List<Integer> result = ConverterUtils.toTargets(sourceItems, converter);

		assertNotNull(result, "结果不应为null");
		assertTrue(result.isEmpty(), "空集合应该返回空列表");
	}

	@Test
	@DisplayName("测试转换为null列表")
	void testToTargetsWithNullCollection() {
		Converter<String, Integer> converter = Integer::parseInt;

		List<Integer> result = ConverterUtils.toTargets(null, converter);

		assertNotNull(result, "结果不应为null");
		assertTrue(result.isEmpty(), "null集合应该返回空列表");
	}

	@Test
	@DisplayName("测试转换为多个目标对象")
	void testToTargetsWithMultipleItems() {
		List<String> sourceItems = Arrays.asList("1", "2", "3", "4", "5");
		Converter<String, Integer> converter = Integer::parseInt;

		List<Integer> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(5, result.size(), "应该转换所有元素");
		assertEquals(Arrays.asList(1, 2, 3, 4, 5), result, "应该正确转换所有元素");
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 复杂对象转换")
	void testToTargetsWithComplexConversion() {
		List<Person> sourceItems = Arrays.asList(new Person("Alice", 25), new Person("Bob", 30));
		Converter<Person, PersonDto> converter = person -> new PersonDto(person.name, person.age);

		List<PersonDto> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(2, result.size());
		assertEquals("Alice", result.get(0).name);
		assertEquals(25, result.get(0).age);
		assertEquals("Bob", result.get(1).name);
		assertEquals(30, result.get(1).age);
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 过滤null值")
	void testToTargetsWithNullElements() {
		List<String> sourceItems = Arrays.asList("1", null, "3", null, "5");
		Converter<String, Integer> converter = s -> s == null ? null : Integer.parseInt(s);

		List<Integer> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(5, result.size(), "应该包含null值");
		assertNull(result.get(1));
		assertNull(result.get(3));
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 单个元素")
	void testToTargetsWithSingleElement() {
		List<String> sourceItems = Collections.singletonList("42");
		Converter<String, Integer> converter = Integer::parseInt;

		List<Integer> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(1, result.size());
		assertEquals(42, result.get(0));
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 转换器返回null")
	void testToTargetsWithConverterReturningNull() {
		List<String> sourceItems = Arrays.asList("1", "invalid", "3");
		Converter<String, Integer> converter = s -> {
			try {
				return Integer.parseInt(s);
			}
			catch (NumberFormatException e) {
				return null;
			}
		};

		List<Integer> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(3, result.size());
		assertEquals(1, result.get(0));
		assertNull(result.get(1));
		assertEquals(3, result.get(2));
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 空字符串元素")
	void testToTargetsWithEmptyStringElements() {
		List<String> sourceItems = Arrays.asList("", "test", "");
		Converter<String, String> converter = s -> s.isEmpty() ? "empty" : s;

		List<String> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(Arrays.asList("empty", "test", "empty"), result);
	}

	@Test
	@DisplayName("测试转换为多个目标对象 - 大列表")
	void testToTargetsWithLargeList() {
		List<Integer> sourceItems = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			sourceItems.add(i);
		}
		Converter<Integer, String> converter = String::valueOf;

		List<String> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(1000, result.size());
		assertEquals("0", result.get(0));
		assertEquals("999", result.get(999));
	}

	@Test
	@DisplayName("测试转换保持顺序")
	void testToTargetsPreservesOrder() {
		List<String> sourceItems = Arrays.asList("first", "second", "third");
		Converter<String, String> converter = String::toUpperCase;

		List<String> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(Arrays.asList("FIRST", "SECOND", "THIRD"), result, "应该保持原始顺序");
	}

	@Test
	@DisplayName("测试转换修改类型")
	void testToTargetsChangeType() {
		List<Integer> sourceItems = Arrays.asList(1, 2, 3, 4, 5);
		Converter<Integer, String> converter = i -> "Number:" + i;

		List<String> result = ConverterUtils.toTargets(sourceItems, converter);

		assertEquals(Arrays.asList("Number:1", "Number:2", "Number:3", "Number:4", "Number:5"), result);
	}

	static class Person {

		String name;

		int age;

		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

	}

	static class PersonDto {

		String name;

		int age;

		PersonDto(String name, int age) {
			this.name = name;
			this.age = age;
		}

	}

}
