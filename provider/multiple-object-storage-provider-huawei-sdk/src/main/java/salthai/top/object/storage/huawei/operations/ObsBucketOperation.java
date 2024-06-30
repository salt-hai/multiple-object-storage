package salthai.top.object.storage.huawei.operations;

import salthai.top.object.storage.core.arguments.bucket.CreateBucketArguments;
import salthai.top.object.storage.core.arguments.bucket.DelBucketArguments;
import salthai.top.object.storage.core.arguments.bucket.SetBucketAclArguments;
import salthai.top.object.storage.core.domain.bucket.BucketDomain;
import salthai.top.object.storage.core.exceptions.BucketException;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;
import salthai.top.object.storage.core.operations.BucketOperation;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import salthai.top.object.storage.huawei.BaseObsOperations;
import salthai.top.object.storage.huawei.ObsConstants;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToCreateBucketRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToDeleteBucketRequestConverter;
import salthai.top.object.storage.huawei.converter.arguments.ArgumentToSetBucketAclRequestConverter;
import salthai.top.object.storage.huawei.converter.domain.ObsBucketToDomainConverter;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ListBucketsRequest;
import com.obs.services.model.ObsBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * obs 兼容的存储桶操作
 *
 * @author Kuang HaiBo 2023/12/18 16:31
 */
public class ObsBucketOperation extends BaseObsOperations implements BucketOperation {

	private final static Logger log = LoggerFactory.getLogger(ObsBucketOperation.class);

	private static final String LOG_PREFIX = ObsConstants.LOG_PREFIX;

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public ObsBucketOperation(ProviderClientManager<ObsClient> clientManager) {
		super(clientManager);
	}

	/**
	 * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
	 * @param bucketName 存储桶名称
	 * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
	 */
	@Override
	public boolean doesBucketExist(String bucketName) {
		return execute(obsClient -> {
			return obsClient.headBucket(bucketName);
		});
	}

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link BucketDomain} 列表
	 * @throws ObjectStorageException 列举时出现异常
	 */
	@Override
	public List<BucketDomain> listBuckets() throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				List<ObsBucket> obsBuckets = obsClient.listBuckets(new ListBucketsRequest());
				return ConverterUtils.toTargets(obsBuckets, new ObsBucketToDomainConverter());
			}
			catch (ObsException e) {
				log.error("==> {} list buckets error the cause: ", LOG_PREFIX, e);
				throw new BucketException("list buckets error the cause:", e);
			}
		});
	}

	/**
	 * 创建存储桶实例
	 * <p>
	 * @param arguments 参数实体 {@link CreateBucketArguments}
	 * @return 存储桶信息 {@link BucketDomain}
	 * @throws ObjectStorageException 创建bucket时出现异常
	 */
	@Override
	public BucketDomain createBucket(CreateBucketArguments arguments) throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				ObsBucket bucket = obsClient
					.createBucket(ConverterUtils.toTarget(arguments, new ArgumentToCreateBucketRequestConverter()));
				return ConverterUtils.toTarget(bucket, new ObsBucketToDomainConverter());
			}
			catch (ObsException e) {
				log.error("==> {} create bucket error the cause: ", LOG_PREFIX, e);
				throw new BucketException("create bucket error the cause:", e);
			}
		});
	}

	/**
	 * 设置bucket acl
	 * @param arguments 设置参数
	 * @return ture；成功
	 * @throws ObjectStorageException 设置时出现异常
	 */
	@Override
	public boolean setBucketAcl(SetBucketAclArguments arguments) throws ObjectStorageException {
		return execute(obsClient -> {
			try {
				obsClient
					.setBucketAcl(ConverterUtils.toTarget(arguments, new ArgumentToSetBucketAclRequestConverter()));
				return true;
			}
			catch (ObsException e) {
				log.error("==> {} set bucket acl  error the cause:", LOG_PREFIX, e);
				throw new BucketException("set bucket acl  error the cause:", e);
			}
		});
	}

	/**
	 * 删除存储桶实例
	 * @param arguments 参数实体 {@link DelBucketArguments}
	 * @throws ObjectStorageException 删除过程异常
	 * <p>
	 */
	@Override
	public void delBucket(DelBucketArguments arguments) throws ObjectStorageException {
		execute(obsClient -> {
			try {
				obsClient
					.deleteBucket(ConverterUtils.toTarget(arguments, new ArgumentToDeleteBucketRequestConverter()));
			}
			catch (ObsException e) {
				log.error("==> {} del bucket error the cause:", LOG_PREFIX, e);
				throw new BucketException("del bucket error the cause:", e);
			}
		});
	}

}
