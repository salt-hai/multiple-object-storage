package salthai.top.object.storage.core.arguments.base;

import java.util.Map;

/**
 * 对象写入基础请求参数
 *
 * @author Kuang HaiBo 2023/11/7 10:33
 */
public abstract class ObjectWriteArguments extends ObjectArguments {

	/**
	 * 请求头
	 */
	private Map<String, String> requestHeaders;

	/**
	 * 元数据
	 */
	private Map<String, String> metadata;

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "ObjectWriteArguments{" + "requestHeaders=" + requestHeaders + ", metadata=" + metadata + "} "
				+ super.toString();
	}

}
