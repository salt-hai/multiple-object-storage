package salthai.top.object.storage.core.arguments.base;

/**
 * 基础的对象版本参数定义
 *
 * @author Kuang HaiBo 2023/11/7 10:29
 */
public abstract class ObjectVersionArguments extends ObjectArguments {

	/**
	 * 对象版本
	 * </p>
	 * 注意: 对于一些服务商来说并不支持相应的版本控制如:百度bos
	 */
	private String versionId;

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@Override
	public String toString() {
		return "ObjectVersionArguments{" + "versionId='" + versionId + '\'' + "} " + super.toString();
	}

}
