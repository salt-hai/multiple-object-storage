package com.hengtacc.object.storage.core;

import cn.hutool.core.util.ServiceLoaderUtil;
import com.hengtacc.object.storage.core.content.ContentTypeDetect;
import com.hengtacc.object.storage.core.content.TikaContentTypeDetect;
import com.hengtacc.object.storage.core.exceptions.FileWrapperException;
import com.hengtacc.object.storage.core.function.OrderComparator;
import com.hengtacc.object.storage.core.wrapper.FileWrapper;
import com.hengtacc.object.storage.core.wrapper.adapter.ByteArrayFileWrapperAdapter;
import com.hengtacc.object.storage.core.wrapper.adapter.DiskFileWrapperAdapter;
import com.hengtacc.object.storage.core.wrapper.adapter.FileWrapperAdapter;
import com.hengtacc.object.storage.core.wrapper.adapter.HttpFileWrapperAdapter;
import com.hengtacc.object.storage.core.wrapper.adapter.InputStreamWrapperAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 文件包装 使用工具 ,用于适配不同的文件 内置: File, byte[]数组文件，URL URI 网络文件 InputStream子类
 * <p>
 * <li>source: 来源一律不为空,
 * <li>contentType:为空时会进行自动识别,
 * <li>fileByteSize:尽量进行指定,但是对于InputStream来说并不会进行读取流来获取，该值对业务影响不大
 * </p>
 *
 * @author Kuang HaiBo 2023/10/30 16:48
 */
public class FileWrappers {

	private static final Collection<FileWrapperAdapter> WRAPPER_ADAPTERS = new ArrayList<>();

	static {
		// 加载文件mine类型识别器
		List<ContentTypeDetect> contentTypeDetects = ServiceLoaderUtil.loadList(ContentTypeDetect.class);
		OrderComparator.sort(contentTypeDetects);

		ContentTypeDetect determinedContentTypeDetect = contentTypeDetects.isEmpty() ? new TikaContentTypeDetect()
				: contentTypeDetects.get(0);

		WRAPPER_ADAPTERS.add(new ByteArrayFileWrapperAdapter(determinedContentTypeDetect));
		WRAPPER_ADAPTERS.add(new DiskFileWrapperAdapter(determinedContentTypeDetect));
		WRAPPER_ADAPTERS.add(new InputStreamWrapperAdapter(determinedContentTypeDetect));
		WRAPPER_ADAPTERS.add(new HttpFileWrapperAdapter());
		// 加载使用方定义的
		WRAPPER_ADAPTERS.addAll(ServiceLoaderUtil.loadList(FileWrapperAdapter.class));
	}

	/**
	 * 包装文件
	 * @param source 文件来源
	 * @return 文件包装对象
	 */
	public static FileWrapper wrapper(Object source) {
		Objects.requireNonNull(source, "source cant be null");
		try {
			return determineAdapter(source).wrapper(source);
		}
		catch (IOException e) {
			throw new FileWrapperException("文件包装异常:" + e);
		}
	}

	/**
	 * 包装文件
	 * @param source 文件来源
	 * @param fileByteSize 文件字节长度
	 * @return 文件包装对象
	 */
	public static FileWrapper wrapper(Object source, Long fileByteSize) {
		Objects.requireNonNull(source, "source cant be null");
		try {
			return determineAdapter(source).wrapper(source, fileByteSize);
		}
		catch (IOException e) {
			throw new FileWrapperException("文件包装异常:" + e);
		}
	}

	/**
	 * 包装文件
	 * @param source 来源
	 * @param contentType 文件媒体类型
	 * @return 包装对象
	 */
	public static FileWrapper wrapper(Object source, String contentType) {
		Objects.requireNonNull(source, "source cant be null");
		try {
			return determineAdapter(source).wrapper(source, contentType);
		}
		catch (IOException e) {
			throw new FileWrapperException("文件包装异常:" + e);
		}
	}

	/**
	 * 包装文件
	 * @param source 来源
	 * @param contentType 可以为空, 文件媒体类型 媒体类型得不到会进行自动识别
	 * @param fileByteSize 可以为空,文件大小,当能获取文件大小时尽量传递
	 * @return 包装对象
	 */
	public static FileWrapper wrapper(Object source, String contentType, Long fileByteSize) {
		Objects.requireNonNull(source, "source cant be null");
		try {
			return determineAdapter(source).wrapper(source, contentType, fileByteSize);
		}
		catch (IOException e) {
			throw new FileWrapperException("文件包装异常:" + e);
		}
	}

	/**
	 * 确认文件包装 适配器
	 * @param source 文件来源
	 * @return 包装适配器
	 */
	public static FileWrapperAdapter determineAdapter(Object source) {
		return WRAPPER_ADAPTERS.stream()
			.filter(fileWrapperAdapter -> fileWrapperAdapter.supports(source.getClass()))
			.min(OrderComparator.INSTANCE)
			.orElseThrow(() -> new FileWrapperException("对于文件来源:[" + source.getClass() + "]找不到合适的文件包装器"));
	}

}
