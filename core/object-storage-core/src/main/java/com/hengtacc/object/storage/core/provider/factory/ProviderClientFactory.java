package com.hengtacc.object.storage.core.provider.factory;

/**
 * 创建实例的工厂
 *
 * @author Kuang HaiBo 2024/6/3 14:48
 */
public interface ProviderClientFactory<T> {

	/**
	 * 创建一个实例，非空
	 * <p>
	 * 通过使用该接口的实例，可以创建一个新的客户端
	 * </p>
	 * @return 实例
	 */
	T create();

	/**
	 * 销毁，集中销毁实例
	 * @param instance 关闭
	 */
	void destroy(T instance);

}
