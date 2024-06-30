package salthai.top.object.storage.core.wrapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流文件包装器
 *
 * @author Kuang HaiBo 2023/10/30 17:24
 */
public class InputStreamWrapper implements FileWrapper {

	private final InputStream inputStream;

	private String contentType;

	/**
	 * 文件字节长度
	 */
	private Long byteSize;

	public InputStreamWrapper(InputStream inputStream, String contentType, Long byteSize) {
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.byteSize = byteSize;
	}

	public InputStreamWrapper(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * <p>
	 * 非空
	 * </p>
	 * 获取文件输入流
	 * @return 文件输入流
	 * @throws IOException 获取时可能出现异常
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	/**
	 * <p>
	 * 非空，尽量能获取到文件大小，
	 * </p>
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
	 * <p>
	 * 可空
	 * </p>
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
