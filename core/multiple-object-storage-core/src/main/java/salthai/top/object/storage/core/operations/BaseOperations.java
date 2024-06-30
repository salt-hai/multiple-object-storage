package salthai.top.object.storage.core.operations;

import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.provider.ProviderClientPoolingManager;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 对象存储 操作 抽象定义
 *
 * @author Kuang HaiBo 2023/10/27 10:37
 */
public abstract class BaseOperations<T> {

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

	public BaseOperations() {
	}

	public BaseOperations(ProviderClientManager<T> providerClientManager) {
		setProviderClientManager(providerClientManager);
	}

	/**
	 * 设置一个池化 客户端管理器
	 * @param providerClientPoolingManager 池化客户端管理器
	 */
	public void setProviderClientPoolingManager(ProviderClientPoolingManager<T> providerClientPoolingManager) {
		Objects.requireNonNull(providerClientPoolingManager);
		this.providerClientPoolingManager = providerClientPoolingManager;
		this.providerClientManager = providerClientPoolingManager;
		this.usePool = true;
	}

	/**
	 * 设置管理器
	 * @param providerClientManager 管理器对象
	 */
	public void setProviderClientManager(ProviderClientManager<T> providerClientManager) {
		Objects.requireNonNull(providerClientManager);
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
	private T getRequiredClinet() {
		T client = getRequiredProviderClientManager().getClient();
		Objects.requireNonNull(client, "client is null");
		return client;
	}

	/**
	 * 获取必要的供应商客户端对象管理器
	 * @return 管理器
	 */
	private ProviderClientManager<T> getRequiredProviderClientManager() {
		Objects.requireNonNull(providerClientManager, "ProviderClientManager is null, maybe you can set up it");
		return providerClientManager;
	}

	/**
	 * 获取必要的供应商客户端对象池化管理器
	 * @return 管理器
	 */
	private ProviderClientPoolingManager<T> getRequiredProviderClientPoolingManager() {
		Objects.requireNonNull(providerClientPoolingManager, "ProviderClientPoolingManager is null");
		return providerClientPoolingManager;
	}

	/**
	 * 执行对象存储操作
	 * @param action 业务操作函数，如上传 获取 生成签名url
	 * @param <Result> 返回值响应
	 * @return 自定义的返回值
	 */
	public <Result> Result execute(Function<T, Result> action) {
		Objects.requireNonNull(action, "execute action not be null");
		T clinet = getRequiredClinet();
		try {
			return action.apply(clinet);
		}
		finally {
			if (usePool) {
				// 回收对象
				getRequiredProviderClientPoolingManager().returnClient(clinet);
			}
		}
	}

	/**
	 * 执行对象存储操作
	 * @param action 业务操作函数，如上传 获取 生成签名url
	 */
	public void execute(Consumer<T> action) {
		Objects.requireNonNull(action, "execute action not be null");
		T clinet = getRequiredClinet();
		try {
			action.accept(clinet);
		}
		finally {
			if (usePool) {
				getRequiredProviderClientPoolingManager().returnClient(clinet);
			}
		}
	}

}
