package salthai.top.object.storage.huawei.config;

import salthai.top.object.storage.core.config.AbstractProviderProperties;

/**
 * 华为 obs
 *
 * @author Kuang HaiBo 2023/11/2 14:13
 */
public class HuaWeiObsProperties extends AbstractProviderProperties {

	/**
	 * path style 详细用途参阅 obs文档
	 */
	private Boolean pathStyle = Boolean.FALSE;

	public Boolean getPathStyle() {
		return pathStyle;
	}

	public void setPathStyle(Boolean pathStyle) {
		this.pathStyle = pathStyle;
	}

	@Override
	public String toString() {
		return "HuaWeiObsProperties{" + "pathStyle=" + pathStyle + "} " + super.toString();
	}

}
