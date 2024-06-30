package com.hengtacc.object.storage.core.wrapper.adapter;

import com.hengtacc.object.storage.core.function.Ordered;
import com.hengtacc.object.storage.core.wrapper.FileWrapper;

import java.io.IOException;

/**
 * 文件包装适配器
 * <p>
 * 该接口为spi接口,如需要自定义请实现并提高使用的优先级{@link #getOrder()}并编写 *
 * "META-INF/services"相关配置,注意spi的实现不应有 有参数的构造函数
 * </p>
 *
 * @author Kuang HaiBo 2023/10/30 15:41
 */
public interface FileWrapperAdapter extends Ordered {

	/**
	 * 获取使用优先级 和spring 逻辑一致,默认的优先级是最低的
	 * @return 优先级,如需重写相同的适配器请把你的优先级提高即可
	 */
	@Override
	default int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	/**
	 * 支持何种来源
	 * @param sourceClass 来源类
	 * @return true 支持
	 */
	boolean supports(Class<?> sourceClass);

	/**
	 * 包装文件
	 * @param source 文件来源
	 * @throws IOException 包装过程中可能出现 IO 异常
	 * @return 文件包装对象
	 */
	default FileWrapper wrapper(Object source) throws IOException {
		return wrapper(source, null, null);
	}

	/**
	 * 包装文件
	 * @param source 文件来源
	 * @throws IOException 包装过程中可能出现 IO 异常
	 * @param fileByteSize 文件字节长度
	 * @return 文件包装对象
	 */
	default FileWrapper wrapper(Object source, Long fileByteSize) throws IOException {
		return wrapper(source, null, fileByteSize);
	}

	/**
	 * 包装文件
	 * @param source 来源
	 * @param contentType 文件媒体类型
	 * @return 包装对象
	 * @throws IOException 包装过程中可能出现 IO 异常
	 */
	default FileWrapper wrapper(Object source, String contentType) throws IOException {
		return wrapper(source, contentType, null);
	}

	/**
	 * 包装文件
	 * @param source 来源
	 * @param contentType 文件媒体类型
	 * @param fileByteSize 文件字节长度
	 * @return 包装对象
	 * @throws IOException 包装过程可能出现IO异常
	 */
	FileWrapper wrapper(Object source, String contentType, Long fileByteSize) throws IOException;

}
