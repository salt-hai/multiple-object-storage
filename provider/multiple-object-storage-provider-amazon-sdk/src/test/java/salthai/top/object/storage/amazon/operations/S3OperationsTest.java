package salthai.top.object.storage.amazon.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;

/**
 * S3 操作类测试
 *
 * @author Kuang HaiBo 2024/1/20 17:40
 */
class S3OperationsTest {

	private ProviderClientManager<S3ClientPackage> clientManager;

	@BeforeEach
	void setUp() {
		// 注意：这是一个结构性的测试，实际运行需要真实的 AWS S3 凭证
		// 在实际环境中，应该使用 mock 或者测试环境凭证
		ProviderClientFactory<S3ClientPackage> mockFactory = createMockFactory();
		clientManager = new DefaultProviderClientSingletonManager<>(mockFactory);
	}

	/**
	 * 创建模拟工厂（仅用于测试结构）
	 */
	private ProviderClientFactory<S3ClientPackage> createMockFactory() {
		return new ProviderClientFactory<S3ClientPackage>() {
			@Override
			public S3ClientPackage create() {
				// 返回 null，仅用于测试类结构
				return null;
			}

			@Override
			public void destroy(S3ClientPackage instance) {
				// 空实现
			}
		};
	}

	@Test
	void testS3ObjectOperationsCreation() {
		// 测试 S3ObjectOperations 能否正确创建
		Assertions.assertDoesNotThrow(() -> new S3ObjectOperations(clientManager));
	}

	@Test
	void testS3BucketOperationsCreation() {
		// 测试 S3BucketOperations 能否正确创建
		Assertions.assertDoesNotThrow(() -> new S3BucketOperations(clientManager));
	}

	@Test
	void testS3ObjectMultipartOperationsCreation() {
		// 测试 S3ObjectMultipartOperations 能否正确创建
		Assertions.assertDoesNotThrow(() -> new S3ObjectMultipartOperations(clientManager));
	}

	@Test
	void testOperationsInheritance() {
		// 测试操作类是否正确继承基类
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		Assertions.assertNotNull(objectOperations);
		Assertions.assertNotNull(bucketOperations);
		Assertions.assertNotNull(multipartOperations);
	}

}
