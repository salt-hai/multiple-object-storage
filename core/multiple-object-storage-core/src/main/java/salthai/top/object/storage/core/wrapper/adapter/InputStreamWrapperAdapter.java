package salthai.top.object.storage.core.wrapper.adapter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import salthai.top.object.storage.core.content.ContentTypeDetect;
import salthai.top.object.storage.core.unit.DataSize;
import salthai.top.object.storage.core.wrapper.FileWrapper;
import salthai.top.object.storage.core.wrapper.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 输入流包装适配
 *
 * @author Kuang HaiBo 2023/10/31 11:26
 */
public class InputStreamWrapperAdapter implements FileWrapperAdapter {

	private static final Logger log = LoggerFactory.getLogger(InputStreamWrapper.class);

	private final ContentTypeDetect contentTypeDetect;

	public InputStreamWrapperAdapter(ContentTypeDetect contentTypeDetect) {
		this.contentTypeDetect = contentTypeDetect;
	}

	/**
	 * 支持何种来源
	 * @param sourceClass 来源类
	 * @return true 支持
	 */
	@Override
	public boolean supports(Class<?> sourceClass) {
		return InputStream.class.isAssignableFrom(sourceClass);
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
		if (Objects.isNull(fileByteSize)) {
			log.warn("==> [文件包装] 包装输入流InputStream以及子类时请尽量指定文件长度");
			fileByteSize = -1L;
		}
		// 不需要确认流文件具体的 媒体类型
		if (StrUtil.isNotBlank(contentType)) {
			return new InputStreamWrapper((InputStream) source, contentType, fileByteSize);
		}
		// 转为支持标记的流 以供确认 媒体类型时二次使用
		InputStream target = IoUtil.toMarkSupportStream((InputStream) source);
		if (!target.markSupported()) {
			throw new IOException("cant not convert to mark support stream");
		}
		try {
			target.mark((int) DataSize.ofKilobytes(64).toBytes());
			contentType = contentTypeDetect.detect(target);
			target.reset();
			return new InputStreamWrapper(target, contentType, fileByteSize);
		}
		catch (IOException ioException) {
			// 异常时需要关闭资源
			target.close();
			throw ioException;
		}
	}

}
