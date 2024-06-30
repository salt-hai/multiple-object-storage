package com.hengtacc.object.storage.core.domain.base;

import com.hengtacc.object.storage.core.model.domain.ObjectStorageDomain;

/**
 * 对象存储 所有者 领域
 *
 * @author Kuang HaiBo 2023/11/8 10:40
 */
public class OwnerDomain implements ObjectStorageDomain {

	/**
	 * 所有者 ID
	 */
	private String id;

	/**
	 * 所有者显示名称
	 */
	private String displayName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "OwnerDomain{" + "id='" + id + '\'' + ", displayName='" + displayName + '\'' + '}';
	}

}
