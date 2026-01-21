package salthai.top.object.storage.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import salthai.top.object.storage.core.exceptions.FileWrapperException;
import salthai.top.object.storage.core.wrapper.FileWrapper;
import salthai.top.object.storage.core.wrapper.adapter.ByteArrayFileWrapperAdapter;
import salthai.top.object.storage.core.wrapper.adapter.DiskFileWrapperAdapter;
import salthai.top.object.storage.core.wrapper.adapter.FileWrapperAdapter;
import salthai.top.object.storage.core.wrapper.adapter.HttpFileWrapperAdapter;
import salthai.top.object.storage.core.wrapper.adapter.InputStreamWrapperAdapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文件包装工具类详细测试
 * <p>
 * 测试文件包装工具类的各种使用场景，包括不同类型的文件源、参数组合和异常处理
 * </p>
 *
 * @author Kuang HaiBo 2024/5/16 10:54
 */
@DisplayName("文件包装工具类测试")
public class FileWrappersTest {

	@Test
	@DisplayName("测试确定磁盘文件适配器")
	void testDetermineDiskFileAdapter() {
		File file = new File("/data/test.png");
		FileWrapperAdapter adapter = FileWrappers.determineAdapter(file);
		assertInstanceOf(DiskFileWrapperAdapter.class, adapter, "应该返回磁盘文件适配器");
	}

	@Test
	@DisplayName("测试确定字节数组适配器")
	void testDetermineByteArrayAdapter() {
		byte[] bytes = new byte[] { 1, 2, 3, 4, 5 };
		FileWrapperAdapter adapter = FileWrappers.determineAdapter(bytes);
		assertInstanceOf(ByteArrayFileWrapperAdapter.class, adapter, "应该返回字节数组适配器");
	}

	@Test
	@DisplayName("测试确定输入流适配器")
	void testDetermineInputStreamAdapter() {
		InputStream inputStream = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
		FileWrapperAdapter adapter = FileWrappers.determineAdapter(inputStream);
		assertInstanceOf(InputStreamWrapperAdapter.class, adapter, "应该返回输入流适配器");
	}

	@Test
	@DisplayName("测试确定 HTTP URL 适配器")
	void testDetermineHttpUrlAdapter() throws Exception {
		java.net.URL url = new java.net.URL("https://example.com/file.pdf");
		FileWrapperAdapter adapter = FileWrappers.determineAdapter(url);
		assertInstanceOf(HttpFileWrapperAdapter.class, adapter, "应该返回 HTTP URL 适配器");
	}

	@Test
	@DisplayName("测试包装文件对象 - 仅传入源")
	void testWrapperFileWithSourceOnly(@TempDir Path tempDir) throws IOException {
		Path testFile = tempDir.resolve("test.txt");
		Files.writeString(testFile, "Hello, World!");

		FileWrapper wrapper = FileWrappers.wrapper(testFile.toFile());
		assertNotNull(wrapper, "包装对象不应为 null");
		assertNotNull(wrapper.getInputStream(), "应该能够获取输入流");
		assertTrue(wrapper.getByteSize() > 0, "文件大小应该大于 0");
	}

	@Test
	@DisplayName("测试包装字节数组 - 仅传入源")
	void testWrapperByteArrayWithSourceOnly() {
		byte[] bytes = "测试内容".getBytes();
		FileWrapper wrapper = FileWrappers.wrapper(bytes);

		assertNotNull(wrapper, "包装对象不应为 null");
		assertEquals(bytes.length, wrapper.getByteSize(), "字节数组大小应该匹配");
	}

	@Test
	@DisplayName("测试包装输入流 - 指定文件大小")
	void testWrapperInputStreamWithSize() {
		byte[] data = "流内容测试".getBytes();
		InputStream inputStream = new ByteArrayInputStream(data);
		long fileSize = data.length;

		FileWrapper wrapper = FileWrappers.wrapper(inputStream, fileSize);

		assertNotNull(wrapper, "包装对象不应为 null");
		assertEquals(fileSize, wrapper.getByteSize(), "文件大小应该匹配指定值");
	}

	@Test
	@DisplayName("测试包装文件对象 - 指定内容类型")
	void testWrapperFileWithContentType(@TempDir Path tempDir) throws IOException {
		Path testFile = tempDir.resolve("test.jpg");
		Files.writeString(testFile, "fake image content");

		String customContentType = "image/jpeg";
		FileWrapper wrapper = FileWrappers.wrapper(testFile.toFile(), customContentType);

		assertNotNull(wrapper, "包装对象不应为 null");
		assertEquals(customContentType, wrapper.getContentType(), "内容类型应该匹配指定值");
	}

	@Test
	@DisplayName("测试包装字节数组 - 指定内容类型和大小")
	void testWrapperByteArrayWithContentTypeAndSize() {
		byte[] bytes = "完整测试数据".getBytes();
		String contentType = "text/plain";
		long size = (long) bytes.length;

		FileWrapper wrapper = FileWrappers.wrapper(bytes, contentType, size);

		assertNotNull(wrapper, "包装对象不应为 null");
		assertEquals(contentType, wrapper.getContentType(), "内容类型应该匹配");
		assertEquals(size, wrapper.getByteSize(), "文件大小应该匹配");
	}

	@Test
	@DisplayName("测试包装 null 源应该抛出异常")
	void testWrapperNullSource() {
		assertThrows(NullPointerException.class, () -> FileWrappers.wrapper(null),
				"包装 null 源应该抛出 NullPointerException");
	}

	@Test
	@DisplayName("测试包装不支持的类型应该抛出异常")
	void testWrapperUnsupportedType() {
		Object unsupportedObject = new Object();
		assertThrows(FileWrapperException.class, () -> FileWrappers.wrapper(unsupportedObject),
				"包装不支持的类型应该抛出 FileWrapperException");
	}

	@Test
	@DisplayName("测试包装文件对象 - 完整参数")
	void testWrapperFileWithFullParameters(@TempDir Path tempDir) throws IOException {
		Path testFile = tempDir.resolve("document.pdf");
		Files.writeString(testFile, "PDF content");

		String contentType = "application/pdf";
		long expectedSize = Files.size(testFile);

		FileWrapper wrapper = FileWrappers.wrapper(testFile.toFile(), contentType, expectedSize);

		assertNotNull(wrapper, "包装对象不应为 null");
		assertEquals(contentType, wrapper.getContentType(), "内容类型应该匹配");
		assertEquals(expectedSize, wrapper.getByteSize(), "文件大小应该匹配");
	}

	@Test
	@DisplayName("测试多个连续包装操作")
	void testMultipleWrapperOperations(@TempDir Path tempDir) throws IOException {
		// 测试文件
		Path testFile = tempDir.resolve("multi.txt");
		String content = "多次包装测试";
		Files.writeString(testFile, content);

		// 第一次包装
		FileWrapper wrapper1 = FileWrappers.wrapper(testFile.toFile());
		assertNotNull(wrapper1);

		// 第二次包装字节数组
		byte[] bytes = content.getBytes();
		FileWrapper wrapper2 = FileWrappers.wrapper(bytes);
		assertNotNull(wrapper2);

		// 第三次包装输入流
		InputStream stream = new ByteArrayInputStream(bytes);
		FileWrapper wrapper3 = FileWrappers.wrapper(stream, (long) bytes.length);
		assertNotNull(wrapper3);

		// 验证所有包装器都有正确的属性
		assertEquals(wrapper1.getByteSize(), wrapper2.getByteSize());
		assertEquals(wrapper2.getByteSize(), wrapper3.getByteSize());
	}

	@Test
	@DisplayName("测试适配器顺序和优先级")
	void testAdapterPriority() {
		// 测试不同类型的源能正确匹配到对应的适配器
		File file = new File("/test/path");
		byte[] bytes = new byte[10];
		InputStream stream = new ByteArrayInputStream(bytes);

		FileWrapperAdapter fileAdapter = FileWrappers.determineAdapter(file);
		FileWrapperAdapter bytesAdapter = FileWrappers.determineAdapter(bytes);
		FileWrapperAdapter streamAdapter = FileWrappers.determineAdapter(stream);

		assertInstanceOf(DiskFileWrapperAdapter.class, fileAdapter);
		assertInstanceOf(ByteArrayFileWrapperAdapter.class, bytesAdapter);
		assertInstanceOf(InputStreamWrapperAdapter.class, streamAdapter);

		// 确保不同的类型返回不同的适配器实例
		assertNotSame(fileAdapter, bytesAdapter);
		assertNotSame(bytesAdapter, streamAdapter);
	}

	@Test
	@DisplayName("测试包装 URL 对象")
	void testWrapperUrlObject() throws Exception {
		java.net.URL url = new java.net.URL("https://example.com/test.json");
		FileWrapper wrapper = FileWrappers.wrapper(url);

		assertNotNull(wrapper, "URL 包装对象不应为 null");
		assertNotNull(wrapper.getInputStream(), "应该能够获取输入流");
	}

	@Test
	@DisplayName("测试包装 URI 对象")
	void testWrapperUriObject() {
		java.net.URI uri = java.net.URI.create("https://example.com/data.xml");
		FileWrapper wrapper = FileWrappers.wrapper(uri);

		assertNotNull(wrapper, "URI 包装对象不应为 null");
		assertNotNull(wrapper.getInputStream(), "应该能够获取输入流");
	}

	@Test
	@DisplayName("测试包装空字节数组")
	void testWrapperEmptyByteArray() {
		byte[] emptyBytes = new byte[0];
		FileWrapper wrapper = FileWrappers.wrapper(emptyBytes);

		assertNotNull(wrapper, "空字节数组包装对象不应为 null");
		assertEquals(0L, wrapper.getByteSize(), "空数组大小应该为 0");
	}

	@Test
	@DisplayName("测试包装大文件")
	void testWrapperLargeFile(@TempDir Path tempDir) throws IOException {
		Path largeFile = tempDir.resolve("large.dat");
		// 创建 1MB 的文件
		byte[] largeContent = new byte[1024 * 1024];
		for (int i = 0; i < largeContent.length; i++) {
			largeContent[i] = (byte) (i % 256);
		}
		Files.write(largeFile, largeContent);

		FileWrapper wrapper = FileWrappers.wrapper(largeFile.toFile());

		assertNotNull(wrapper);
		assertEquals(largeContent.length, wrapper.getByteSize(), "大文件大小应该正确");
	}

	@Test
	@DisplayName("测试设置和获取内容类型")
	void testSetAndGetContentType(@TempDir Path tempDir) throws IOException {
		Path testFile = tempDir.resolve("content-type-test.txt");
		Files.writeString(testFile, "content type test");

		FileWrapper wrapper = FileWrappers.wrapper(testFile.toFile());

		// 测试默认内容类型
		String originalType = wrapper.getContentType();
		assertNotNull(originalType, "默认内容类型不应为 null");

		// 设置新的内容类型
		String newType = "text/plain; charset=utf-8";
		wrapper.setContentType(newType);
		assertEquals(newType, wrapper.getContentType(), "内容类型应该更新");

		// 设置 null 内容类型（应该使用默认值）
		wrapper.setContentType(null);
		assertNotNull(wrapper.getContentType(), "内容类型不应为 null");
	}

	@Test
	@DisplayName("测试设置和获取文件大小")
	void testSetAndGetByteSize(@TempDir Path tempDir) throws IOException {
		Path testFile = tempDir.resolve("size-test.bin");
		byte[] content = "size test content".getBytes();
		Files.write(testFile, content);

		FileWrapper wrapper = FileWrappers.wrapper(testFile.toFile());

		// 获取原始大小
		Long originalSize = wrapper.getByteSize();
		assertEquals(content.length, originalSize, "原始大小应该匹配");

		// 设置新的大小
		Long newSize = 9999L;
		wrapper.setByteSize(newSize);
		assertEquals(newSize, wrapper.getByteSize(), "大小应该更新");

		// 设置 null 大小
		wrapper.setByteSize(null);
		assertNull(wrapper.getByteSize(), "大小应该可以为 null");
	}

}
