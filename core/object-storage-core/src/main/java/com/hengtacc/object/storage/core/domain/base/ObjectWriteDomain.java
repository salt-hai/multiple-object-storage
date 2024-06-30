package com.hengtacc.object.storage.core.domain.base;

/**
 * 对象写入 领域对象
 *
 * @author Kuang HaiBo 2023/11/8 10:39
 */
public class ObjectWriteDomain extends BaseDomain {

	/**
	 * etag 值
	 */
	private String etag;

	/**
	 * 版本id
	 */
	private String versionId;

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@Override
	public String toString() {
		return "ObjectWriteDomain{" + "etag='" + etag + '\'' + ", versionId='" + versionId + '\'' + "} "
				+ super.toString();
	}

}
