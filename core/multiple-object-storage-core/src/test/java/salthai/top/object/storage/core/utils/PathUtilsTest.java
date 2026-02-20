package salthai.top.object.storage.core.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("路径工具测试")
class PathUtilsTest {

	@Test
	@DisplayName("测试拼接单个路径")
	void testJoinSinglePath() {
		String result = PathUtils.join("/user/data");
		assertEquals("/user/data", result, "单个路径应该原样返回");
	}

	@Test
	@DisplayName("测试拼接两个路径 - 不带前导斜杠")
	void testJoinTwoPathsWithoutLeadingSlash() {
		String result = PathUtils.join("/user/data", "file.txt");
		assertEquals("/user/data/file.txt", result, "应该正确拼接两个路径");
	}

	@Test
	@DisplayName("测试拼接两个路径 - 带前导斜杠")
	void testJoinTwoPathsWithLeadingSlash() {
		String result = PathUtils.join("/user/data", "/file.txt");
		assertEquals("/user/data/file.txt", result, "应该忽略第二个路径的前导斜杠");
	}

	@Test
	@DisplayName("测试拼接多个路径")
	void testJoinMultiplePaths() {
		String result = PathUtils.join("/user", "data", "files", "document.pdf");
		assertEquals("/user/data/files/document.pdf", result, "应该正确拼接多个路径");
	}

	@Test
	@DisplayName("测试拼接多个路径 - 混合带和不带前导斜杠")
	void testJoinMultiplePathsWithMixedLeadingSlashes() {
		String result = PathUtils.join("/user", "/data/", "files", "/documents/report.pdf");
		assertEquals("/user/data/files/documents/report.pdf", result, "应该正确处理混合的前导斜杠");
	}

	@Test
	@DisplayName("测试拼接空路径段")
	void testJoinWithEmptySegments() {
		String result = PathUtils.join("/user", "", "data", "", "file.txt");
		assertEquals("/user/data/file.txt", result, "应该忽略空路径段");
	}

	@Test
	@DisplayName("测试路径以斜杠结尾")
	void testJoinWithTrailingSlash() {
		String result = PathUtils.join("/user/data/", "file.txt");
		assertEquals("/user/data/file.txt", result, "应该正确处理以斜杠结尾的路径");
	}

	@Test
	@DisplayName("测试拼接空数组")
	void testJoinWithEmptyArray() {
		String result = PathUtils.join("/user/data");
		assertEquals("/user/data", result, "空数组应该返回原路径");
	}

	@Test
	@DisplayName("测试拼接 null 数组")
	void testJoinWithNullArray() {
		String result = PathUtils.join("/user/data", (String[]) null);
		assertEquals("/user/data", result, "null 数组应该返回原路径");
	}

	@Test
	@DisplayName("测试复杂路径拼接")
	void testJoinComplexPaths() {
		String result = PathUtils.join("/bucket", "folder/subfolder", "nested/file.pdf");
		assertEquals("/bucket/folder/subfolder/nested/file.pdf", result, "应该正确拼接复杂路径");
	}

	@Test
	@DisplayName("测试路径包含多个连续斜杠")
	void testJoinWithMultipleSlashes() {
		String result = PathUtils.join("/user//data/", "//file.txt");
		assertEquals("/user//data/file.txt", result, "应该保留路径中的多个斜杠");
	}

	@Test
	@DisplayName("测试Windows风格路径")
	void testJoinWindowsStylePaths() {
		String result = PathUtils.join("C:\\user\\data", "file.txt");
		assertEquals("C:\\user\\data/file.txt", result, "应该处理Windows风格路径");
	}

	@Test
	@DisplayName("测试相对路径拼接")
	void testJoinRelativePaths() {
		String result = PathUtils.join("user/data", "file.txt");
		assertEquals("user/data/file.txt", result, "应该正确处理相对路径");
	}

	@Test
	@DisplayName("测试根路径拼接")
	void testJoinRootPath() {
		String result = PathUtils.join("/", "user", "data", "file.txt");
		assertEquals("/user/data/file.txt", result, "应该正确处理根路径");
	}

	@Test
	@DisplayName("测试所有路径段都为空")
	void testJoinAllEmptySegments() {
		String result = PathUtils.join("/user/", "", "", "");
		assertEquals("/user/", result, "所有空段应该保留原路径");
	}

	@Test
	@DisplayName("测试深层嵌套路径")
	void testJoinDeepNestedPaths() {
		String result = PathUtils.join("/a", "b", "c", "d", "e", "f", "g", "file.txt");
		assertEquals("/a/b/c/d/e/f/g/file.txt", result, "应该正确处理深层嵌套路径");
	}

	@Test
	@DisplayName("测试路径包含特殊字符")
	void testJoinPathsWithSpecialCharacters() {
		String result = PathUtils.join("/user data", "file with spaces.pdf");
		assertEquals("/user data/file with spaces.pdf", result, "应该保留路径中的特殊字符");
	}

	@Test
	@DisplayName("测试路径包含中文字符")
	void testJoinPathsWithChineseCharacters() {
		String result = PathUtils.join("/用户/文档", "报告.pdf");
		assertEquals("/用户/文档/报告.pdf", result, "应该正确处理中文路径");
	}

	@Test
	@DisplayName("测试文件名包含点号")
	void testJoinFileNameWithDots() {
		String result = PathUtils.join("/user/data", ".hiddenfile");
		assertEquals("/user/data/.hiddenfile", result, "应该正确处理以点开头的文件名");
	}

	@Test
	@DisplayName("测试路径只包含斜杠")
	void testJoinOnlySlash() {
		String result = PathUtils.join("/", "/", "/");
		assertEquals("//", result, "应该保留多个斜杠");
	}

}
