package com.hengtacc.object.storage.core.domain.object;

import com.hengtacc.object.storage.core.arguments.object.DelObjectPreArguments;

/**
 * 删除对象 领域
 *
 * @author Kuang HaiBo 2023/11/9 14:25
 */
public class DelObjectDomain extends DelObjectPreArguments {

	public DelObjectDomain(String objectName) {
		super(objectName, null);
	}

	public DelObjectDomain() {
	}

}
