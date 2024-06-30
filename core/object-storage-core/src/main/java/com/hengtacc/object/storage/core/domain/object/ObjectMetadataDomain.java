package com.hengtacc.object.storage.core.domain.object;

import com.hengtacc.object.storage.core.domain.base.ObjectWriteDomain;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 对象元数据
 *
 * @author Kuang HaiBo 2023/11/8 10:55
 */
public class ObjectMetadataDomain extends ObjectWriteDomain {

	/**
	 * 自定义的 元数据
	 */
	private Map<String, String> userMetadata = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * 内容大小
	 */
	private long contentLength;

	/**
	 * 内容类型 mine 类型
	 */
	private String contentType;

	/**
	 * 最后修改时间
	 */
	private Date lastModified;

	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	public void setUserMetadata(Map<String, String> userMetadata) {
		if (Objects.isNull(userMetadata) || userMetadata.isEmpty()) {
			return;
		}
		this.userMetadata = userMetadata;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "ObjectMetadataDomain{" + "userMetadata=" + userMetadata + ", contentLength=" + contentLength
				+ ", contentType='" + contentType + '\'' + ", lastModified=" + lastModified + "} " + super.toString();
	}

}
