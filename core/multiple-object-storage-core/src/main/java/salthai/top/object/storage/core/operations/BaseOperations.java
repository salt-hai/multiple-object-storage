package salthai.top.object.storage.core.operations;

import salthai.top.object.storage.core.StorageTemplate;
import salthai.top.object.storage.core.provider.ProviderClientManager;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 对象存储 操作 抽象定义
 *
 * @param <T> 表示不同的客户端
 * @author Kuang HaiBo 2023/10/27 10:37
 */
public abstract class BaseOperations<T> {

	/**
	 * 用于存储操作的模板
	 */
	private final StorageTemplate<T> storageTemplate;

	/**
	 * 使用客户端管理器进行构建
	 * @param providerClientManager 客户端管理器,该管理器不能为空
	 */
	public BaseOperations(ProviderClientManager<T> providerClientManager) {
		this.storageTemplate = new StorageTemplate<>(providerClientManager);
	}

	/**
	 * 直接使用 模板构建
	 * @param storageTemplate 相应模板
	 */
	public BaseOperations(StorageTemplate<T> storageTemplate) {
		this.storageTemplate = storageTemplate;
	}

	/**
	 * 执行对象存储操作
	 * @param action 业务操作函数，如上传 获取 生成签名url
	 * @param <Result> 返回值响应
	 * @return 自定义的返回值
	 */
	public <Result> Result execute(Function<T, Result> action) {
		return storageTemplate.execute(action);
	}

	/**
	 * 执行对象存储操作
	 * @param action 业务操作函数，如上传 获取 生成签名url
	 */
	public void execute(Consumer<T> action) {
		storageTemplate.execute(action);
	}

}
