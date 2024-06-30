package salthai.top.object.storage.core.provider;

import salthai.top.object.storage.core.operations.BaseOperations;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 池化管理 各个供应商客户端使用的接口
 *
 * @param <T> 链接对象
 * @author Kuang HaiBo 2024/6/3 14:41
 */
public interface ProviderClientPoolingManager<T> extends ProviderClientManager<T> {

	/**
	 * 回收对对象 预留的方法, 供对象池回收使用, 使用方式:{@link BaseOperations#execute(Consumer)}
	 * {@link BaseOperations#execute(Function)}
	 * @param client 对象
	 */
	void returnClient(T client);

}
