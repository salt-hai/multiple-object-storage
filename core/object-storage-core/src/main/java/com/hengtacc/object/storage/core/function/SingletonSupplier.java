package com.hengtacc.object.storage.core.function;

import cn.hutool.core.lang.Assert;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * copy from spring
 *
 * @author Kuang HaiBo 2023/10/27 15:16
 */
public class SingletonSupplier<T> implements Supplier<T> {

	private final Supplier<? extends T> instanceSupplier;

	private final Supplier<? extends T> defaultSupplier;

	private volatile T singletonInstance;

	/**
	 * Guards access to write operations on the response.
	 */
	private final Lock writeLock = new ReentrantLock();

	/**
	 * Build a {@code SingletonSupplier} with the given singleton instance and a default
	 * supplier for the case when the instance is {@code null}.
	 * @param instance the singleton instance (potentially {@code null})
	 * @param defaultSupplier the default supplier as a fallback
	 */
	public SingletonSupplier(T instance, Supplier<? extends T> defaultSupplier) {
		this.instanceSupplier = null;
		this.defaultSupplier = defaultSupplier;
		this.singletonInstance = instance;
	}

	/**
	 * Build a {@code SingletonSupplier} with the given instance supplier and a default
	 * supplier for the case when the instance is {@code null}.
	 * @param instanceSupplier the immediate instance supplier
	 * @param defaultSupplier the default supplier as a fallback
	 */
	public SingletonSupplier(Supplier<? extends T> instanceSupplier, Supplier<? extends T> defaultSupplier) {
		this.instanceSupplier = instanceSupplier;
		this.defaultSupplier = defaultSupplier;
	}

	private SingletonSupplier(Supplier<? extends T> supplier) {
		this.instanceSupplier = supplier;
		this.defaultSupplier = null;
	}

	private SingletonSupplier(T singletonInstance) {
		this.instanceSupplier = null;
		this.defaultSupplier = null;
		this.singletonInstance = singletonInstance;
	}

	/**
	 * Get the shared singleton instance for this supplier.
	 * @return the singleton instance (or {@code null} if none)
	 */
	@Override
	public T get() {
		T instance = this.singletonInstance;
		if (instance == null) {
			this.writeLock.lock();
			try {
				instance = this.singletonInstance;
				if (instance == null) {
					if (this.instanceSupplier != null) {
						instance = this.instanceSupplier.get();
					}
					if (instance == null && this.defaultSupplier != null) {
						instance = this.defaultSupplier.get();
					}
					this.singletonInstance = instance;
				}
			}
			finally {
				this.writeLock.unlock();
			}
		}
		return instance;
	}

	/**
	 * Obtain the shared singleton instance for this supplier.
	 * @return the singleton instance (never {@code null})
	 * @throws IllegalStateException in case of no instance
	 */
	public T obtain() {
		T instance = get();
		Assert.state(instance != null, "No instance from Supplier");
		return instance;
	}

	/**
	 * Build a {@code SingletonSupplier} with the given singleton instance.
	 * @param instance the singleton instance (never {@code null})
	 * @return the singleton supplier (never {@code null})
	 */
	public static <T> SingletonSupplier<T> of(T instance) {
		return new SingletonSupplier<>(instance);
	}

	/**
	 * Build a {@code SingletonSupplier} with the given singleton instance.
	 * @param instance the singleton instance (potentially {@code null})
	 * @return the singleton supplier, or {@code null} if the instance was {@code null}
	 */
	public static <T> SingletonSupplier<T> ofNullable(T instance) {
		return (instance != null ? new SingletonSupplier<>(instance) : null);
	}

	/**
	 * Build a {@code SingletonSupplier} with the given supplier.
	 * @param supplier the instance supplier (never {@code null})
	 * @return the singleton supplier (never {@code null})
	 */
	public static <T> SingletonSupplier<T> of(Supplier<T> supplier) {
		return new SingletonSupplier<>(supplier);
	}

	/**
	 * Build a {@code SingletonSupplier} with the given supplier.
	 * @param supplier the instance supplier (potentially {@code null})
	 * @return the singleton supplier, or {@code null} if the instance supplier was
	 * {@code null}
	 */
	public static <T> SingletonSupplier<T> ofNullable(Supplier<T> supplier) {
		return (supplier != null ? new SingletonSupplier<>(supplier) : null);
	}

}