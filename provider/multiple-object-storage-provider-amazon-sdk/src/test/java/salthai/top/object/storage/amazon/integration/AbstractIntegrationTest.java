package salthai.top.object.storage.amazon.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salthai.top.object.storage.amazon.operations.S3BucketOperations;
import salthai.top.object.storage.amazon.operations.S3ObjectOperations;
import salthai.top.object.storage.core.domain.object.ObjectSummary;
import salthai.top.object.storage.core.domain.object.ListObjectsDomain;
import salthai.top.object.storage.core.operations.ObjectOperations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * 集成测试基类
 * <p>
 * 提供测试基础设施和通用方法，参考 Spring Data Redis 的测试风格
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 14:00
 */
public abstract class AbstractIntegrationTest {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTest.class);

	protected static Properties testProperties;

	protected String bucketName;

	protected String testObjectPrefix;

	protected boolean isIntegrationTestEnabled;

	@BeforeAll
	static void loadTestProperties() throws IOException {
		testProperties = new Properties();
		try (var input = AbstractIntegrationTest.class.getClassLoader()
			.getResourceAsStream("application-test.properties")) {
			if (input != null) {
				testProperties.load(input);
			}
		}
	}

	@BeforeEach
	void setUp() {
		// 生成唯一的测试标识符
		String testId = UUID.randomUUID().toString().substring(0, 8);

		// 从配置或环境变量读取设置
		isIntegrationTestEnabled = Boolean
			.parseBoolean(System.getProperty("test.integration.enabled", "true"));

		if (isIntegrationTestEnabled) {
			testObjectPrefix = "test-object-" + testId + "-";
			bucketName = getRequiredProperty(getBucketNamePropertyKey());

			logger.info("==> 集成测试启用");
			logger.info("==> 测试桶名: {}", bucketName);
			logger.info("==> 测试对象前缀: {}", testObjectPrefix);
		}
		else {
			logger.warn("==> 集成测试已禁用，跳过需要真实连接的测试");
		}
	}

	@AfterEach
	void tearDown() {
		if (isIntegrationTestEnabled && Boolean.parseBoolean(System.getProperty("test.cleanup.enabled", "true"))) {
			cleanupTestObjects();
		}
	}

	/**
	 * 获取必需的配置属性，如果不存在则抛出异常
	 */
	protected String getRequiredProperty(String key) {
		String value = testProperties.getProperty(key);
		if (value == null || value.trim().isEmpty() || value.startsWith("your-")) {
			throw new TestConfigurationException(
					String.format("必需的配置属性 '%s' 未设置或使用默认值。请在 application-test.properties 中配置。", key));
		}
		return value;
	}

	/**
	 * 获取可选的配置属性，如果不存在则返回默认值
	 */
	protected String getProperty(String key, String defaultValue) {
		String value = testProperties.getProperty(key);
		return (value == null || value.trim().isEmpty()) ? defaultValue : value;
	}

	/**
	 * 获取桶名称配置的属性键
	 */
	protected abstract String getBucketNamePropertyKey();

	/**
	 * 获取对象操作实例
	 */
	protected abstract ObjectOperations getObjectOperations();

	/**
	 * 清理测试期间创建的对象
	 */
	protected void cleanupTestObjects() {
		try {
			ObjectOperations objectOperations = getObjectOperations();
			ListObjectsDomain listResult = objectOperations.listObjects(bucketName);
			List<ObjectSummary> objects = listResult.getObjectSummaries();

			int deletedCount = 0;
			for (ObjectSummary summary : objects) {
				String objectName = summary.getObjectName();
				if (objectName.startsWith(testObjectPrefix)) {
					try {
						objectOperations.delObject(bucketName, objectName);
						deletedCount++;
						logger.debug("==> 清理测试对象: {}", objectName);
					}
					catch (Exception e) {
						logger.warn("==> 清理测试对象失败: {} - {}", objectName, e.getMessage());
					}
				}
			}

			if (deletedCount > 0) {
				logger.info("==> 清理了 {} 个测试对象", deletedCount);
			}
		}
		catch (Exception e) {
			logger.error("==> 清理测试对象时发生错误", e);
		}
	}

	/**
	 * 生成唯一的测试对象名称
	 */
	protected String generateTestObjectName(String suffix) {
		return testObjectPrefix + suffix;
	}

	/**
	 * 创建测试用的字节数组输入流
	 */
	protected InputStream createTestInputStream(String content) {
		return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 创建大文件测试用的输入流
	 */
	protected InputStream createLargeTestInputStream(int sizeInMB) {
		byte[] data = new byte[sizeInMB * 1024 * 1024];
		// 填充随机数据
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (i % 256);
		}
		return new ByteArrayInputStream(data);
	}

	/**
	 * 跳过测试（当集成测试未启用时）
	 */
	protected void skipIfIntegrationTestDisabled() {
		org.junit.jupiter.api.Assumptions.assumeTrue(isIntegrationTestEnabled,
				"集成测试已禁用，跳过此测试");
	}

	/**
	 * 测试配置异常
	 */
	public static class TestConfigurationException extends RuntimeException {

		public TestConfigurationException(String message) {
			super(message);
		}

		public TestConfigurationException(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
