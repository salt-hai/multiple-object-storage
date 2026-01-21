package salthai.top.object.storage.amazon.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import salthai.top.object.storage.amazon.S3Constants;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.client.S3ClientPackageFactory;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.amazon.operations.S3ObjectOperations;
import salthai.top.object.storage.core.FileWrappers;
import salthai.top.object.storage.core.domain.object.GetObjectDomain;
import salthai.top.object.storage.core.domain.object.ListObjectsDomain;
import salthai.top.object.storage.core.domain.object.ObjectMetadataDomain;
import salthai.top.object.storage.core.domain.object.ObjectSummary;
import salthai.top.object.storage.core.domain.object.PutObjectDomain;
import salthai.top.object.storage.core.enums.HttpMethod;
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AWS S3 对象操作集成测试
 * <p>
 * 参考 Spring Data Redis 的测试风格，使用 Testcontainers 进行真实的集成测试
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 14:30
 */
@DisplayName("AWS S3 对象操作集成测试")
@Testcontainers(disabledWithoutDocker = true)
class S3ObjectOperationsIntegrationTest extends AbstractIntegrationTest {

	// 使用 LocalStack 模拟 AWS S3 服务
	@Container
	static GenericContainer<?> localstack = new GenericContainer<>(
			DockerImageName.parse("localstack/localstack:3.0.2")).withExposedPorts(4566).withEnv("SERVICES", "s3")
			.withEnv("AWS_DEFAULT_REGION", "us-east-1").waitingFor(Wait.forLogMessage(".*Ready.*", 1));

	private ObjectOperations objectOperations;

	private ProviderClientManager<S3ClientPackage> clientManager;

	private S3ClientPackage s3Client;

	@Override
	protected String getBucketNamePropertyKey() {
		return "s3.bucket.name";
	}

	@Override
	protected ObjectOperations getObjectOperations() {
		return objectOperations;
	}

	@org.junit.jupiter.api.BeforeEach
	void setUpS3Client() {
		if (Boolean.parseBoolean(System.getProperty("test.integration.enabled", "true"))) {
			// 配置 S3 客户端连接到 LocalStack
			S3Properties properties = new S3Properties();
			properties.setEndpoint(
					String.format("http://%s:%d", localstack.getHost(), localstack.getMappedPort(4566)));
			properties.setAccessKey("test-access-key");
			properties.setSecretKey("test-secret-key");
			properties.setRegion("us-east-1");

			S3Configuration s3Configuration = S3Configuration.builder()
				.pathStyleAccessEnabled(true)
				.region(Region.US_EAST_1)
				.build();

			S3ClientPackageFactory factory = new S3ClientPackageFactory(properties, s3Configuration);
			clientManager = new DefaultProviderClientSingletonManager<>(factory);
			objectOperations = new S3ObjectOperations(clientManager);
			s3Client = clientManager.getClient();

			// 创建测试桶
			try {
				s3Client.getS3Client().createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
				logger.info("==> 测试桶创建成功: {}", bucketName);
			}
			catch (Exception e) {
				logger.warn("==> 测试桶可能已存在: {}", e.getMessage());
			}
		}
	}

	@Test
	@DisplayName("测试上传对象")
	void testPutObject() {
		skipIfIntegrationTestDisabled();

		// Given
		String objectName = generateTestObjectName("upload-test.txt");
		String content = "Hello, AWS S3! 这是测试内容。";
		var fileWrapper = FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8));

		// When
		PutObjectDomain result = objectOperations.putObject(bucketName, fileWrapper, objectName);

		// Then
		assertNotNull(result, "上传结果不应为 null");
		assertEquals(objectName, result.getObjectName(), "对象名称应该匹配");
		assertEquals(bucketName, result.getBucketName(), "桶名称应该匹配");
		assertTrue(objectOperations.doesObjectExist(bucketName, objectName), "对象应该存在");

		logger.info("==> 上传对象成功: {}", objectName);
	}

	@Test
	@DisplayName("测试上传文件")
	void testPutFile(@TempDir Path tempDir) throws IOException {
		skipIfIntegrationTestDisabled();

		// Given
		String objectName = generateTestObjectName("file-upload-test.pdf");
		Path testFile = tempDir.resolve("test-file.pdf");
		String content = "PDF 文件测试内容";
		Files.writeString(testFile, content);

		// When
		PutObjectDomain result = objectOperations.putObject(bucketName, FileWrappers.wrapper(testFile.toFile()),
				objectName);

		// Then
		assertNotNull(result);
		assertEquals(objectName, result.getObjectName());
		assertTrue(objectOperations.doesObjectExist(bucketName, objectName));

		logger.info("==> 上传文件成功: {}", objectName);
	}

	@Test
	@DisplayName("测试下载对象")
	void testGetObject() {
		skipIfIntegrationTestDisabled();

		// Given - 先上传一个对象
		String objectName = generateTestObjectName("download-test.txt");
		String content = "下载测试内容 - Download Test Content";
		objectOperations.putObject(bucketName, FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)),
				objectName);

		// When
		GetObjectDomain result = objectOperations.getObject(bucketName, objectName);

		// Then
		assertNotNull(result, "下载结果不应为 null");
		assertEquals(objectName, result.getObjectName(), "对象名称应该匹配");
		assertEquals(bucketName, result.getBucketName(), "桶名称应该匹配");

		byte[] downloadedBytes = result.getObjectBytes();
		assertNotNull(downloadedBytes, "下载的字节不应为 null");
		String downloadedContent = new String(downloadedBytes, StandardCharsets.UTF_8);
		assertEquals(content, downloadedContent, "下载的内容应该匹配");

		logger.info("==> 下载对象成功: {} (大小: {} bytes)", objectName, downloadedBytes.length);
	}

	@Test
	@DisplayName("测试获取对象元数据")
	void testGetObjectMetadata() {
		skipIfIntegrationTestDisabled();

		// Given
		String objectName = generateTestObjectName("metadata-test.json");
		String content = "{\"message\": \"metadata test\"}";
		objectOperations.putObject(bucketName, FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)),
				objectName);

		// When
		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);

		// Then
		assertNotNull(metadata, "元数据不应为 null");
		assertEquals(objectName, metadata.getObjectName(), "对象名称应该匹配");
		assertEquals(bucketName, metadata.getBucketName(), "桶名称应该匹配");
		assertEquals(content.length(), metadata.getObjectSize(), "对象大小应该匹配");
		assertNotNull(metadata.getContentType(), "内容类型不应为 null");

		logger.info("==> 获取元数据成功: {} (大小: {}, 类型: {})", objectName, metadata.getObjectSize(),
				metadata.getContentType());
	}

	@Test
	@DisplayName("测试列举对象")
	void testListObjects() {
		skipIfIntegrationTestDisabled();

		// Given - 上传多个对象
		String prefix = generateTestObjectName("list/");
		for (int i = 1; i <= 5; i++) {
			String objectName = prefix + "file-" + i + ".txt";
			objectOperations.putObject(bucketName,
					FileWrappers.wrapper(("Content " + i).getBytes(StandardCharsets.UTF_8)), objectName);
		}

		// When
		ListObjectsDomain result = objectOperations.listObjects(bucketName);

		// Then
		assertNotNull(result, "列举结果不应为 null");
		List<ObjectSummary> objects = result.getObjectSummaries();
		assertNotNull(objects, "对象列表不应为 null");

		// 验证包含我们上传的测试对象
		long testObjectCount = objects.stream().filter(obj -> obj.getObjectName().startsWith(prefix)).count();
		assertTrue(testObjectCount >= 5, "应该至少找到 5 个测试对象");

		logger.info("==> 列举对象成功: 找到 {} 个对象 (其中 {} 个测试对象)", objects.size(), testObjectCount);
	}

	@Test
	@DisplayName("测试删除对象")
	void testDeleteObject() {
		skipIfIntegrationTestDisabled();

		// Given - 上传一个对象
		String objectName = generateTestObjectName("delete-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("to be deleted".getBytes(StandardCharsets.UTF_8)), objectName);
		assertTrue(objectOperations.doesObjectExist(bucketName, objectName), "对象应该存在");

		// When
		boolean result = objectOperations.delObject(bucketName, objectName);

		// Then
		assertTrue(result, "删除操作应该返回 true");
		assertFalse(objectOperations.doesObjectExist(bucketName, objectName), "对象不应该存在");

		logger.info("==> 删除对象成功: {}", objectName);
	}

	@Test
	@DisplayName("测试检查对象是否存在")
	void testDoesObjectExist() {
		skipIfIntegrationTestDisabled();

		// Given
		String existingObject = generateTestObjectName("exist-test.txt");
		String nonExistingObject = generateTestObjectName("non-existent.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("exist test".getBytes(StandardCharsets.UTF_8)), existingObject);

		// When & Then
		assertTrue(objectOperations.doesObjectExist(bucketName, existingObject), "存在的对象应该返回 true");
		assertFalse(objectOperations.doesObjectExist(bucketName, nonExistingObject), "不存在的对象应该返回 false");

		logger.info("==> 对象存在性检查成功");
	}

	@Test
	@DisplayName("测试生成预签名 URL")
	void testGenPreSignedUrl() {
		skipIfIntegrationTestDisabled();

		// Given
		String objectName = generateTestObjectName("presigned-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("presigned url test".getBytes(StandardCharsets.UTF_8)), objectName);

		// When
		String url = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.GET, Duration.ofHours(1));

		// Then
		assertNotNull(url, "预签名 URL 不应为 null");
		assertTrue(url.contains(bucketName), "URL 应该包含桶名");
		assertTrue(url.contains(objectName), "URL 应该包含对象名");

		logger.info("==> 生成预签名 URL 成功: {}", url);
	}

	@Test
	@DisplayName("测试创建文件夹")
	void testMkdir() throws Exception {
		skipIfIntegrationTestDisabled();

		// Given
		String folderPath = generateTestObjectName("folder/test-folder/");

		// When
		boolean result = objectOperations.mkdir(bucketName, folderPath);

		// Then
		assertTrue(result, "创建文件夹应该成功");

		logger.info("==> 创建文件夹成功: {}", folderPath);
	}

	@Test
	@DisplayName("测试上传大文件")
	void testUploadLargeFile() {
		skipIfIntegrationTestDisabled();

		// Given - 创建 5MB 的数据
		String objectName = generateTestObjectName("large-file.bin");
		int sizeInMB = 5;
		byte[] largeData = new byte[sizeInMB * 1024 * 1024];
		for (int i = 0; i < largeData.length; i++) {
			largeData[i] = (byte) (i % 256);
		}

		// When
		PutObjectDomain result = objectOperations.putObject(bucketName, FileWrappers.wrapper(largeData), objectName);

		// Then
		assertNotNull(result);
		assertEquals(objectName, result.getObjectName());

		// 验证文件大小
		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);
		assertEquals(largeData.length, metadata.getObjectSize(), "文件大小应该匹配");

		logger.info("==> 上传大文件成功: {} (大小: {} MB)", objectName, sizeInMB);
	}

	@Test
	@DisplayName("测试上传中文字符文件名")
	void testUploadChineseFilename() {
		skipIfIntegrationTestDisabled();

		// Given
		String objectName = generateTestObjectName("中文-测试-文件.txt");
		String content = "测试中文文件名";

		// When
		PutObjectDomain result = objectOperations.putObject(bucketName,
				FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)), objectName);

		// Then
		assertNotNull(result);
		assertTrue(objectOperations.doesObjectExist(bucketName, objectName));

		logger.info("==> 上传中文文件名成功: {}", objectName);
	}

}
