package salthai.top.object.storage.core.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * SPI机制中的服务加载工具类 <pre>
 *     1、创建接口，并创建实现类
 *     2、ClassPath/META-INF/services下创建与接口全限定类名相同的文件
 *     3、文件内容填写实现类的全限定类名
 * </pre>
 *
 * @author Kuang HaiBo 2025/6/17 15:09
 */
public class ServiceLoaderUtil {

	/**
	 * 加载服务
	 * @param <T> 接口类型
	 * @param clazz 服务接口
	 * @param loader {@link ClassLoader}
	 * @return 服务接口实现列表
	 */
	public static <T> ServiceLoader<T> load(Class<T> clazz, ClassLoader loader) {
		return ServiceLoader.load(clazz, ObjectUtils.defaultIfNull(loader, getClassLoader()));
	}

	/**
	 * 加载服务 并已list列表返回
	 * @param <T> 接口类型
	 * @param clazz 服务接口
	 * @return 服务接口实现列表
	 * @since 5.4.2
	 */
	public static <T> List<T> loadList(Class<T> clazz) {
		return loadList(clazz, null);
	}

	/**
	 * 加载服务 并已list列表返回
	 * @param <T> 接口类型
	 * @param clazz 服务接口
	 * @param loader {@link ClassLoader}
	 * @return 服务接口实现列表
	 * @since 5.4.2
	 */
	public static <T> List<T> loadList(Class<T> clazz, ClassLoader loader) {
		List<T> list = new ArrayList<>();
		ServiceLoader<T> loaded = load(clazz, loader);
		for (T t : loaded) {
			list.add(t);
		}
		return list;

	}

	/**
	 * 获取当前线程的{@link ClassLoader}
	 * @return 当前线程的class loader
	 * @see Thread#getContextClassLoader()
	 */
	private static ClassLoader getContextClassLoader() {
		if (System.getSecurityManager() == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		else {
			// 绕开权限检查
			return AccessController
				.doPrivileged((PrivilegedAction<ClassLoader>) () -> Thread.currentThread().getContextClassLoader());
		}
	}

	/**
	 * 获取系统{@link ClassLoader}
	 * @return 系统{@link ClassLoader}
	 * @see ClassLoader#getSystemClassLoader()
	 * @since 5.7.0
	 */
	private static ClassLoader getSystemClassLoader() {
		if (System.getSecurityManager() == null) {
			return ClassLoader.getSystemClassLoader();
		}
		else {
			// 绕开权限检查
			return AccessController.doPrivileged((PrivilegedAction<ClassLoader>) ClassLoader::getSystemClassLoader);
		}
	}

	/**
	 * 获取{@link ClassLoader}<br>
	 * 获取顺序如下：<br>
	 *
	 * <pre>
	 * 1、获取当前线程的ContextClassLoader
	 * 2、获取当前类对应的ClassLoader
	 * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
	 * </pre>
	 * @return 类加载器
	 */
	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = getContextClassLoader();
		if (classLoader == null) {
			classLoader = ServiceLoaderUtil.class.getClassLoader();
			if (null == classLoader) {
				classLoader = getSystemClassLoader();
			}
		}
		return classLoader;
	}

}
