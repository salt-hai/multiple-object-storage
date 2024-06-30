package salthai.top.object.storage.core.domain.base;

import salthai.top.object.storage.core.model.domain.ObjectStorageDomain;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 基础响应对象
 *
 * @author Kuang HaiBo 2023/12/15 11:22
 */
public class BaseResponseDomain implements ObjectStorageDomain {

	/**
	 * 响应头部信息
	 */
	private Map<String, Object> header = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		if (Objects.isNull(header) || header.isEmpty()) {
			return;
		}
		this.header = header;
	}

	/**
	 * 新增响应头部
	 * @param key key
	 * @param value value
	 */
	public void addHeader(String key, Object value) {
		this.header.put(key, value);
	}

	@Override
	public String toString() {
		return "BaseObjectsDomain{" + "header=" + header + '}';
	}

}