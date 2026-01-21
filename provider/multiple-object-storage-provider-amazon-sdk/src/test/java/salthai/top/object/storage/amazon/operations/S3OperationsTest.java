package salthai.top.object.storage.amazon.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salthai.top.object.storage.amazon.BaseS3Operations;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.S3Constants;
import salthai.top.object.storage.core.operations.ObjectOperations;
import salthai.top.object.storage.core.operations.BucketOperations;
import salthai.top.object.storage.core.operations.ObjectMultipartOperations;
import salthai.top.object.storage.core.provider.DefaultProviderClientSingletonManager;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * S3 操作类详细测试
 * <p>
 * 测试 S3 操作类的创建、继承关系和接口实现
 * </p>
 *
 * @author Kuang HaiBo 2024/1/20 17:40
 */
@DisplayName("S3 操作类测试")
class S3OperationsTest {

	private ProviderClientManager<S3ClientPackage> clientManager;

	private static final String TEST_FACTORY_NAME = "测试工厂";

	@BeforeEach
	void setUp() {
		// 创建模拟工厂用于测试
		ProviderClientFactory<S3ClientPackage> mockFactory = createMockFactory();
		clientManager = new DefaultProviderClientSingletonManager<>(mockFactory);
	}

	/**
	 * 创建模拟工厂（仅用于测试类结构）
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
	@DisplayName("测试 S3ObjectOperations 创建")
	void testS3ObjectOperationsCreation() {
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);

		assertNotNull(objectOperations, "S3ObjectOperations 实例不应为 null");
		assertNotNull(objectOperations.getClientManager(), "客户端管理器不应为 null");
	}

	@Test
	@DisplayName("测试 S3BucketOperations 创建")
	void testS3BucketOperationsCreation() {
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);

		assertNotNull(bucketOperations, "S3BucketOperations 实例不应为 null");
		assertNotNull(bucketOperations.getClientManager(), "客户端管理器不应为 null");
	}

	@Test
	@DisplayName("测试 S3ObjectMultipartOperations 创建")
	void testS3ObjectMultipartOperationsCreation() {
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		assertNotNull(multipartOperations, "S3ObjectMultipartOperations 实例不应为 null");
		assertNotNull(multipartOperations.getClientManager(), "客户端管理器不应为 null");
	}

	@Test
	@DisplayName("测试操作类继承 BaseS3Operations")
	void testOperationsInheritBaseS3Operations() {
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		assertInstanceOf(BaseS3Operations.class, objectOperations, "S3ObjectOperations 应该继承 BaseS3Operations");
		assertInstanceOf(BaseS3Operations.class, bucketOperations, "S3BucketOperations 应该继承 BaseS3Operations");
		assertInstanceOf(BaseS3Operations.class, multipartOperations,
				"S3ObjectMultipartOperations 应该继承 BaseS3Operations");
	}

	@Test
	@DisplayName("测试 S3ObjectOperations 实现 ObjectOperations 接口")
	void testS3ObjectOperationsImplementsInterface() {
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);

		assertInstanceOf(ObjectOperations.class, objectOperations,
				"S3ObjectOperations 应该实现 ObjectOperations 接口");
	}

	@Test
	@DisplayName("测试 S3BucketOperations 实现 BucketOperations 接口")
	void testS3BucketOperationsImplementsInterface() {
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);

		assertInstanceOf(BucketOperations.class, bucketOperations,
				"S3BucketOperations 应该实现 BucketOperations 接口");
	}

	@Test
	@DisplayName("测试 S3ObjectMultipartOperations 实现 ObjectMultipartOperations 接口")
	void testS3ObjectMultipartOperationsImplementsInterface() {
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		assertInstanceOf(ObjectMultipartOperations.class, multipartOperations,
				"S3ObjectMultipartOperations 应该实现 ObjectMultipartOperations 接口");
	}

	@Test
	@DisplayName("测试操作类使用相同的客户端管理器")
	void testOperationsUseSameClientManager() {
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		assertSame(clientManager, objectOperations.getClientManager(),
				"S3ObjectOperations 应该使用传入的客户端管理器");
		assertSame(clientManager, bucketOperations.getClientManager(),
				"S3BucketOperations 应该使用传入的客户端管理器");
		assertSame(clientManager, multipartOperations.getClientManager(),
				"S3ObjectMultipartOperations 应该使用传入的客户端管理器");
	}

	@Test
	@DisplayName("测试创建多个操作类实例")
	void testCreateMultipleOperationsInstances() {
		S3ObjectOperations ops1 = new S3ObjectOperations(clientManager);
		S3ObjectOperations ops2 = new S3ObjectOperations(clientManager);

		assertNotNull(ops1);
		assertNotNull(ops2);
		assertNotSame(ops1, ops2, "不同的操作类实例应该是不同的对象");
	}

	@Test
	@DisplayName("测试操作类不为 null")
	void testOperationsNotNull() {
		S3ObjectOperations objectOperations = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOperations = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOperations = new S3ObjectMultipartOperations(clientManager);

		assertNotNull(objectOperations, "S3ObjectOperations 不应为 null");
		assertNotNull(bucketOperations, "S3BucketOperations 不应为 null");
		assertNotNull(multipartOperations, "S3ObjectMultipartOperations 不应为 null");
	}

	@Test
	@DisplayName("测试操作类可以正常创建且不抛出异常")
	void testOperationsCreationDoesNotThrow() {
		assertDoesNotThrow(() -> new S3ObjectOperations(clientManager),
				"创建 S3ObjectOperations 不应抛出异常");
		assertDoesNotThrow(() -> new S3BucketOperations(clientManager),
				"创建 S3BucketOperations 不应抛出异常");
		assertDoesNotThrow(() -> new S3ObjectMultipartOperations(clientManager),
				"创建 S3ObjectMultipartOperations 不应抛出异常");
	}

	@Test
	@DisplayName("测试 S3 日志常量存在")
	void testS3LogPrefixConstant() {
		assertNotNull(S3Constants.LOG_PREFIX, "S3 日志前缀常量不应为 null");
		assertFalse(S3Constants.LOG_PREFIX.isEmpty(), "S3 日志前缀不应为空字符串");
	}

	@Test
	@DisplayName("测试使用不同的客户端管理器创建操作类")
	void testOperationsWithDifferentClientManagers() {
		ProviderClientFactory<S3ClientPackage> factory1 = createMockFactory();
		ProviderClientFactory<S3ClientPackage> factory2 = createMockFactory();

		ProviderClientManager<S3ClientPackage> manager1 = new DefaultProviderClientSingletonManager<>(factory1);
		ProviderClientManager<S3ClientPackage> manager2 = new DefaultProviderClientSingletonManager<>(factory2);

		S3ObjectOperations ops1 = new S3ObjectOperations(manager1);
		S3ObjectOperations ops2 = new S3ObjectOperations(manager2);

		assertNotSame(ops1.getClientManager(), ops2.getClientManager(),
				"不同的操作类应该使用不同的客户端管理器");
	}

	@Test
	@DisplayName("测试操作类的类型检查")
	void testOperationsTypeChecking() {
		S3ObjectOperations objectOps = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOps = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOps = new S3ObjectMultipartOperations(clientManager);

		// 检查类名
		assertEquals("S3ObjectOperations", objectOps.getClass().getSimpleName());
		assertEquals("S3BucketOperations", bucketOps.getClass().getSimpleName());
		assertEquals("S3ObjectMultipartOperations", multipartOps.getClass().getSimpleName());

		// 检查包名
		assertTrue(objectOps.getClass().getPackageName().contains("amazon"));
		assertTrue(bucketOps.getClass().getPackageName().contains("amazon"));
		assertTrue(multipartOps.getClass().getPackageName().contains("amazon"));
	}

	@Test
	@DisplayName("测试操作类的继承层次结构")
	void testOperationsInheritanceHierarchy() {
		S3ObjectOperations objectOps = new S3ObjectOperations(clientManager);

		// 检查继承链
		assertTrue(BaseS3Operations.class.isAssignableFrom(objectOps.getClass()));
		assertTrue(salthai.top.object.storage.core.operations.BaseOperations.class.isAssignableFrom(objectOps.getClass()));
	}

	@Test
	@DisplayName("测试同时创建所有类型的操作类")
	void testCreateAllOperationTypesSimultaneously() {
		S3ObjectOperations objectOps = new S3ObjectOperations(clientManager);
		S3BucketOperations bucketOps = new S3BucketOperations(clientManager);
		S3ObjectMultipartOperations multipartOps = new S3ObjectMultipartOperations(clientManager);

		// 验证所有操作类都正确创建
		assertNotNull(objectOps);
		assertNotNull(bucketOps);
		assertNotNull(multipartOps);

		// 验证所有操作类都使用同一个客户端管理器
		assertEquals(clientManager, objectOps.getClientManager());
		assertEquals(clientManager, bucketOps.getClientManager());
		assertEquals(clientManager, multipartOps.getClientManager());
	}

}
