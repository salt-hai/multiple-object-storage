package com.hengtacc.object.storage.aliyun;

import com.aliyun.oss.OSS;
import com.hengtacc.object.storage.core.operations.BaseOperations;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;

/**
 * 阿里云 基础 操作类
 *
 * @author Kuang HaiBo 2023/11/2 16:46
 */
public abstract class BaseOssOperations extends BaseOperations<OSS> {

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BaseOssOperations(ProviderClientManager<OSS> clientManager) {
		super(clientManager);
	}

}
