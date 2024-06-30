package salthai.top.object.storage.core.wrapper.adapter;

import cn.hutool.core.util.StrUtil;
import salthai.top.object.storage.core.content.ContentTypeDetect;
import salthai.top.object.storage.core.wrapper.DiskFileWrapper;
import salthai.top.object.storage.core.wrapper.FileWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * 本地文件包装适配
 *
 * @author Kuang HaiBo 2023/10/30 15:48
 */
public class DiskFileWrapperAdapter implements FileWrapperAdapter {

	private final ContentTypeDetect contentTypeDetect;

	public DiskFileWrapperAdapter(ContentTypeDetect contentTypeDetect) {
		this.contentTypeDetect = contentTypeDetect;
	}

	/**
	 * 支持何种来源
	 * @param sourceClass 来源类
	 * @return true 支持
	 */
	@Override
	public boolean supports(Class<?> sourceClass) {
		return File.class.isAssignableFrom(sourceClass);
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
		if (!((File) source).exists()) {
			throw new FileNotFoundException("'" + ((File) source).getAbsolutePath() + "' this path cant not load");
		}
		DiskFileWrapper diskFileWrapper = new DiskFileWrapper((File) source);
		if (Objects.nonNull(fileByteSize)) {
			diskFileWrapper.setByteSize(fileByteSize);
		}
		if (StrUtil.isBlank(contentType)) {
			diskFileWrapper.setContentType(contentTypeDetect.detect(((File) source).getName()));
		}
		return diskFileWrapper;
	}

}
