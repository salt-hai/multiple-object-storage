package com.hengtacc.object.storage.aliyun.config;

import com.hengtacc.object.storage.core.config.AbstractProviderProperties;

/**
 * 阿里云配置文件
 *
 * @author Kuang HaiBo 2023/11/2 11:31
 */
public class AliYunOssProperties extends AbstractProviderProperties {

	/**
	 * 使用临时授权访问时, region stsRoleArn 必须填写
	 */
	private Boolean enableTempAccess = Boolean.FALSE;

	/**
	 * 授权STSAssumeRole访问的Region。以华东1（杭州）为例，其它Region请根据实际情况填写。
	 */
	private String region;

	/**
	 * AM角色的RamRoleArn
	 */
	private String stsRoleArn;

	public Boolean getEnableTempAccess() {
		return enableTempAccess;
	}

	public void setEnableTempAccess(Boolean enableTempAccess) {
		this.enableTempAccess = enableTempAccess;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStsRoleArn() {
		return stsRoleArn;
	}

	public void setStsRoleArn(String stsRoleArn) {
		this.stsRoleArn = stsRoleArn;
	}

	@Override
	public String toString() {
		return "AliYunProperties{" + "enableTempAccess=" + enableTempAccess + ", region='" + region + '\''
				+ ", stsRoleArn='" + stsRoleArn + '\'' + "} " + super.toString();
	}

}
