package salthai.top.object.storage.amazon;

import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.core.operations.BaseOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;

/**
 * S3 操作基类
 *
 * @author Kuang HaiBo 2024/1/11 11:24
 */
public class BaseS3Operations extends BaseOperations<S3ClientPackage> {

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BaseS3Operations(ProviderClientManager<S3ClientPackage> clientManager) {
		super(clientManager);
	}

}
