package salthai.top.object.storage.amazon.config;

import salthai.top.object.storage.core.config.AbstractProviderProperties;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "S3Properties{" + "region='" + region + '\'' + "} " + super.toString();
	}

}
