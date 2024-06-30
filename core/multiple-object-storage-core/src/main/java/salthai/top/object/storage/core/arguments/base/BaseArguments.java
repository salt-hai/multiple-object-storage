
package salthai.top.object.storage.core.arguments.base;

import salthai.top.object.storage.core.model.arguments.ObjectStorageArguments;

import java.util.Map;

/**
 * 基础的请求参数
 *
 * @author Kunag
 */
public abstract class BaseArguments implements ObjectStorageArguments {

	/**
	 * 额外请求头
	 */
	private Map<String, String> extraHeaders;

	/**
	 * 额外请求参数
	 */
	private Map<String, String> extraQueryParams;

	public Map<String, String> getExtraHeaders() {
		return extraHeaders;
	}

	public void setExtraHeaders(Map<String, String> extraHeaders) {
		this.extraHeaders = extraHeaders;
	}

	public Map<String, String> getExtraQueryParams() {
		return extraQueryParams;
	}

	public void setExtraQueryParams(Map<String, String> extraQueryParams) {
		this.extraQueryParams = extraQueryParams;
	}

	@Override
	public String toString() {
		return "BaseArguments{" + "extraHeaders=" + extraHeaders + ", extraQueryParams=" + extraQueryParams + '}';
	}

}
