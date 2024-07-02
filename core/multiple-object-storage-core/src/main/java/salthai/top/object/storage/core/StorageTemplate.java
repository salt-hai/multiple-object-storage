package salthai.top.object.storage.core;

import salthai.top.object.storage.core.provider.ProviderClientManager;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 存储操作模板, 严格意义来说直接使用该类会破坏项目的封装性,导致不能快速切换存储服务
 * <p>
 * 通过调用{@link #execute(Consumer)} {@link #execute(Function)} 来操作对象存储系统. *
 * 调用方无需关心对象存储系统客户端的生命周期 *
 * ,也可以用于执行一些在{@link salthai.top.object.storage.core.operations.BucketOperation}等类中没有的方法
 * </p>
 *
 * @param <T> 表示不同的客户端
 * @author Kuang HaiBo 2024/7/2 14:12
 */
public class StorageTemplate<T> extends StorageAccessor<T> {

	/**
	 * 使用客户端管理器进行构建
	 * @param providerClientManager 客户端管理器,该管理器不能为空
	 */
	public StorageTemplate(ProviderClientManager<T> providerClientManager) {
		super(providerClientManager);
	}

	/**
	 * empty
	 */
	public StorageTemplate() {
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
			if (isUsePool()) {
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
			if (isUsePool()) {
				getRequiredProviderClientPoolingManager().returnClient(clinet);
			}
		}
	}

}
