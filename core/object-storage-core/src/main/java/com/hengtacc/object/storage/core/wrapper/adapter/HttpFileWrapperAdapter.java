package com.hengtacc.object.storage.core.wrapper.adapter;

import com.hengtacc.object.storage.core.exceptions.FileWrapperException;
import com.hengtacc.object.storage.core.wrapper.FileWrapper;
import com.hengtacc.object.storage.core.wrapper.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 *
 * 网络文件 {@link java.net.URL} or {@link URI}
 * <p>
 * 不推荐处理超大文件资源
 * </p>
 *
 * @author Kuang HaiBo 2023/11/1 10:19
 */
public class HttpFileWrapperAdapter implements FileWrapperAdapter {

	private static final Logger log = LoggerFactory.getLogger(HttpFileWrapperAdapter.class);

	/**
	 * 支持何种来源
	 * @param sourceClass 来源类
	 * @return true 支持
	 */
	@Override
	public boolean supports(Class<?> sourceClass) {
		return URL.class.isAssignableFrom(sourceClass) || URI.class.isAssignableFrom(sourceClass);
	}

	/**
	 * 包装文件
	 * @param source 来源
	 * @param contentType 文件媒体类型
	 * @param fileByteSize 文件字节长度
	 * @return 包装对象
	 * @throws IOException 包装过程可能出现IO异常
	 */
	@Override
	public FileWrapper wrapper(Object source, String contentType, Long fileByteSize) throws IOException {
		URL fileUrl = null;
		if (source instanceof URL) {
			fileUrl = (URL) source;
		}
		if (source instanceof URI) {
			fileUrl = ((URI) source).toURL();
		}
		if (Objects.isNull(fileUrl)) {
			throw new FileWrapperException("不支持包装非 URL  URI 的文件");
		}
		if (!urlResourceExist(fileUrl)) {
			throw new FileWrapperException("所给的文件资源不存在");
		}
		URLConnection urlConnection = fileUrl.openConnection();
		try {
			InputStream stream = urlConnection.getInputStream();
			InputStreamWrapper httpFileWrapper = new InputStreamWrapper(stream);
			httpFileWrapper
				.setByteSize(Objects.nonNull(fileByteSize) ? fileByteSize : urlConnection.getContentLength());
			httpFileWrapper.setContentType(Objects.nonNull(contentType) ? contentType : urlConnection.getContentType());
			return httpFileWrapper;
		}
		catch (IOException ioException) {
			// 当意外出现时 需要关闭链接
			disconnect(urlConnection);
			if (log.isDebugEnabled()) {
				log.debug("==>  wrapper http resource file exception:", ioException);
			}
			throw ioException;
		}
	}

	/**
	 * 资源存在?
	 * @param url url 资源
	 * @return 存在 ture
	 */
	boolean urlResourceExist(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		try {
			if (urlConnection instanceof HttpURLConnection) {
				((HttpURLConnection) urlConnection).setRequestMethod("HEAD");
				return ((HttpURLConnection) urlConnection).getResponseCode() == HttpURLConnection.HTTP_OK;
			}
			return urlConnection.getContentLength() >= 0;
		}
		finally {
			disconnect(urlConnection);
		}
	}

	/**
	 * 销毁链接
	 * @param connection 链接
	 */
	void disconnect(URLConnection connection) {
		if (connection instanceof HttpURLConnection) {
			((HttpURLConnection) connection).disconnect();
		}
	}

}
