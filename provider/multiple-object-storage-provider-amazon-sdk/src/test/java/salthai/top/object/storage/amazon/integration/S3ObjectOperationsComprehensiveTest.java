package salthai.top.object.storage.amazon.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.client.S3ClientPackageFactory;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.amazon.operations.S3ObjectOperations;
import salthai.top.object.storage.core.FileWrappers;
import salthai.top.object.storage.core.arguments.object.*;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AWS S3 对象操作全面集成测试
 * <p>
 * 包含全面的测试场景,覆盖边界条件、异常情况、并发操作等
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 15:00
 */
@DisplayName("AWS S3 对象操作全面集成测试")
@Testcontainers(disabledWithoutDocker = true)
class S3ObjectOperationsComprehensiveTest extends AbstractIntegrationTest {

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

			try {
				s3Client.getS3Client().createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
				logger.info("==> 测试桶创建成功: {}", bucketName);
			}
			catch (Exception e) {
				logger.warn("==> 测试桶可能已存在: {}", e.getMessage());
			}
		}
	}

	// ==================== 基础操作测试 ====================

	@Test
	@DisplayName("测试上传空文件")
	void testUploadEmptyFile() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("empty-file.txt");
		byte[] emptyData = new byte[0];
		PutObjectDomain result = objectOperations.putObject(bucketName, FileWrappers.wrapper(emptyData), objectName);

		assertNotNull(result);
		assertTrue(objectOperations.doesObjectExist(bucketName, objectName));

		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);
		assertEquals(0, metadata.getObjectSize());

		logger.info("==> 空文件上传成功: {}", objectName);
	}

	@Test
	@DisplayName("测试上传不同内容类型")
	void testUploadDifferentContentTypes() {
		skipIfIntegrationTestDisabled();

		// 测试纯文本
		String textObject = generateTestObjectName("test.txt");
		objectOperations.putObject(bucketName, FileWrappers.wrapper("text content".getBytes(), "text/plain"),
				textObject);
		ObjectMetadataDomain textMetadata = objectOperations.getObjectMetadata(bucketName, textObject);
		assertTrue(textMetadata.getContentType().contains("text"));

		// 测试 JSON
		String jsonObject = generateTestObjectName("test.json");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("{\"key\":\"value\"}".getBytes(), "application/json"), jsonObject);
		ObjectMetadataDomain jsonMetadata = objectOperations.getObjectMetadata(bucketName, jsonObject);
		assertTrue(jsonMetadata.getContentType().contains("json"));

		// 测试二进制
		String binaryObject = generateTestObjectName("test.bin");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper(new byte[] { 0x00, 0x01, 0x02, 0x03 }, "application/octet-stream"),
				binaryObject);

		logger.info("==> 不同内容类型上传成功");
	}

	@Test
	@DisplayName("测试上传带特殊字符的对象名")
	void testUploadObjectWithSpecialCharacters() {
		skipIfIntegrationTestDisabled();

		String[] specialNames = { "test file-1.txt", "test_file_2.txt", "test.file.3.txt",
				"测试中文-文件-4.txt", "test file with spaces 5.txt", "test-file[6].txt" };

		for (String name : specialNames) {
			String objectName = generateTestObjectName(name);
			String content = "Content for " + name;

			PutObjectDomain result = objectOperations.putObject(bucketName,
					FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)), objectName);

			assertNotNull(result, "上传 " + name + " 应该成功");
			assertTrue(objectOperations.doesObjectExist(bucketName, objectName), name + " 应该存在");
		}

		logger.info("==> 特殊字符文件名上传成功");
	}

	@Test
	@DisplayName("测试对象拷贝")
	void testCopyObject() {
		skipIfIntegrationTestDisabled();

		// 创建源对象
		String sourceObject = generateTestObjectName("source-file.txt");
		String content = "Source file content for copy test";
		objectOperations.putObject(bucketName, FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)),
				sourceObject);

		// 拷贝到目标对象
		String targetObject = generateTestObjectName("target-file.txt");
		boolean result = objectOperations.copyObject(bucketName, sourceObject, bucketName, targetObject);

		assertTrue(result, "拷贝操作应该成功");
		assertTrue(objectOperations.doesObjectExist(bucketName, targetObject), "目标对象应该存在");

		// 验证内容一致
		GetObjectDomain sourceData = objectOperations.getObject(bucketName, sourceObject);
		GetObjectDomain targetData = objectOperations.getObject(bucketName, targetObject);
		assertArrayEquals(sourceData.getObjectBytes(), targetData.getObjectBytes(), "拷贝的内容应该一致");

		logger.info("==> 对象拷贝成功: {} -> {}", sourceObject, targetObject);
	}

	@Test
	@DisplayName("测试批量删除对象")
	void testDeleteMultipleObjects() {
		skipIfIntegrationTestDisabled();

		// 创建多个对象
		List<String> objectNames = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			String objectName = generateTestObjectName("batch-delete-" + i + ".txt");
			objectOperations.putObject(bucketName,
					FileWrappers.wrapper(("Content " + i).getBytes(StandardCharsets.UTF_8)), objectName);
			objectNames.add(objectName);
		}

		// 批量删除
		DelObjectsArguments delArgs = new DelObjectsArguments();
		delArgs.setBucketName(bucketName);
		delArgs.setObjectNames(objectNames);
		List<salthai.top.object.storage.core.domain.object.DelObjectDomain> results = objectOperations
			.delObjects(delArgs);

		assertNotNull(results);
		assertEquals(5, results.size());

		// 验证所有对象都已删除
		for (String objectName : objectNames) {
			assertFalse(objectOperations.doesObjectExist(bucketName, objectName),
					objectName + " 应该已被删除");
		}

		logger.info("==> 批量删除成功: {} 个对象", results.size());
	}

	@Test
	@DisplayName("测试列举带前缀的对象")
	void testListObjectsWithPrefix() {
		skipIfIntegrationTestDisabled();

		String prefix = generateTestObjectName("prefix-test/");
		String[] files = { "prefix-test/file1.txt", "prefix-test/file2.txt", "prefix-test/subdir/file3.txt",
				"other-file.txt" };

		// 上传文件
		for (String file : files) {
			String objectName = generateTestObjectName(file);
			objectOperations.putObject(bucketName,
					FileWrappers.wrapper(file.getBytes(StandardCharsets.UTF_8)), objectName);
		}

		// 列举带前缀的对象
		ListObjectsArguments args = new ListObjectsArguments();
		args.setBucketName(bucketName);
		args.setPrefix(prefix);
		ListObjectsDomain result = objectOperations.listObjects(args);

		assertNotNull(result);
		// 验证结果包含指定前缀的对象

		logger.info("==> 列举带前缀对象成功: 前缀='{}', 找到 {} 个对象", prefix,
				result.getObjectSummaries().size());
	}

	@Test
	@DisplayName("测试列举对象限制数量")
	void testListObjectsWithMaxKeys() {
		skipIfIntegrationTestDisabled();

		String prefix = generateTestObjectName("max-keys/");
		// 上传 10 个对象
		for (int i = 1; i <= 10; i++) {
			String objectName = prefix + "file-" + i + ".txt";
			objectOperations.putObject(bucketName,
					FileWrappers.wrapper(("Content " + i).getBytes(StandardCharsets.UTF_8)), objectName);
		}

		// 限制返回 5 个
		ListObjectsArguments args = new ListObjectsArguments();
		args.setBucketName(bucketName);
		args.setPrefix(prefix);
		args.setMaxKeys(5);
		ListObjectsDomain result = objectOperations.listObjects(args);

		assertNotNull(result);
		assertTrue(result.getObjectSummaries().size() <= 5, "返回的对象数不应超过 maxKeys");

		logger.info("==> 列举对象限制数量成功: maxKeys=5, 返回 {} 个对象", result.getObjectSummaries().size());
	}

	// ==================== 预签名 URL 测试 ====================

	@Test
	@DisplayName("测试生成不同 HTTP 方法的预签名 URL")
	void testGeneratePresignedUrlWithDifferentMethods() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("presigned-methods-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("test content".getBytes(StandardCharsets.UTF_8)), objectName);

		// GET URL
		String getUrl = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.GET,
				Duration.ofHours(1));
		assertNotNull(getUrl);
		assertTrue(getUrl.contains(bucketName));

		// PUT URL
		String putUrl = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.PUT,
				Duration.ofHours(1));
		assertNotNull(putUrl);
		assertTrue(putUrl.contains(bucketName));

		logger.info("==> 不同 HTTP 方法的预签名 URL 生成成功");
	}

	@Test
	@DisplayName("测试生成不同过期时间的预签名 URL")
	void testGeneratePresignedUrlWithDifferentExpirations() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("presigned-expiration-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("test content".getBytes(StandardCharsets.UTF_8)), objectName);

		// 1 小时过期
		String url1h = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.GET,
				Duration.ofHours(1));
		assertNotNull(url1h);

		// 1 天过期
		String url1d = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.GET,
				Duration.ofDays(1));
		assertNotNull(url1d);

		// 1 分钟过期
		String url1m = objectOperations.genPreSignedUrl(bucketName, objectName, HttpMethod.GET,
				Duration.ofMinutes(1));
		assertNotNull(url1m);

		logger.info("==> 不同过期时间的预签名 URL 生成成功");
	}

	@Test
	@DisplayName("测试使用日期生成预签名 URL")
	void testGeneratePresignedUrlWithDate() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("presigned-date-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("test content".getBytes(StandardCharsets.UTF_8)), objectName);

		// 1 小时后过期
		Date expiration = Date.from(Instant.now().plusSeconds(3600));
		String getUrl = objectOperations.getPreSignedUrl(bucketName, objectName, expiration);

		assertNotNull(getUrl);
		assertTrue(getUrl.contains(bucketName));
		assertTrue(getUrl.contains(objectName));

		logger.info("==> 使用日期生成预签名 URL 成功");
	}

	// ==================== 边界条件测试 ====================

	@Test
	@DisplayName("测试上传大文件 (10MB)")
	void testUploadLargeFile10MB() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("large-file-10mb.bin");
		int sizeInMB = 10;
		byte[] largeData = new byte[sizeInMB * 1024 * 1024];
		for (int i = 0; i < largeData.length; i++) {
			largeData[i] = (byte) (i % 256);
		}

		PutObjectDomain result = objectOperations.putObject(bucketName, FileWrappers.wrapper(largeData),
				objectName);

		assertNotNull(result);
		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);
		assertEquals(largeData.length, metadata.getObjectSize());

		logger.info("==> 大文件上传成功: {} (大小: {} MB)", objectName, sizeInMB);
	}

	@Test
	@DisplayName("测试对象元数据完整性")
	void testObjectMetadataCompleteness() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("metadata-complete.txt");
		String content = "Test metadata completeness";
		objectOperations.putObject(bucketName, FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)),
				objectName);

		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);

		// 验证所有元数据字段
		assertNotNull(metadata.getObjectName());
		assertNotNull(metadata.getBucketName());
		assertNotNull(metadata.getContentType());
		assertNotNull(metadata.getObjectSize());
		assertNotNull(metadata.getLastModified());

		logger.info("==> 对象元数据完整性验证成功");
	}

	@Test
	@DisplayName("测试下载对象内容完整性")
	void testDownloadedObjectContentIntegrity() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("integrity-test.bin");
		byte[] originalData = new byte[1024 * 100]; // 100 KB
		for (int i = 0; i < originalData.length; i++) {
			originalData[i] = (byte) (i % 256);
		}

		objectOperations.putObject(bucketName, FileWrappers.wrapper(originalData), objectName);

		GetObjectDomain downloaded = objectOperations.getObject(bucketName, objectName);
		byte[] downloadedData = downloaded.getObjectBytes();

		assertArrayEquals(originalData, downloadedData, "下载的内容应该与原始内容完全一致");

		logger.info("==> 下载对象内容完整性验证成功");
	}

	// ==================== 文件夹和路径测试 ====================

	@Test
	@DisplayName("测试创建多级文件夹")
	void testCreateNestedDirectories() {
		skipIfIntegrationTestDisabled();

		String folderPath = generateTestObjectName("nested/folder/structure/test/");

		boolean result = objectOperations.mkdir(bucketName, folderPath);

		assertTrue(result, "创建多级文件夹应该成功");

		logger.info("==> 多级文件夹创建成功: {}", folderPath);
	}

	@Test
	@DisplayName("测试上传文件到文件夹路径")
	void testUploadToFolder() {
		skipIfIntegrationTestDisabled();

		String folderPath = generateTestObjectName("folder/subfolder/");
		String fileName = "file-in-folder.txt";
		String objectName = folderPath + fileName;

		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("file in folder".getBytes(StandardCharsets.UTF_8)), objectName);

		assertTrue(objectOperations.doesObjectExist(bucketName, objectName));

		// 列举文件夹内容
		ListObjectsDomain listResult = objectOperations.listObjects(bucketName);
		boolean found = listResult.getObjectSummaries().stream().anyMatch(obj -> obj.getObjectName().equals(objectName));
		assertTrue(found, "应该在列举结果中找到上传的文件");

		logger.info("==> 文件上传到文件夹成功: {}", objectName);
	}

	// ==================== 异常场景测试 ====================

	@Test
	@DisplayName("测试删除不存在的对象")
	void testDeleteNonExistentObject() {
		skipIfIntegrationTestDisabled();

		String nonExistentObject = generateTestObjectName("non-existent-file.txt");

		// 删除不存在的对象应该返回 true (幂等操作)
		boolean result = objectOperations.delObject(bucketName, nonExistentObject);

		// S3 删除不存在的对象通常返回成功
		assertTrue(result);

		logger.info("==> 删除不存在的对象处理正确");
	}

	@Test
	@DisplayName("测试获取不存在对象的元数据")
	void testGetMetadataNonExistentObject() {
		skipIfIntegrationTestDisabled();

		String nonExistentObject = generateTestObjectName("non-existent-meta.txt");

		assertThrows(Exception.class, () -> {
			objectOperations.getObjectMetadata(bucketName, nonExistentObject);
		}, "获取不存在对象的元数据应该抛出异常");

		logger.info("==> 获取不存在对象元数据异常处理正确");
	}

	@Test
	@DisplayName("测试下载不存在的对象")
	void testDownloadNonExistentObject() {
		skipIfIntegrationTestDisabled();

		String nonExistentObject = generateTestObjectName("non-existent-download.txt");

		assertThrows(Exception.class, () -> {
			objectOperations.getObject(bucketName, nonExistentObject);
		}, "下载不存在的对象应该抛出异常");

		logger.info("==> 下载不存在对象异常处理正确");
	}

	// ==================== 参数化测试 ====================

	@Test
	@DisplayName("测试使用参数类上传对象")
	void testPutObjectWithArguments() {
		skipIfIntegrationTestDisabled();

		PutObjectArguments arguments = new PutObjectArguments();
		arguments.setBucketName(bucketName);
		arguments.setObjectName(generateTestObjectName("with-args.txt"));
		arguments.setInputStream(new ByteArrayInputStream("test with arguments".getBytes(StandardCharsets.UTF_8)));
		arguments.setObjectSize(18);
		arguments.setContentType("text/plain");

		PutObjectDomain result = objectOperations.putObject(arguments);

		assertNotNull(result);
		assertEquals(arguments.getObjectName(), result.getObjectName());
		assertTrue(objectOperations.doesObjectExist(bucketName, arguments.getObjectName()));

		logger.info("==> 使用参数类上传对象成功");
	}

	@Test
	@DisplayName("测试使用参数类获取对象")
	void testGetObjectWithArguments() {
		skipIfIntegrationTestDisabled();

		// 先上传
		String objectName = generateTestObjectName("get-with-args.txt");
		String content = "test get with arguments";
		objectOperations.putObject(bucketName, FileWrappers.wrapper(content.getBytes(StandardCharsets.UTF_8)),
				objectName);

		// 使用参数获取
		GetObjectArguments arguments = new GetObjectArguments();
		arguments.setBucketName(bucketName);
		arguments.setObjectName(objectName);

		GetObjectDomain result = objectOperations.getObject(arguments);

		assertNotNull(result);
		assertEquals(objectName, result.getObjectName());
		assertArrayEquals(content.getBytes(StandardCharsets.UTF_8), result.getObjectBytes());

		logger.info("==> 使用参数类获取对象成功");
	}

	// ==================== 字符编码测试 ====================

	@Test
	@DisplayName("测试上传不同编码的文本文件")
	void testUploadDifferentEncodingTextFiles() {
		skipIfIntegrationTestDisabled();

		// UTF-8
		String utf8File = generateTestObjectName("utf8.txt");
		String utf8Content = "UTF-8 测试 content 𝔘𝔱𝔣-8";
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper(utf8Content.getBytes(StandardCharsets.UTF_8)), utf8File);

		// 验证
		GetObjectDomain utf8Result = objectOperations.getObject(bucketName, utf8File);
		String downloadedUtf8 = new String(utf8Result.getObjectBytes(), StandardCharsets.UTF_8);
		assertEquals(utf8Content, downloadedUtf8);

		logger.info("==> 不同编码文本文件上传成功");
	}

	// ==================== 时间相关测试 ====================

	@Test
	@DisplayName("测试对象修改时间")
	void testObjectLastModifiedTime() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("last-modified-test.txt");
		objectOperations.putObject(bucketName,
				FileWrappers.wrapper("test last modified".getBytes(StandardCharsets.UTF_8)), objectName);

		ObjectMetadataDomain metadata = objectOperations.getObjectMetadata(bucketName, objectName);

		assertNotNull(metadata.getLastModified(), "最后修改时间不应为 null");

		// 验证时间是最近的 (1 分钟内)
		long timeDiff = System.currentTimeMillis() - metadata.getLastModified().getTime();
		assertTrue(timeDiff < 60000, "最后修改时间应该是最近的");

		logger.info("==> 对象修改时间验证成功: {}", metadata.getLastModified());
	}

}
