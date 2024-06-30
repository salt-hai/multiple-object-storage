package com.hengtacc.object.storage.core.content;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 默认 使用 tika 进行媒体类型解析
 *
 * @author Kuang HaiBo 2023/10/30 10:37
 */
public class TikaContentTypeDetect implements ContentTypeDetect {

	/**
	 * tika 对象
	 */
	private final Tika tika = new Tika();

	/**
	 * 通过文件名检测
	 * @param fileName 文件名 带拓展名
	 * @return 探测结果
	 * @throws IOException IO 异常
	 */
	@Override
	public String detect(String fileName) throws IOException {
		return tika.detect(fileName);
	}

	/**
	 * 识别
	 * @param file 文件对象
	 * @return mine 类型 application/zip
	 * @throws IOException io 异常
	 */
	@Override
	public String detect(File file) throws IOException {
		// just use name
		return detect(file.getName());
	}

	/**
	 * 识别
	 * @param bytes 字节数组
	 * @return mine类型
	 */
	@Override
	public String detect(byte[] bytes) {
		return tika.detect(bytes);
	}

	/**
	 * 识别
	 * @param in 输入流
	 * @return 媒体类型
	 * @throws IOException io异常
	 */
	@Override
	public String detect(InputStream in) throws IOException {
		return tika.detect(in);
	}

}
