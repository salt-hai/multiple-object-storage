package com.hengtacc.object.storage.core.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 本地文件包装
 *
 * @author Kuang HaiBo 2023/10/30 15:24
 */
public class DiskFileWrapper implements FileWrapper {

	private final File file;

	private String contentType;

	/**
	 * 文件字节长度
	 */
	private Long byteSize;

	public DiskFileWrapper(File file) {
		this.file = file;
		setByteSize(file.length());
	}

	public DiskFileWrapper(File file, String contentType) {
		this.file = file;
		setContentType(contentType);
		setByteSize(file.length());
	}

	public DiskFileWrapper(File file, String contentType, Long byteSize) {
		this.file = file;
		setContentType(contentType);
		setByteSize(byteSize);
	}

	/**
	 * 获取文件输入流
	 * @return 文件输入流
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return Files.newInputStream(file.toPath());
	}

	/**
	 * 获取文件字节大小
	 * @return 字节大小
	 */
	@Override
	public Long getByteSize() {
		return byteSize;
	}

	/**
	 * 设置字节大小
	 * @param byteSize 文件字节大小
	 */
	@Override
	public void setByteSize(Long byteSize) {
		this.byteSize = byteSize;
	}

	/**
	 * 获取文件媒体类型
	 * @return 媒体类型
	 */
	@Override
	public String getContentType() {
		return contentType;
	}

	/**
	 * 设置媒体类型
	 * @param contentType 媒体类型
	 */
	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
