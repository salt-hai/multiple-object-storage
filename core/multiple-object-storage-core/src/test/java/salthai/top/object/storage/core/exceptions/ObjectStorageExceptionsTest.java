package salthai.top.object.storage.core.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("对象存储异常测试")
class ObjectStorageExceptionsTest {

	@Test
	@DisplayName("测试 ObjectClientException - 仅消息")
	void testObjectClientExceptionWithMessage() {
		String message = "客户端连接失败";
		ObjectClientException exception = new ObjectClientException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	@DisplayName("测试 ObjectClientException - 消息和原因")
	void testObjectClientExceptionWithMessageAndCause() {
		String message = "客户端执行失败";
		Throwable cause = new IOException("连接超时");
		ObjectClientException exception = new ObjectClientException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
		assertTrue(exception.getCause() instanceof IOException);
	}

	@Test
	@DisplayName("测试 ObjectClientException - 仅原因")
	void testObjectClientExceptionWithCause() {
		Throwable cause = new RuntimeException("内部错误");
		ObjectClientException exception = new ObjectClientException(cause);

		assertNotNull(exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 BucketException - 仅消息")
	void testBucketExceptionWithMessage() {
		String message = "存储桶不存在";
		BucketException exception = new BucketException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	@DisplayName("测试 BucketException - 消息和原因")
	void testBucketExceptionWithMessageAndCause() {
		String message = "创建存储桶失败";
		Throwable cause = new IllegalStateException("权限不足");
		BucketException exception = new BucketException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 BucketException - 仅原因")
	void testBucketExceptionWithCause() {
		Throwable cause = new IllegalArgumentException("无效的存储桶名称");
		BucketException exception = new BucketException(cause);

		assertNotNull(exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 PutFileException - 仅消息")
	void testPutFileExceptionWithMessage() {
		String message = "文件上传失败";
		PutFileException exception = new PutFileException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 PutFileException - 消息和原因")
	void testPutFileExceptionWithMessageAndCause() {
		String message = "文件上传超时";
		Throwable cause = new IOException("网络连接断开");
		PutFileException exception = new PutFileException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 GetFileException - 仅消息")
	void testGetFileExceptionWithMessage() {
		String message = "文件下载失败";
		GetFileException exception = new GetFileException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 GetFileException - 消息和原因")
	void testGetFileExceptionWithMessageAndCause() {
		String message = "文件不存在";
		Throwable cause = new IllegalArgumentException("无效的文件路径");
		GetFileException exception = new GetFileException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 DelFileException - 仅消息")
	void testDelFileExceptionWithMessage() {
		String message = "文件删除失败";
		DelFileException exception = new DelFileException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 DelFileException - 消息和原因")
	void testDelFileExceptionWithMessageAndCause() {
		String message = "批量删除失败";
		Throwable cause = new UnsupportedOperationException("批量删除未实现");
		DelFileException exception = new DelFileException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 CopyFileException - 仅消息")
	void testCopyFileExceptionWithMessage() {
		String message = "文件复制失败";
		CopyFileException exception = new CopyFileException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 CopyFileException - 消息和原因")
	void testCopyFileExceptionWithMessageAndCause() {
		String message = "跨区域复制失败";
		Throwable cause = new IllegalStateException("区域不支持");
		CopyFileException exception = new CopyFileException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 FileWrapperException - 仅消息")
	void testFileWrapperExceptionWithMessage() {
		String message = "文件包装失败";
		FileWrapperException exception = new FileWrapperException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 FileWrapperException - 消息和原因")
	void testFileWrapperExceptionWithMessageAndCause() {
		String message = "不支持的文件类型";
		Throwable cause = new IllegalArgumentException("不支持的类型: CustomType");
		FileWrapperException exception = new FileWrapperException(message, cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试 ObjectStorageConfigPropertyException - 仅消息")
	void testObjectStorageConfigPropertyExceptionWithMessage() {
		String message = "配置属性缺失: endpoint";
		ObjectStorageConfigPropertyException exception = new ObjectStorageConfigPropertyException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
		assertTrue(exception instanceof ObjectStorageException);
	}

	@Test
	@DisplayName("测试 ObjectStorageConfigPropertyException - 消息和原因")
	void testObjectStorageConfigPropertyExceptionWithMessageAndCause() {
		String message = "配置解析失败";
		Throwable cause = new IllegalStateException("配置文件格式错误");
		ObjectStorageConfigPropertyException exception = new ObjectStorageConfigPropertyException(message,
				cause);

		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试异常类型继承关系")
	void testExceptionInheritance() {
		ObjectClientException clientException = new ObjectClientException("test");
		BucketException bucketException = new BucketException("test");
		PutFileException putFileException = new PutFileException("test");
		GetFileException getFileException = new GetFileException("test");
		DelFileException delFileException = new DelFileException("test");
		CopyFileException copyFileException = new CopyFileException("test");
		FileWrapperException fileWrapperException = new FileWrapperException("test");
		ObjectStorageConfigPropertyException configException = new ObjectStorageConfigPropertyException(
				"test");

		assertTrue(clientException instanceof ObjectStorageException);
		assertTrue(bucketException instanceof ObjectStorageException);
		assertTrue(putFileException instanceof ObjectStorageException);
		assertTrue(getFileException instanceof ObjectStorageException);
		assertTrue(delFileException instanceof ObjectStorageException);
		assertTrue(copyFileException instanceof ObjectStorageException);
		assertTrue(fileWrapperException instanceof ObjectStorageException);
		assertTrue(configException instanceof ObjectStorageException);

		assertTrue(clientException instanceof RuntimeException);
		assertTrue(bucketException instanceof RuntimeException);
	}

	@Test
	@DisplayName("测试异常堆栈跟踪")
	void testExceptionStackTrace() {
		Throwable cause = new IOException("底层IO错误");
		BucketException exception = new BucketException("存储桶操作失败", cause);

		StackTraceElement[] stackTrace = exception.getStackTrace();
		assertNotNull(stackTrace);
		assertTrue(stackTrace.length > 0);

		assertEquals(cause, exception.getCause());
		assertNotNull(exception.getCause().getStackTrace());
	}

	@Test
	@DisplayName("测试异常消息为空字符串")
	void testExceptionWithEmptyMessage() {
		ObjectClientException exception = new ObjectClientException("");
		assertEquals("", exception.getMessage());
	}

	@Test
	@DisplayName("测试异常链")
	void testExceptionChaining() {
		Throwable rootCause = new NullPointerException("根原因");
		Throwable intermediateCause = new IllegalArgumentException("中间原因", rootCause);
		BucketException exception = new BucketException("表面原因", intermediateCause);

		assertEquals("表面原因", exception.getMessage());
		assertEquals(intermediateCause, exception.getCause());
		assertEquals(rootCause, exception.getCause().getCause());
	}

	@Test
	@DisplayName("测试异常抑制")
	void testExceptionSuppression() {
		IOException ioException = new IOException("IO错误");
		BucketException exception = new BucketException("存储桶错误", ioException);

		exception.addSuppressed(new RuntimeException("抑制异常1"));
		exception.addSuppressed(new RuntimeException("抑制异常2"));

		assertEquals(2, exception.getSuppressed().length);
	}

	@Test
	@DisplayName("测试异常设置栈跟踪不可写")
	void testExceptionSetStackTrace() {
		ObjectClientException exception = new ObjectClientException("测试");
		StackTraceElement[] newStackTrace = new StackTraceElement[] {
				new StackTraceElement("com.example.Class", "method", "Class.java", 42) };

		exception.setStackTrace(newStackTrace);

		assertEquals(1, exception.getStackTrace().length);
		assertEquals("com.example.Class", exception.getStackTrace()[0].getClassName());
	}

	@Test
	@DisplayName("测试异常初始化原因")
	void testExceptionInitCause() {
		ObjectClientException exception = new ObjectClientException("测试");

		assertThrows(IllegalStateException.class, () -> exception.initCause(new RuntimeException("不应该成功")),
				"当原因已经设置时应该抛出异常");
	}

	@Test
	@DisplayName("测试所有自定义异常都可以正确实例化")
	void testAllExceptionsCanBeInstantiated() {
		assertDoesNotThrow(() -> new ObjectClientException("test"));
		assertDoesNotThrow(() -> new BucketException("test"));
		assertDoesNotThrow(() -> new PutFileException("test"));
		assertDoesNotThrow(() -> new GetFileException("test"));
		assertDoesNotThrow(() -> new DelFileException("test"));
		assertDoesNotThrow(() -> new CopyFileException("test"));
		assertDoesNotThrow(() -> new FileWrapperException("test"));
		assertDoesNotThrow(() -> new ObjectStorageConfigPropertyException("test"));
	}

}
