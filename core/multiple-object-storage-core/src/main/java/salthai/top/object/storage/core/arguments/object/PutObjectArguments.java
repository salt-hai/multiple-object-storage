package salthai.top.object.storage.core.arguments.object;

import salthai.top.object.storage.core.arguments.base.PutObjectBaseArguments;

import java.io.InputStream;
import java.util.Objects;

/**
 * <p>
 * acl建议put完成之后再执行设置,若put时或put完成之后不设置acl,此时对象的的acl绝大部分情况会跟着bucket走
 * </p>
 * 上传一个文件 请求参数
 *
 * @author Kuang HaiBo 2023/11/7 13:54
 */
public class PutObjectArguments extends PutObjectBaseArguments {

	/**
	 * 要上传的文件流 不能为空,调用方使用完需要自己关闭
	 */
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		Objects.requireNonNull(inputStream, "inputStream cant be null");
		this.inputStream = inputStream;
	}

}
