package salthai.top.object.storage.baidu;

import com.baidubce.services.bos.BosClient;
import salthai.top.object.storage.core.operations.BaseOperations;
import salthai.top.object.storage.core.provider.ProviderClientManager;

/**
 * 基础的百度bos操作抽象
 *
 * @author HaiBo Kuang 2023/11/10 21:59
 */
public abstract class BaseBosOperations extends BaseOperations<BosClient> {

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BaseBosOperations(ProviderClientManager<BosClient> clientManager) {
		super(clientManager);
	}

}
