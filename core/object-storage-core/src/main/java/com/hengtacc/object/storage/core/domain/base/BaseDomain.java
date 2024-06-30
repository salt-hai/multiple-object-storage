package com.hengtacc.object.storage.core.domain.base;

/**
 * 基础公共属性
 *
 * @author Kuang HaiBo 2023/11/8 10:16
 */
public class BaseDomain extends BaseResponseDomain {

	/**
	 * 桶名称
	 */
	private String bucketName;

	/**
	 * 区域
	 */
	private String region;

	/**
	 * object key
	 */
	private String objectName;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public String toString() {
		return "BaseDomain{" + "bucketName='" + bucketName + '\'' + ", region='" + region + '\'' + ", objectName='"
				+ objectName + '\'' + "} " + super.toString();
	}

}
