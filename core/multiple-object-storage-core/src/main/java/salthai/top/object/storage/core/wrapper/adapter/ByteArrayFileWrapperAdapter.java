package salthai.top.object.storage.core.wrapper.adapter;

import cn.hutool.core.util.StrUtil;
import salthai.top.object.storage.core.content.ContentTypeDetect;
import salthai.top.object.storage.core.wrapper.ByteArrayFileWrapper;
import salthai.top.object.storage.core.wrapper.FileWrapper;

import java.io.IOException;

/**
 * 字节数组
 *
 * @author Kuang HaiBo 2023/10/30 16:04
 */
public class ByteArrayFileWrapperAdapter implements FileWrapperAdapter {

	private final ContentTypeDetect contentTypeDetect;

	public ByteArrayFileWrapperAdapter(ContentTypeDetect contentTypeDetect) {
		this.contentTypeDetect = contentTypeDetect;
	}

	/**
	 * 支持何种来源
	 * @param sourceClass 来源类
	 * @return true 支持
	 */
	@Override
	public boolean supports(Class<?> sourceClass) {
		return byte[].class.isAssignableFrom(sourceClass);
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
		if (StrUtil.isBlank(contentType)) {
			contentType = contentTypeDetect.detect((byte[]) source);
		}
		return new ByteArrayFileWrapper((byte[]) source, contentType);
	}

}
