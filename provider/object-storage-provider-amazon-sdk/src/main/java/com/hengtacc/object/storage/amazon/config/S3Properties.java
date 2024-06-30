package com.hengtacc.object.storage.amazon.config;

import com.hengtacc.object.storage.core.config.AbstractProviderProperties;
import software.amazon.awssdk.regions.Region;

/**
 * 存储配置属性
 *
 * @author Kuang HaiBo 2024/1/10 10:17
 */

public class S3Properties extends AbstractProviderProperties {

	/**
	 * <p>
	 * 区域
	 * </p>
	 */
	private String region = Region.CN_NORTH_1.id();

	/**
	 * S3支持virtual-hosted style URL和path style URL两种访问bucket的方式
	 */
	private Boolean pathStyleAccess = true;

	/**
	 * 如果想把该sdk作为通用协议使用需要注意: aliyun 不支持分块传输
	 * <p>
	 * 是否将数据进行分块传输
	 * </p>
	 */
	private Boolean chunkedEncoding = true;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Boolean getPathStyleAccess() {
		return pathStyleAccess;
	}

	public void setPathStyleAccess(Boolean pathStyleAccess) {
		this.pathStyleAccess = pathStyleAccess;
	}

	public Boolean getChunkedEncoding() {
		return chunkedEncoding;
	}

	public void setChunkedEncoding(Boolean chunkedEncoding) {
		this.chunkedEncoding = chunkedEncoding;
	}

	@Override
	public String toString() {
		return "S3Properties{" + "region='" + region + '\'' + ", pathStyleAccess=" + pathStyleAccess
				+ ", chunkedEncoding=" + chunkedEncoding + "} " + super.toString();
	}

}
