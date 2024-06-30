package com.hengtacc.object.storage.huawei;

import com.hengtacc.object.storage.core.operations.BaseOperations;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import com.obs.services.ObsClient;

/**
 * 基础的obs 操作抽象定义
 *
 * @author Kuang HaiBo 2023/11/9 15:58
 */
public abstract class BaseObsOperations extends BaseOperations<ObsClient> {

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BaseObsOperations(ProviderClientManager<ObsClient> clientManager) {
		super(clientManager);
	}

}
