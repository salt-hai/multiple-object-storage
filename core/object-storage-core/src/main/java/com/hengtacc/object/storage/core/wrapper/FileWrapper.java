package com.hengtacc.object.storage.core.wrapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件包装
 * <p>
 * 提供一个接口，包装文件信息，以适配系统内存在的文件形式
 * </p>
 *
 * @author Kuang HaiBo 2023/10/27 14:54
 */
public interface FileWrapper {

	/**
	 * <p>
	 * 非空
	 * </p>
	 * 获取文件输入流
	 * @throws IOException 获取时可能出现异常
	 * @return 文件输入流
	 */
	InputStream getInputStream() throws IOException;

	/**
	 * <p>
	 * 非空，尽量能获取到文件大小，当获取不到文件大小时,框架目前并不会进行读取流获取，如有大文件这将耗费大量内存
	 * <p>
	 * 还是希望各个实现类或适配器类能正确获取文件大小
	 * </p>
	 * 获取文件字节大小
	 * @return 字节大小
	 *
	 */
	default Long getByteSize() {
		return -1L;
	}

	/**
	 * 设置字节大小
	 * @param byteSize 文件字节大小
	 */
	void setByteSize(Long byteSize);

	/**
	 * 获取文件媒体类型
	 * <p>
	 * 不能为空 默认是:application/octet-stream
	 * </p>
	 * @return 媒体类型
	 */
	default String getContentType() {
		return "application/octet-stream";
	}

	/**
	 * 设置媒体类型
	 * @param contentType 媒体类型
	 */
	void setContentType(String contentType);

}
