package salthai.top.object.storage.core.domain.object;

import salthai.top.object.storage.core.domain.base.BaseDomain;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 获取对象
 *
 * @author Kuang HaiBo 2023/11/8 10:43
 */
public class GetObjectDomain extends BaseDomain implements Closeable {

	/**
	 * 该对象内容 以输入流形式存在，调用方使用完需要自行关闭
	 */
	private InputStream objectContent;

	public InputStream getObjectContent() {
		return objectContent;
	}

	public void setObjectContent(InputStream objectContent) {
		this.objectContent = objectContent;
	}

	/**
	 * 关闭下载对象
	 * @throws IOException IO异常
	 */
	@Override
	public void close() throws IOException {
		if (objectContent != null) {
			objectContent.close();
		}
	}

	@Override
	public String toString() {
		return "GetObjectDomain{" + "objectContent=" + objectContent + "} " + super.toString();
	}

}
