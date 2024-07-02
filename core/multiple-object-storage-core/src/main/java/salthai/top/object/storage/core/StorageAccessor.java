package salthai.top.object.storage.core;

import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.ProviderClientPoolingManager;

import java.util.Objects;

/**
 * 存储访问基础类,用于继承
 *
 * @param <T> 表示不同的客户端
 * @author Kuang HaiBo 2024/7/2 14:11
 */
public abstract class StorageAccessor<T> {

	/**
	 * 管理器 not null
	 */
	private ProviderClientManager<T> providerClientManager;

	/**
	 * 池化管理的管理器
	 */
	private ProviderClientPoolingManager<T> providerClientPoolingManager;

	/**
	 * 使用对象池
	 */
	private boolean usePool = false;

	/**
	 * empty
	 */
	public StorageAccessor() {
	}

	/**
	 * 使用客户端管理器进行构建
	 * @param providerClientManager 客户端管理器,该管理器不能为空
	 */
	public StorageAccessor(ProviderClientManager<T> providerClientManager) {
		setProviderClientManager(providerClientManager);
	}

	/**
	 * 设置一个池化 客户端管理器
	 * @param providerClientPoolingManager 池化客户端管理器
	 */
	public void setProviderClientPoolingManager(ProviderClientPoolingManager<T> providerClientPoolingManager) {
		Objects.requireNonNull(providerClientPoolingManager, "ProviderClientPoolingManager cant be null");
		this.providerClientPoolingManager = providerClientPoolingManager;
		this.providerClientManager = providerClientPoolingManager;
		this.usePool = true;
	}

	/**
	 * 设置管理器
	 * @param providerClientManager 管理器对象
	 */
	public void setProviderClientManager(ProviderClientManager<T> providerClientManager) {
		Objects.requireNonNull(providerClientManager, "ProviderClientManager cant be null");
		this.providerClientManager = providerClientManager;
		if (providerClientManager instanceof ProviderClientPoolingManager) {
			usePool = true;
			providerClientPoolingManager = (ProviderClientPoolingManager<T>) providerClientManager;
		}
	}

	/**
	 * 获取必要的客户端
	 * @return 客户端
	 */
	public T getRequiredClinet() {
		T client = getRequiredProviderClientManager().getClient();
		Objects.requireNonNull(client, "client is null");
		return client;
	}

	/**
	 * 使用了对象池
	 * @return true: 是
	 */
	public boolean isUsePool() {
		return usePool;
	}

	/**
	 * 获取必要的供应商客户端对象管理器
	 * @return 管理器
	 */
	public ProviderClientManager<T> getRequiredProviderClientManager() {
		Objects.requireNonNull(providerClientManager, "ProviderClientManager is null, maybe you can set up it");
		return providerClientManager;
	}

	/**
	 * 获取必要的供应商客户端对象池化管理器
	 * @return 管理器
	 */
	public ProviderClientPoolingManager<T> getRequiredProviderClientPoolingManager() {
		Objects.requireNonNull(providerClientPoolingManager,
				"ProviderClientPoolingManager is null,maybe you can set up it");
		return providerClientPoolingManager;
	}

}
