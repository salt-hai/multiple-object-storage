package salthai.top.object.storage.core.arguments.object;

import cn.hutool.core.lang.Assert;
import jakarta.validation.constraints.NotBlank;
import salthai.top.object.storage.core.model.arguments.ObjectStorageArguments;

/**
 * 预删除参数
 *
 * @author Kuang HaiBo 2023/11/8 15:57
 */
public class DelObjectPreArguments implements ObjectStorageArguments {

	public DelObjectPreArguments(String objectName, String versionId) {
		setObjectName(objectName);
		this.versionId = versionId;
	}

	public DelObjectPreArguments() {
	}

	/**
	 * 对象名
	 */
	@NotBlank(message = "对象名称不允许为空")
	private String objectName;

	/**
	 * 版本号， bos 不支持，oss 不支持同一个批次不同的version id
	 */
	private String versionId;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		Assert.notBlank(objectName, "object name cant be null or empty");
		this.objectName = objectName;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@Override
	public String toString() {
		return "DelObjectPreArguments{" + "objectName='" + objectName + '\'' + ", versionId='" + versionId + '\'' + '}';
	}

}
