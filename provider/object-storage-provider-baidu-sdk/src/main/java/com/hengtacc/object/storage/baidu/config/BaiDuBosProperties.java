package com.hengtacc.object.storage.baidu.config;

import com.hengtacc.object.storage.core.config.AbstractProviderProperties;

/**
 * 百度 bos 配置
 *
 * @author HaiBo Kuang 2023/11/10 21:18
 */
public class BaiDuBosProperties extends AbstractProviderProperties {

	/**
	 * 部分非BOS常规域名下，默认bucket virtual hosting风格的可能会有不兼容场景，表现为dns解析失败等，可以开启PathStyle解决。
	 * 其他场景建议使用默认风格。
	 */
	private Boolean pathStyleAccessEnable = Boolean.FALSE;

	/**
	 * 设置PUT操作同步or异步，默认异步
	 */
	private Boolean enableHttpAsyncPut = Boolean.TRUE;

	public Boolean getPathStyleAccessEnable() {
		return pathStyleAccessEnable;
	}

	public void setPathStyleAccessEnable(Boolean pathStyleAccessEnable) {
		this.pathStyleAccessEnable = pathStyleAccessEnable;
	}

	public Boolean getEnableHttpAsyncPut() {
		return enableHttpAsyncPut;
	}

	public void setEnableHttpAsyncPut(Boolean enableHttpAsyncPut) {
		this.enableHttpAsyncPut = enableHttpAsyncPut;
	}

	@Override
	public String toString() {
		return "BaiDuProperties{" + "pathStyleAccessEnable=" + pathStyleAccessEnable + ", enableHttpAsyncPut="
				+ enableHttpAsyncPut + "} " + super.toString();
	}

}
