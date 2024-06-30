package salthai.top.object.storage.core.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务商管理器
 * <p>
 * 两种当spring环境下,当应用停止时会执行自动关闭{@link #close()}此时关闭的是对象, 对于对象池设计来说关闭的是对象池,都是自动关闭的
 * </p>
 *
 * @author Kuang HaiBo 2023/10/27 10:27
 */
public interface ProviderClientManager<Client> extends AutoCloseable {

	Logger log = LoggerFactory.getLogger(ProviderClientManager.class);

	/**
	 * 获取相应的sdk 客户端 不为空
	 * @return 客户端 单例对象
	 */
	Client getClient();

	/**
	 * 释放相关资源
	 */
	@Override
	default void close() throws Exception {
		log.warn("==> 对象存储服务商:[{}] 未实现关闭客户端方法,请注意", getClass());
	}

}
