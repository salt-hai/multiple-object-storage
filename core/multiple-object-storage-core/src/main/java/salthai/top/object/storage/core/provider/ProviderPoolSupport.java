package salthai.top.object.storage.core.provider;

import salthai.top.object.storage.core.provider.factory.ProviderClientFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 用于创建对象池的工具
 *
 * @author Kuang HaiBo 2024/5/23 17:15
 */
public abstract class ProviderPoolSupport {

	public ProviderPoolSupport() {
	}

	/**
	 * 通过创建客户端的工厂实例化一个可被池化的对象工厂
	 *
	 * @param <T>
	 */
	private static class ProviderPooledObjectFactory<T> extends BasePooledObjectFactory<T> {

		/**
		 * 创建客户端的工厂
		 */
		private final ProviderClientFactory<T> instanceFactory;

		public ProviderPooledObjectFactory(ProviderClientFactory<T> instanceFactory) {
			this.instanceFactory = instanceFactory;
		}

		/**
		 * Creates an object instance, to be wrapped in a {@link PooledObject}.
		 * <p>
		 * This method <strong>must</strong> support concurrent, multi-threaded
		 * activation.
		 * </p>
		 * @return an instance to be served by the pool
		 * @throws Exception if there is a problem creating a new instance, this will be
		 * propagated to the code requesting an object.
		 */
		@Override
		public T create() throws Exception {
			return instanceFactory.create();
		}

		/**
		 * Wraps the provided instance with an implementation of {@link PooledObject}.
		 * @param obj the instance to wrap
		 * @return The provided instance, wrapped by a {@link PooledObject}
		 */
		@Override
		public PooledObject<T> wrap(T obj) {
			return new DefaultPooledObject<>(obj);
		}

		/**
		 * 销毁对象
		 * @param p ignored
		 */
		@Override
		public void destroyObject(PooledObject<T> p) throws Exception {
			instanceFactory.destroy(p.getObject());
			super.destroyObject(p);
		}

	}

	/**
	 * 通过实例工厂包装一个通用对象池
	 * @param instanceFactory 创建连接的工厂,创建的都是 相应的实例对象
	 * @param poolConfig 池配置
	 * @param <T> 链接 对象
	 * @return 通用对象池
	 */
	public static <T> GenericObjectPool<T> createGenericObjectPool(ProviderClientFactory<T> instanceFactory,
			GenericObjectPoolConfig<T> poolConfig) {
		return new GenericObjectPool<>(new ProviderPooledObjectFactory<>(instanceFactory), poolConfig);
	}

}
