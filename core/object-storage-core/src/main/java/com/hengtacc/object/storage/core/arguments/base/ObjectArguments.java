package com.hengtacc.object.storage.core.arguments.base;

import cn.hutool.core.lang.Assert;

/**
 * 对象参数
 *
 * @author Kuang HaiBo 2023/11/7 10:26
 */
public abstract class ObjectArguments extends BucketArguments {

	/**
	 * 对象key 对象名
	 */
	private String objectName;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		Assert.notBlank(objectName, "object name cant be null");
		this.objectName = objectName;
	}

	@Override
	public String toString() {
		return "ObjectArguments{" + "objectName='" + objectName + '\'' + "} " + super.toString();
	}

}
