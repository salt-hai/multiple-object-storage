package com.hengtacc.object.storage.core.provider;

import com.hengtacc.object.storage.core.provider.factory.ProviderClientFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 使用对象池实现的客户端管理器
 * <p>
 *
 * @author Kuang HaiBo 2024/5/23 17:14
 */
public class DefaultProviderClientPoolingManager<T> implements ProviderClientPoolingManager<T> {

	private final Logger log = LoggerFactory.getLogger(DefaultProviderClientPoolingManager.class);

	/**
	 * 客户端对象池，对象都是从这里取出 not null
	 */
	final ObjectPool<T> objectPool;

	/**
	 * 提供一个对象池直接构建
	 * @param objectPool 通用对象池
	 */
	public DefaultProviderClientPoolingManager(ObjectPool<T> objectPool) {
		Objects.requireNonNull(objectPool, "ObjectPool must be not null");
		this.objectPool = objectPool;
	}

	/**
	 * 通过工厂创建
	 * @param instantiateFactory 实例工厂
	 * @param poolConfig 池配置
	 */
	@SuppressWarnings("rawtypes,unchecked")
	public DefaultProviderClientPoolingManager(ProviderClientFactory<T> instantiateFactory,
			GenericObjectPoolConfig poolConfig) {
		objectPool = ProviderPoolSupport.createGenericObjectPool(instantiateFactory, poolConfig);
	}

	/**
	 * 获取相应的sdk 客户端
	 * @return 客户端
	 */
	@Override
	public T getClient() {
		try {
			return objectPool.borrowObject();
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * 归还
	 * @param t 对象
	 */
	@Override
	public void returnClient(T t) {
		try {
			objectPool.returnObject(t);
		}
		catch (Exception e) {
			log.error("==>  returnClient error:", e);
		}
	}

	/**
	 * 释放相关资源
	 */
	@Override
	public void close() {
		objectPool.close();
	}

}
