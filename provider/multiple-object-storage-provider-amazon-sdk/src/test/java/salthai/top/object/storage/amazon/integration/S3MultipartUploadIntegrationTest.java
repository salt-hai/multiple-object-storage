package salthai.top.object.storage.amazon.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.client.S3ClientPackageFactory;
import salthai.top.object.storage.amazon.config.S3Properties;
import salthai.top.object.storage.amazon.operations.S3ObjectMultipartOperations;
import salthai.top.object.storage.core.arguments.multipart.*;
import salthai.top.object.storage.core.domain.multipart.*;
import salthai.top.object.storage.core.operations.ObjectMultipartOperations;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AWS S3 分片上传集成测试
 * <p>
 * 测试大文件分片上传的完整流程
 * </p>
 *
 * @author Kuang HaiBo 2024/1/21 15:30
 */
@DisplayName("AWS S3 分片上传集成测试")
@Testcontainers(disabledWithoutDocker = true)
class S3MultipartUploadIntegrationTest extends AbstractIntegrationTest {

	@Container
	static GenericContainer<?> localstack = new GenericContainer<>(
			DockerImageName.parse("localstack/localstack:3.0.2")).withExposedPorts(4566).withEnv("SERVICES", "s3")
			.withEnv("AWS_DEFAULT_REGION", "us-east-1").waitingFor(Wait.forLogMessage(".*Ready.*", 1));

	private ObjectMultipartOperations multipartOperations;

	private ProviderClientManager<S3ClientPackage> clientManager;

	private S3ClientPackage s3Client;

	@Override
	protected String getBucketNamePropertyKey() {
		return "s3.bucket.name";
	}

	@Override
	protected ObjectMultipartOperations getObjectOperations() {
		// 不使用,返回 null
		return null;
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
			multipartOperations = new S3ObjectMultipartOperations(clientManager);
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

	// ==================== 分片上传测试 ====================

	@Test
	@DisplayName("测试完整的分片上传流程")
	void testCompleteMultipartUpload() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("multipart-upload.txt");
		int partSize = 5 * 1024 * 1024; // 5MB per part

		// 1. 初始化分片上传
		InitiateMultipartUploadArguments initArgs = new InitiateMultipartUploadArguments();
		initArgs.setBucketName(bucketName);
		initArgs.setObjectName(objectName);
		InitiateMultipartUploadDomain initResult = multipartOperations.initiateMultipartUpload(initArgs);

		assertNotNull(initResult, "初始化结果不应为 null");
		assertNotNull(initResult.getUploadId(), "UploadId 不应为 null");
		String uploadId = initResult.getUploadId();

		logger.info("==> 分片上传初始化成功: uploadId={}", uploadId);

		// 2. 上传 3 个分片
		List<PartSummaryDomain> parts = new ArrayList<>();
		for (int partNumber = 1; partNumber <= 3; partNumber++) {
			byte[] partData = createPartData(partSize, partNumber);

			UploadPartArguments uploadArgs = new UploadPartArguments();
			uploadArgs.setBucketName(bucketName);
			uploadArgs.setObjectName(objectName);
			uploadArgs.setUploadId(uploadId);
			uploadArgs.setPartNumber(partNumber);
			uploadArgs.setPartSize(partSize);
			uploadArgs.setInputStream(new ByteArrayInputStream(partData));

			UploadPartDomain uploadResult = multipartOperations.uploadPart(uploadArgs);

			assertNotNull(uploadResult, "分片 " + partNumber + " 上传结果不应为 null");
			assertEquals(partNumber, uploadResult.getPartNumber(), "分片号应该匹配");

			// 创建分片摘要
			PartSummaryDomain part = new PartSummaryDomain();
			part.setPartNumber(partNumber);
			part.setETag(uploadResult.getETag());
			parts.add(part);

			logger.info("==> 分片 {} 上传成功: ETag={}", partNumber, uploadResult.getETag());
		}

		// 3. 完成分片上传
		CompleteMultipartUploadArguments completeArgs = new CompleteMultipartUploadArguments();
		completeArgs.setBucketName(bucketName);
		completeArgs.setObjectName(objectName);
		completeArgs.setUploadId(uploadId);
		completeArgs.setParts(parts);

		CompleteMultipartUploadDomain completeResult = multipartOperations.completeMultipartUpload(completeArgs);

		assertNotNull(completeResult, "完成结果不应为 null");
		assertEquals(objectName, completeResult.getObjectName(), "对象名称应该匹配");
		assertEquals(bucketName, completeResult.getBucketName(), "桶名称应该匹配");

		logger.info("==> 分片上传完成成功: location={}", completeResult.getLocation());
	}

	@Test
	@DisplayName("测试中止分片上传")
	void testAbortMultipartUpload() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("abort-multipart.txt");

		// 1. 初始化分片上传
		InitiateMultipartUploadArguments initArgs = new InitiateMultipartUploadArguments();
		initArgs.setBucketName(bucketName);
		initArgs.setObjectName(objectName);
		InitiateMultipartUploadDomain initResult = multipartOperations.initiateMultipartUpload(initArgs);

		String uploadId = initResult.getUploadId();

		// 2. 上传一个分片
		UploadPartArguments uploadArgs = new UploadPartArguments();
		uploadArgs.setBucketName(bucketName);
		uploadArgs.setObjectName(objectName);
		uploadArgs.setUploadId(uploadId);
		uploadArgs.setPartNumber(1);
		uploadArgs.setPartSize(1024 * 1024);
		uploadArgs.setInputStream(new ByteArrayInputStream(createPartData(1024 * 1024, 1)));

		multipartOperations.uploadPart(uploadArgs);

		// 3. 中止分片上传
		AbortMultipartUploadArguments abortArgs = new AbortMultipartUploadArguments();
		abortArgs.setBucketName(bucketName);
		abortArgs.setObjectName(objectName);
		abortArgs.setUploadId(uploadId);

		AbortMultipartUploadDomain abortResult = multipartOperations.abortMultipartUpload(abortArgs);

		assertNotNull(abortResult, "中止结果不应为 null");

		logger.info("==> 分片上传中止成功: uploadId={}", uploadId);
	}

	@Test
	@DisplayName("测试列举分片")
	void testListParts() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("list-parts.txt");

		// 1. 初始化分片上传
		InitiateMultipartUploadArguments initArgs = new InitiateMultipartUploadArguments();
		initArgs.setBucketName(bucketName);
		initArgs.setObjectName(objectName);
		InitiateMultipartUploadDomain initResult = multipartOperations.initiateMultipartUpload(initArgs);

		String uploadId = initResult.getUploadId();

		// 2. 上传 2 个分片
		for (int partNumber = 1; partNumber <= 2; partNumber++) {
			UploadPartArguments uploadArgs = new UploadPartArguments();
			uploadArgs.setBucketName(bucketName);
			uploadArgs.setObjectName(objectName);
			uploadArgs.setUploadId(uploadId);
			uploadArgs.setPartNumber(partNumber);
			uploadArgs.setPartSize(1024 * 1024);
			uploadArgs.setInputStream(new ByteArrayInputStream(createPartData(1024 * 1024, partNumber)));

			multipartOperations.uploadPart(uploadArgs);
		}

		// 3. 列举分片
		ListPartsArguments listArgs = new ListPartsArguments();
		listArgs.setBucketName(bucketName);
		listArgs.setObjectName(objectName);
		listArgs.setUploadId(uploadId);

		ListPartsDomain listResult = multipartOperations.listParts(listArgs);

		assertNotNull(listResult, "列举结果不应为 null");
		assertEquals(2, listResult.getParts().size(), "应该找到 2 个分片");
		assertEquals(uploadId, listResult.getUploadId(), "UploadId 应该匹配");

		logger.info("==> 列举分片成功: 找到 {} 个分片", listResult.getParts().size());

		// 4. 清理: 中止上传
		AbortMultipartUploadArguments abortArgs = new AbortMultipartUploadArguments();
		abortArgs.setBucketName(bucketName);
		abortArgs.setObjectName(objectName);
		abortArgs.setUploadId(uploadId);
		multipartOperations.abortMultipartUpload(abortArgs);
	}

	@Test
	@DisplayName("测试列举进行中的分片上传")
	void testListMultipartUploads() {
		skipIfIntegrationTestDisabled();

		// 1. 初始化 2 个分片上传
		String object1 = generateTestObjectName("multipart-1.txt");
		String object2 = generateTestObjectName("multipart-2.txt");

		InitiateMultipartUploadArguments initArgs1 = new InitiateMultipartUploadArguments();
		initArgs1.setBucketName(bucketName);
		initArgs1.setObjectName(object1);
		InitiateMultipartUploadDomain result1 = multipartOperations.initiateMultipartUpload(initArgs1);

		InitiateMultipartUploadArguments initArgs2 = new InitiateMultipartUploadArguments();
		initArgs2.setBucketName(bucketName);
		initArgs2.setObjectName(object2);
		InitiateMultipartUploadDomain result2 = multipartOperations.initiateMultipartUpload(initArgs2);

		// 2. 列举进行中的分片上传
		ListMultipartUploadsArguments listArgs = new ListMultipartUploadsArguments();
		listArgs.setBucketName(bucketName);

		ListMultipartUploadsDomain listResult = multipartOperations.listMultipartUploads(listArgs);

		assertNotNull(listResult, "列举结果不应为 null");
		assertNotNull(listResult.getMultipartUploads(), "分片上传列表不应为 null");

		logger.info("==> 列举进行中的分片上传成功: 找到 {} 个上传", listResult.getMultipartUploads().size());

		// 3. 清理
		AbortMultipartUploadArguments abortArgs1 = new AbortMultipartUploadArguments();
		abortArgs1.setBucketName(bucketName);
		abortArgs1.setObjectName(object1);
		abortArgs1.setUploadId(result1.getUploadId());
		multipartOperations.abortMultipartUpload(abortArgs1);

		AbortMultipartUploadArguments abortArgs2 = new AbortMultipartUploadArguments();
		abortArgs2.setBucketName(bucketName);
		abortArgs2.setObjectName(object2);
		abortArgs2.setUploadId(result2.getUploadId());
		multipartOperations.abortMultipartUpload(abortArgs2);
	}

	@Test
	@DisplayName("测试小文件分片上传 (多个小分片)")
	void testMultipartUploadWithSmallParts() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("small-parts.txt");
		int partSize = 100 * 1024; // 100KB per part

		// 初始化
		InitiateMultipartUploadArguments initArgs = new InitiateMultipartUploadArguments();
		initArgs.setBucketName(bucketName);
		initArgs.setObjectName(objectName);
		InitiateMultipartUploadDomain initResult = multipartOperations.initiateMultipartUpload(initArgs);

		String uploadId = initResult.getUploadId();

		// 上传 5 个小分片
		List<PartSummaryDomain> parts = new ArrayList<>();
		for (int partNumber = 1; partNumber <= 5; partNumber++) {
			UploadPartArguments uploadArgs = new UploadPartArguments();
			uploadArgs.setBucketName(bucketName);
			uploadArgs.setObjectName(objectName);
			uploadArgs.setUploadId(uploadId);
			uploadArgs.setPartNumber(partNumber);
			uploadArgs.setPartSize(partSize);
			uploadArgs.setInputStream(new ByteArrayInputStream(createPartData(partSize, partNumber)));

			UploadPartDomain uploadResult = multipartOperations.uploadPart(uploadArgs);

			PartSummaryDomain part = new PartSummaryDomain();
			part.setPartNumber(partNumber);
			part.setETag(uploadResult.getETag());
			parts.add(part);
		}

		// 完成
		CompleteMultipartUploadArguments completeArgs = new CompleteMultipartUploadArguments();
		completeArgs.setBucketName(bucketName);
		completeArgs.setObjectName(objectName);
		completeArgs.setUploadId(uploadId);
		completeArgs.setParts(parts);

		CompleteMultipartUploadDomain completeResult = multipartOperations.completeMultipartUpload(completeArgs);

		assertNotNull(completeResult);
		assertEquals(objectName, completeResult.getObjectName());

		logger.info("==> 小文件分片上传成功: 5 个分片, 每个 {} KB", partSize / 1024);
	}

	@Test
	@DisplayName("测试分片上传指定内容类型")
	void testMultipartUploadWithContentType() {
		skipIfIntegrationTestDisabled();

		String objectName = generateTestObjectName("multipart-type.json");

		// 初始化时指定内容类型
		InitiateMultipartUploadArguments initArgs = new InitiateMultipartUploadArguments();
		initArgs.setBucketName(bucketName);
		initArgs.setObjectName(objectName);
		initArgs.setContentType("application/json");

		InitiateMultipartUploadDomain initResult = multipartOperations.initiateMultipartUpload(initArgs);

		String uploadId = initResult.getUploadId();

		// 上传分片
		List<PartSummaryDomain> parts = new ArrayList<>();
		int partSize = 1024 * 1024;

		for (int partNumber = 1; partNumber <= 2; partNumber++) {
			UploadPartArguments uploadArgs = new UploadPartArguments();
			uploadArgs.setBucketName(bucketName);
			uploadArgs.setObjectName(objectName);
			uploadArgs.setUploadId(uploadId);
			uploadArgs.setPartNumber(partNumber);
			uploadArgs.setPartSize(partSize);
			uploadArgs.setInputStream(new ByteArrayInputStream(createPartData(partSize, partNumber)));

			UploadPartDomain uploadResult = multipartOperations.uploadPart(uploadArgs);

			PartSummaryDomain part = new PartSummaryDomain();
			part.setPartNumber(partNumber);
			part.setETag(uploadResult.getETag());
			parts.add(part);
		}

		// 完成
		CompleteMultipartUploadArguments completeArgs = new CompleteMultipartUploadArguments();
		completeArgs.setBucketName(bucketName);
		completeArgs.setObjectName(objectName);
		completeArgs.setUploadId(uploadId);
		completeArgs.setParts(parts);

		multipartOperations.completeMultipartUpload(completeArgs);

		logger.info("==> 分片上传指定内容类型成功");
	}

	// ==================== 辅助方法 ====================

	/**
	 * 创建分片数据
	 */
	private byte[] createPartData(int size, int partNumber) {
		byte[] data = new byte[size];
		String content = "Part " + partNumber + " - ";
		byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

		// 填充内容
		for (int i = 0; i < data.length; i++) {
			if (i < contentBytes.length) {
				data[i] = contentBytes[i];
			}
			else {
				data[i] = (byte) (i % 256);
			}
		}
		return data;
	}

}
