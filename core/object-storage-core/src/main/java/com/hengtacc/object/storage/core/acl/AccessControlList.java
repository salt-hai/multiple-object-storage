package com.hengtacc.object.storage.core.acl;

/**
 * acl 兼容性枚举
 * <p>
 * 如果要有更高级的使用，请使用高级service进行操作
 * </p>
 *
 * @author Kuang HaiBo 2023/11/7 17:09
 */
public enum AccessControlList {

	/**
	 * 私有
	 */
	Private("private"),

	/**
	 * 公共读
	 */
	PublicRead("public-read"),

	/**
	 * 公共读写
	 */
	PublicReadWrite("public-read-write");

	private final String cannedAclString;

	AccessControlList(String cannedAclString) {
		this.cannedAclString = cannedAclString;
	}

	public String getCannedAclString() {
		return cannedAclString;
	}

	@Override
	public String toString() {
		return "AccessControlList{" + "cannedAclString='" + cannedAclString + '\'' + "} " + super.toString();
	}

}
