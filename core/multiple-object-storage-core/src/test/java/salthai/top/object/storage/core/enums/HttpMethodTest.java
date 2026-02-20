package salthai.top.object.storage.core.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HTTP方法枚举测试")
class HttpMethodTest {

	@Test
	@DisplayName("测试所有枚举值是否存在")
	void testAllEnumValuesExist() {
		HttpMethod[] methods = HttpMethod.values();
		assertEquals(6, methods.length, "应该有6个HTTP方法");
	}

	@Test
	@DisplayName("测试GET方法枚举")
	void testGetMethod() {
		HttpMethod method = HttpMethod.GET;
		assertEquals("GET", method.name());
	}

	@Test
	@DisplayName("测试POST方法枚举")
	void testPostMethod() {
		HttpMethod method = HttpMethod.POST;
		assertEquals("POST", method.name());
	}

	@Test
	@DisplayName("测试PUT方法枚举")
	void testPutMethod() {
		HttpMethod method = HttpMethod.PUT;
		assertEquals("PUT", method.name());
	}

	@Test
	@DisplayName("测试DELETE方法枚举")
	void testDeleteMethod() {
		HttpMethod method = HttpMethod.DELETE;
		assertEquals("DELETE", method.name());
	}

	@Test
	@DisplayName("测试HEAD方法枚举")
	void testHeadMethod() {
		HttpMethod method = HttpMethod.HEAD;
		assertEquals("HEAD", method.name());
	}

	@Test
	@DisplayName("测试OPTIONS方法枚举")
	void testOptionsMethod() {
		HttpMethod method = HttpMethod.OPTIONS;
		assertEquals("OPTIONS", method.name());
	}

	@Test
	@DisplayName("测试valueOf方法 - GET")
	void testValueOfGet() {
		HttpMethod method = HttpMethod.valueOf("GET");
		assertEquals(HttpMethod.GET, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - POST")
	void testValueOfPost() {
		HttpMethod method = HttpMethod.valueOf("POST");
		assertEquals(HttpMethod.POST, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - PUT")
	void testValueOfPut() {
		HttpMethod method = HttpMethod.valueOf("PUT");
		assertEquals(HttpMethod.PUT, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - DELETE")
	void testValueOfDelete() {
		HttpMethod method = HttpMethod.valueOf("DELETE");
		assertEquals(HttpMethod.DELETE, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - HEAD")
	void testValueOfHead() {
		HttpMethod method = HttpMethod.valueOf("HEAD");
		assertEquals(HttpMethod.HEAD, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - OPTIONS")
	void testValueOfOptions() {
		HttpMethod method = HttpMethod.valueOf("OPTIONS");
		assertEquals(HttpMethod.OPTIONS, method);
	}

	@Test
	@DisplayName("测试valueOf方法 - 无效名称应该抛出异常")
	void testValueOfInvalidName() {
		assertThrows(IllegalArgumentException.class, () -> HttpMethod.valueOf("PATCH"));
		assertThrows(IllegalArgumentException.class, () -> HttpMethod.valueOf("INVALID"));
	}

	@Test
	@DisplayName("测试枚举相等性")
	void testEnumEquality() {
		HttpMethod method1 = HttpMethod.GET;
		HttpMethod method2 = HttpMethod.GET;
		HttpMethod method3 = HttpMethod.POST;

		assertEquals(method1, method2);
		assertNotEquals(method1, method3);
	}

	@Test
	@DisplayName("测试枚举顺序")
	void testEnumOrder() {
		HttpMethod[] methods = HttpMethod.values();
		assertEquals(HttpMethod.DELETE, methods[0]);
		assertEquals(HttpMethod.GET, methods[1]);
		assertEquals(HttpMethod.HEAD, methods[2]);
		assertEquals(HttpMethod.POST, methods[3]);
		assertEquals(HttpMethod.PUT, methods[4]);
		assertEquals(HttpMethod.OPTIONS, methods[5]);
	}

	@Test
	@DisplayName("测试枚举序数")
	void testEnumOrdinal() {
		assertEquals(0, HttpMethod.DELETE.ordinal());
		assertEquals(1, HttpMethod.GET.ordinal());
		assertEquals(2, HttpMethod.HEAD.ordinal());
		assertEquals(3, HttpMethod.POST.ordinal());
		assertEquals(4, HttpMethod.PUT.ordinal());
		assertEquals(5, HttpMethod.OPTIONS.ordinal());
	}

	@Test
	@DisplayName("测试枚举toString方法")
	void testEnumToString() {
		assertEquals("DELETE", HttpMethod.DELETE.toString());
		assertEquals("GET", HttpMethod.GET.toString());
		assertEquals("HEAD", HttpMethod.HEAD.toString());
		assertEquals("POST", HttpMethod.POST.toString());
		assertEquals("PUT", HttpMethod.PUT.toString());
		assertEquals("OPTIONS", HttpMethod.OPTIONS.toString());
	}

	@Test
	@DisplayName("测试常见HTTP方法")
	void testCommonHttpMethods() {
		assertEquals(6, HttpMethod.values().length);

		assertTrue(contains(HttpMethod.values(), HttpMethod.GET));
		assertTrue(contains(HttpMethod.values(), HttpMethod.POST));
		assertTrue(contains(HttpMethod.values(), HttpMethod.PUT));
		assertTrue(contains(HttpMethod.values(), HttpMethod.DELETE));
	}

	private boolean contains(HttpMethod[] methods, HttpMethod method) {
		for (HttpMethod m : methods) {
			if (m == method) {
				return true;
			}
		}
		return false;
	}

	@Test
	@DisplayName("测试枚举在switch语句中使用")
	void testEnumInSwitch() {
		HttpMethod method = HttpMethod.GET;
		boolean isSafe = false;

		switch (method) {
			case GET:
			case HEAD:
				isSafe = true;
				break;
			case POST:
			case PUT:
			case DELETE:
			case OPTIONS:
				isSafe = false;
				break;
		}

		assertTrue(isSafe);
	}

	@Test
	@DisplayName("测试安全方法")
	void testSafeMethods() {
		HttpMethod safeMethod1 = HttpMethod.GET;
		HttpMethod safeMethod2 = HttpMethod.HEAD;

		assertEquals(1, safeMethod1.ordinal());
		assertEquals(2, safeMethod2.ordinal());
	}

	@Test
	@DisplayName("测试幂等方法")
	void testIdempotentMethods() {
		HttpMethod[] idempotentMethods = { HttpMethod.GET, HttpMethod.HEAD, HttpMethod.PUT, HttpMethod.DELETE };

		for (HttpMethod method : idempotentMethods) {
			assertNotNull(method);
		}
	}

	@Test
	@DisplayName("测试枚举Class类型")
	void testEnumClassType() {
		assertTrue(HttpMethod.GET instanceof Enum);
		assertEquals(HttpMethod.class, HttpMethod.GET.getDeclaringClass());
	}

	@Test
	@DisplayName("测试枚举比较")
	void testEnumCompareTo() {
		assertTrue(HttpMethod.DELETE.compareTo(HttpMethod.GET) < 0);
		assertTrue(HttpMethod.PUT.compareTo(HttpMethod.POST) > 0);
		assertEquals(0, HttpMethod.GET.compareTo(HttpMethod.GET));
	}

	@Test
	@DisplayName("测试枚举getDeclaringClass")
	void testGetDeclaringClass() {
		assertEquals(HttpMethod.class, HttpMethod.GET.getDeclaringClass());
		assertEquals(HttpMethod.class, HttpMethod.POST.getDeclaringClass());
	}

	@Test
	@DisplayName("测试所有方法名称唯一性")
	void testAllMethodNamesAreUnique() {
		HttpMethod[] methods = HttpMethod.values();
		for (int i = 0; i < methods.length; i++) {
			for (int j = i + 1; j < methods.length; j++) {
				assertNotEquals(methods[i].name(), methods[j].name(),
						"方法名称应该唯一: " + methods[i].name() + " vs " + methods[j].name());
			}
		}
	}

	@Test
	@DisplayName("测试枚举不是null")
	void testEnumsAreNotNull() {
		for (HttpMethod method : HttpMethod.values()) {
			assertNotNull(method);
		}
	}

}
