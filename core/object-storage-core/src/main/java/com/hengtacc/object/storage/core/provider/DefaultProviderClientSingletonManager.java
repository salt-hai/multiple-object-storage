package com.hengtacc.object.storage.core.provider;

import com.hengtacc.object.storage.core.function.SingletonSupplier;
import com.hengtacc.object.storage.core.provider.factory.ProviderClientFactory;

import java.io.Closeable;
import java.util.Objects;

/**
 * 默认的单例实现
 *
 * @author Kuang HaiBo 2024/5/24 14:13
 */
public class DefaultProviderClientSingletonManager<T> implements ProviderClientManager<T> {

	/**
	 * 创建链接对象的工厂 .可为空
	 */
	private ProviderClientFactory<T> providerClientFactory;

	/**
	 * 链接对象单例供应商
	 */
	final SingletonSupplier<T> clientSingletonSupplier;

	/**
	 * 使用工厂实例化， 创建和关闭对象，都使用工厂管理
	 * @param providerClientFactory 链接对象工厂
	 */
	public DefaultProviderClientSingletonManager(ProviderClientFactory<T> providerClientFactory) {
		// 使用工厂实例化该管理器时,延迟创建客户端实例,当需要获取时再创建
		clientSingletonSupplier = SingletonSupplier.of(providerClientFactory::create);
		this.providerClientFactory = providerClientFactory;
	}

	/**
	 * 使用链接对象单例供应商 实例化, 获取对象使用供应商获取,会尝试进行关闭对象
	 * @param clientSingletonSupplier 链接对象单例供应商
	 */
	public DefaultProviderClientSingletonManager(SingletonSupplier<T> clientSingletonSupplier) {
		this.clientSingletonSupplier = clientSingletonSupplier;
	}

	/**
	 * 获取相应的sdk 客户端 不为空
	 * @return 客户端
	 */
	@Override
	public T getClient() {
		return clientSingletonSupplier.get();
	}

	/**
	 * 释放相关资源
	 */
	@Override
	public void close() throws Exception {
		T client = clientSingletonSupplier.get();
		if (Objects.isNull(client)) {
			return;
		}
		if (Objects.nonNull(providerClientFactory)) {
			providerClientFactory.destroy(client);
			return;
		}
		// maybe can close
		if (client instanceof Closeable) {
			((Closeable) client).close();
			return;
		}
		if (client instanceof AutoCloseable) {
			((AutoCloseable) client).close();
			return;
		}
		log.warn("==> 您的链接对象无法关闭,请注意:[{}]", client);
	}

}
