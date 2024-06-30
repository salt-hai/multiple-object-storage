package salthai.top.object.storage.core.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节文件包装
 *
 * @author Kuang HaiBo 2023/10/30 16:01
 */
public class ByteArrayFileWrapper implements FileWrapper {

	/**
	 * 字节
	 */
	private final byte[] bytes;

	/**
	 * 字节大小
	 */
	private Long byteSize;

	/**
	 * 文件媒体类型
	 */
	private String contentType;

	public ByteArrayFileWrapper(byte[] bytes, String contentType) {
		this.bytes = bytes;
		this.byteSize = (long) bytes.length;
		this.contentType = contentType;
	}

	/**
	 * 获取文件输入流
	 * @return 文件输入流
	 * @throws IOException 获取时可能出现异常
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
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
