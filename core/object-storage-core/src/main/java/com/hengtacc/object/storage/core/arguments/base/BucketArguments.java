package com.hengtacc.object.storage.core.arguments.base;

import cn.hutool.core.lang.Assert;

/**
 * 桶请求参数
 *
 * @author Kuang HaiBo 2023/11/7 10:18
 */
public abstract class BucketArguments extends BaseArguments {

	/**
	 * 桶名称
	 */
	private String bucketName;

	/**
	 * 桶区域 可以为空 对于oss 来说无用
	 */
	private String region;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		Assert.notBlank(bucketName, "bucket name cant be null");
		this.bucketName = bucketName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "BucketArguments{" + "bucketName='" + bucketName + '\'' + ", region='" + region + '\'' + "} "
				+ super.toString();
	}

}
