package salthai.top.object.storage.core.content;

import salthai.top.object.storage.core.function.Ordered;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 识别文件的 MIME 类型 <a href=
 * "https://zh.wikipedia.org/zh-cn/%E4%BA%92%E8%81%94%E7%BD%91%E5%AA%92%E4%BD%93%E7%B1%BB%E5%9E%8B">简介</a>
 * <p>
 * 一个文件mine类型识别的spi接口,如需要自定义请实现并提高使用的优先级{@link #getOrder()}并编写
 * "META-INF/services"相关配置,注意spi的实现不应有 有参数的构造函数
 * </p>
 *
 * @author Kuang HaiBo 2023/10/30 10:30
 */
public interface ContentTypeDetect extends Ordered {

	/**
	 * 获取此对象的order值。 较高的值被解释为较低的优先级。因此，具有最低值的对象具有最高优先级 (有点类似于Servlet启动时加载的值)。
	 * 相同的顺序值将导致受影响对象的任意排序位置
	 * <p>
	 * @return the order value
	 * @see #HIGHEST_PRECEDENCE
	 * @see #LOWEST_PRECEDENCE
	 */
	@Override
	default int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	/**
	 * 通过文件名检测
	 * @param fileName 文件名 带拓展名
	 * @return 探测结果
	 * @throws IOException IO 异常
	 */
	String detect(String fileName) throws IOException;

	/**
	 * 识别
	 * @param file 文件对象
	 * @return mine 类型 application/zip
	 * @throws IOException io 异常
	 */
	String detect(File file) throws IOException;

	/**
	 * 识别
	 * @param bytes 字节数组
	 * @return mine类型
	 */
	String detect(byte[] bytes);

	/**
	 * 识别
	 * @param in 输入流
	 * @return 媒体类型
	 * @throws IOException io异常
	 */
	String detect(InputStream in) throws IOException;

}
