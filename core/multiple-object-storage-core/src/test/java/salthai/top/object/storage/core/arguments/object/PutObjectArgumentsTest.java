package salthai.top.object.storage.core.arguments.object;

import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.core.constants.StorageConstants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PutObjectArguments 参数类测试
 * <p>
 * 测试上传对象参数的各种场景，包括参数设置、验证和边界条件
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 10:00
 */
@DisplayName("PutObjectArguments 参数类测试")
class PutObjectArgumentsTest {

	private PutObjectArguments arguments;

	private static final String TEST_BUCKET_NAME = "test-bucket";

	private static final String TEST_OBJECT_NAME = "test-object.txt";

	private static final String TEST_REGION = "cn-north-1";

	@BeforeEach
	void setUp() {
		arguments = new PutObjectArguments();
		arguments.setBucketName(TEST_BUCKET_NAME);
		arguments.setObjectName(TEST_OBJECT_NAME);
		arguments.setRegion(TEST_REGION);
	}

	@Test
	@DisplayName("测试设置和获取输入流")
	void testSetAndGetInputStream() {
		byte[] data = "测试数据内容".getBytes(StandardCharsets.UTF_8);
		InputStream inputStream = new ByteArrayInputStream(data);

		arguments.setInputStream(inputStream);

		assertEquals(inputStream, arguments.getInputStream(), "输入流应该正确设置和获取");
	}

	@Test
	@DisplayName("测试设置 null 输入流应该抛出异常")
	void testSetNullInputStream() {
		assertThrows(NullPointerException.class, () -> arguments.setInputStream(null),
				"设置 null 输入流应该抛出 NullPointerException");
	}

	@Test
	@DisplayName("测试设置对象大小")
	void testSetObjectSize() {
		long size = 1024 * 1024; // 1MB
		arguments.setObjectSize(size);

		assertEquals(size, arguments.getObjectSize(), "对象大小应该正确设置");
	}

	@Test
	@DisplayName("测试设置分片大小")
	void testSetPartSize() {
		long partSize = 5 * 1024 * 1024; // 5MB
		arguments.setPartSize(partSize);

		assertEquals(partSize, arguments.getPartSize(), "分片大小应该正确设置");
	}

	@Test
	@DisplayName("测试设置自定义内容类型")
	void testSetCustomContentType() {
		String contentType = "image/jpeg";
		arguments.setContentType(contentType);

		assertEquals(contentName, arguments.getContentType(), "内容类型应该正确设置");
	}

	@Test
	@DisplayName("测试默认内容类型")
	void testDefaultContentType() {
		// 不设置内容类型，应该使用默认值
		assertEquals(StorageConstants.DEFAULT_MINE_TYPE, arguments.getContentType(),
				"默认内容类型应该是 application/octet-stream");
	}

	@Test
	@DisplayName("测试设置各种常见内容类型")
	void testSetVariousContentTypes() {
		// 测试文本类型
		arguments.setContentType("text/plain");
		assertEquals("text/plain", arguments.getContentType());

		// 测试 JSON 类型
		arguments.setContentType("application/json");
		assertEquals("application/json", arguments.getContentType());

		// 测试图片类型
		arguments.setContentType("image/png");
		assertEquals("image/png", arguments.getContentType());

		// 测试 PDF 类型
		arguments.setContentType("application/pdf");
		assertEquals("application/pdf", arguments.getContentType());

		// 测试视频类型
		arguments.setContentType("video/mp4");
		assertEquals("video/mp4", arguments.getContentType());
	}

	@Test
	@DisplayName("测试设置带字符集的内容类型")
	void testSetContentTypeWithCharset() {
		String contentTypeWithCharset = "text/plain; charset=utf-8";
		arguments.setContentType(contentTypeWithCharset);

		assertEquals(contentTypeWithCharset, arguments.getContentType());
	}

	@Test
	@DisplayName("测试设置桶名称")
	void testSetBucketName() {
		String bucketName = "my-test-bucket";
		arguments.setBucketName(bucketName);

		assertEquals(bucketName, arguments.getBucketName());
	}

	@Test
	@DisplayName("测试设置 null 桶名称应该抛出异常")
	void testSetNullBucketName() {
		assertThrows(IllegalArgumentException.class, () -> arguments.setBucketName(null),
				"设置 null 桶名称应该抛出 IllegalArgumentException");
	}

	@Test
	@DisplayName("测试设置空桶名称应该抛出异常")
	void testSetEmptyBucketName() {
		assertThrows(IllegalArgumentException.class, () -> arguments.setBucketName(""),
				"设置空桶名称应该抛出 IllegalArgumentException");
	}

	@Test
	@DisplayName("测试设置空白桶名称应该抛出异常")
	void testSetBlankBucketName() {
		assertThrows(IllegalArgumentException.class, () -> arguments.setBucketName("   "),
				"设置空白桶名称应该抛出 IllegalArgumentException");
	}

	@Test
	@DisplayName("测试设置对象名称")
	void testSetObjectName() {
		String objectName = "path/to/object/file.txt";
		arguments.setObjectName(objectName);

		assertEquals(objectName, arguments.getObjectName());
	}

	@Test
	@DisplayName("测试设置 null 对象名称应该抛出异常")
	void testSetNullObjectName() {
		assertThrows(IllegalArgumentException.class, () -> arguments.setObjectName(null),
				"设置 null 对象名称应该抛出 IllegalArgumentException");
	}

	@Test
	@DisplayName("测试设置空对象名称应该抛出异常")
	void testSetEmptyObjectName() {
		assertThrows(IllegalArgumentException.class, () -> arguments.setObjectName(""),
				"设置空对象名称应该抛出 IllegalArgumentException");
	}

	@Test
	@DisplayName("测试设置区域")
	void testSetRegion() {
		String region = "us-west-2";
		arguments.setRegion(region);

		assertEquals(region, arguments.getRegion());
	}

	@Test
	@DisplayName("测试设置 null 区域")
	void testSetNullRegion() {
		// region 可以为 null
		arguments.setRegion(null);

		assertNull(arguments.getRegion(), "区域应该可以为 null");
	}

	@Test
	@DisplayName("测试设置空区域")
	void testSetEmptyRegion() {
		arguments.setRegion("");

		assertEquals("", arguments.getRegion(), "空区域应该可以设置");
	}

	@Test
	@DisplayName("测试完整参数配置")
	void testCompleteConfiguration() {
		byte[] data = "完整测试数据".getBytes(StandardCharsets.UTF_8);
		InputStream inputStream = new ByteArrayInputStream(data);

		arguments.setInputStream(inputStream);
		arguments.setObjectSize(data.length);
		arguments.setPartSize(5 * 1024 * 1024);
		arguments.setContentType("text/plain");

		assertEquals(inputStream, arguments.getInputStream());
		assertEquals(data.length, arguments.getObjectSize());
		assertEquals(5 * 1024 * 1024, arguments.getPartSize());
		assertEquals("text/plain", arguments.getContentType());
		assertEquals(TEST_BUCKET_NAME, arguments.getBucketName());
		assertEquals(TEST_OBJECT_NAME, arguments.getObjectName());
		assertEquals(TEST_REGION, arguments.getRegion());
	}

	@Test
	@DisplayName("测试 toString 方法包含所有重要信息")
	void testToStringContainsAllInformation() {
		byte[] data = "toString test".getBytes(StandardCharsets.UTF_8);
		arguments.setInputStream(new ByteArrayInputStream(data));
		arguments.setObjectSize(data.length);
		arguments.setPartSize(1024);
		arguments.setContentType("text/plain");

		String toString = arguments.toString();

		assertTrue(toString.contains(TEST_BUCKET_NAME), "toString 应该包含桶名称");
		assertTrue(toString.contains(TEST_OBJECT_NAME), "toString 应该包含对象名称");
		assertTrue(toString.contains(TEST_REGION), "toString 应该包含区域");
		assertTrue(toString.contains("text/plain"), "toString 应该包含内容类型");
		assertTrue(toString.contains(String.valueOf(data.length)), "toString 应该包含对象大小");
		assertTrue(toString.contains("1024"), "toString 应该包含分片大小");
	}

	@Test
	@DisplayName("测试设置不同的对象大小值")
	void testSetDifferentObjectSizes() {
		// 测试小文件
		arguments.setObjectSize(1024);
		assertEquals(1024, arguments.getObjectSize());

		// 测试中等文件
		arguments.setObjectSize(10 * 1024 * 1024); // 10MB
		assertEquals(10 * 1024 * 1024, arguments.getObjectSize());

		// 测试大文件（接近 5GB 限制）
		arguments.setObjectSize(5L * 1024 * 1024 * 1024 - 1);
		assertEquals(5L * 1024 * 1024 * 1024 - 1, arguments.getObjectSize());

		// 测试零大小
		arguments.setObjectSize(0);
		assertEquals(0, arguments.getObjectSize());
	}

	@Test
	@DisplayName("测试设置不同的分片大小值")
	void testSetDifferentPartSizes() {
		// 测试最小分片大小 (100KB)
		arguments.setPartSize(100 * 1024);
		assertEquals(100 * 1024, arguments.getPartSize());

		// 测试常用分片大小 (5MB)
		arguments.setPartSize(5 * 1024 * 1024);
		assertEquals(5 * 1024 * 1024, arguments.getPartSize());

		// 测试大分片 (100MB)
		arguments.setPartSize(100 * 1024 * 1024);
		assertEquals(100 * 1024 * 1024, arguments.getPartSize());

		// 测试零分片大小
		arguments.setPartSize(0);
		assertEquals(0, arguments.getPartSize());
	}

	@Test
	@DisplayName("测试对象名称包含路径")
	void testObjectNameWithPath() {
		String objectNameWithPath = "folder/subfolder/file.txt";
		arguments.setObjectName(objectNameWithPath);

		assertEquals(objectNameWithPath, arguments.getObjectName());
	}

	@Test
	@DisplayName("测试对象名称包含特殊字符")
	void testObjectNameWithSpecialCharacters() {
		String[] specialNames = { "file-with-dash.txt", "file_with_underscore.txt", "file.with.dots.txt",
				"文件名中文.txt", "file with spaces.txt" };

		for (String name : specialNames) {
			PutObjectArguments args = new PutObjectArguments();
			args.setBucketName(TEST_BUCKET_NAME);
			args.setObjectName(name);
			assertEquals(name, args.getObjectName());
		}
	}

	@Test
	@DisplayName("测试设置不同的桶名称格式")
	void testSetBucketNameFormats() {
		// 小写桶名
		PutObjectArguments args1 = new PutObjectArguments();
		args1.setBucketName("my-bucket");
		assertEquals("my-bucket", args1.getBucketName());

		// 带数字的桶名
		PutObjectArguments args2 = new PutObjectArguments();
		args2.setBucketName("bucket123");
		assertEquals("bucket123", args2.getBucketName());

		// 带端点的桶名
		PutObjectArguments args3 = new PutObjectArguments();
		args3.setBucketName("my-bucket.endpoint");
		assertEquals("my-bucket.endpoint", args3.getBucketName());
	}

	@Test
	@DisplayName("测试多次更新相同参数")
	void testUpdateSameParameterMultipleTimes() {
		// 多次设置对象大小
		arguments.setObjectSize(100);
		assertEquals(100, arguments.getObjectSize());

		arguments.setObjectSize(200);
		assertEquals(200, arguments.getObjectSize());

		arguments.setObjectSize(300);
		assertEquals(300, arguments.getObjectSize());

		// 多次设置内容类型
		arguments.setContentType("text/plain");
		assertEquals("text/plain", arguments.getContentType());

		arguments.setContentType("application/json");
		assertEquals("application/json", arguments.getContentType());

		arguments.setContentType("image/png");
		assertEquals("image/png", arguments.getContentType());
	}

	@Test
	@DisplayName("测试不同区域的设置")
	void testSetDifferentRegions() {
		// 阿里云区域
		arguments.setRegion("oss-cn-hangzhou");
		assertEquals("oss-cn-hangzhou", arguments.getRegion());

		// AWS 区域
		arguments.setRegion("us-east-1");
		assertEquals("us-east-1", arguments.getRegion());

		// 华为云区域
		arguments.setRegion("cn-north-4");
		assertEquals("cn-north-4", arguments.getRegion());

		// 百度云区域
		arguments.setRegion("bj");
		assertEquals("bj", arguments.getRegion());
	}

}
