package com.hengtacc.object.storage.baidu.operations;

import com.baidubce.BceClientException;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.CreateBucketResponse;
import com.baidubce.services.bos.model.DeleteBucketRequest;
import com.baidubce.services.bos.model.ListBucketsResponse;
import com.hengtacc.object.storage.baidu.BaseBosOperations;
import com.hengtacc.object.storage.baidu.BosConstants;
import com.hengtacc.object.storage.baidu.converter.acl.BosAclConverter;
import com.hengtacc.object.storage.baidu.converter.arguments.ArgumentsToCreateBucketRequestConverter;
import com.hengtacc.object.storage.baidu.converter.domain.CreateBucketResponseToDomainConverter;
import com.hengtacc.object.storage.baidu.converter.domain.ListBucketsResponseToDomainConverter;
import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.DelBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.exceptions.BucketException;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;
import com.hengtacc.object.storage.core.operations.BucketOperation;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import com.hengtacc.object.storage.core.utils.ConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * bos 桶操作
 *
 * @author Kuang HaiBo 2023/11/20 15:47
 */
public class BosBucketOperations extends BaseBosOperations implements BucketOperation {

	private final static String LOG_PREFIX = BosConstants.LOG_PREFIX;

	private final static Logger log = LoggerFactory.getLogger(BosObjectOperations.class);

	private final static BosAclConverter ACL_CONVERTER = BosAclConverter.INSTANCE;

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public BosBucketOperations(ProviderClientManager<BosClient> clientManager) {
		super(clientManager);
	}

	/**
	 * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
	 * @param bucketName 存储桶名称
	 * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
	 */
	@Override
	public boolean doesBucketExist(String bucketName) {
		return execute(bosClient -> {
			return bosClient.doesBucketExist(bucketName);
		});
	}

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link BucketDomain} 列表
	 * @throws ObjectStorageException 列举时出现异常
	 */
	@Override
	public List<BucketDomain> listBuckets() throws ObjectStorageException {
		return execute(bosClient -> {
			try {
				ListBucketsResponse listBucketsResponse = bosClient.listBuckets();
				return ConverterUtils.toTarget(listBucketsResponse, new ListBucketsResponseToDomainConverter());
			}
			catch (BceClientException e) {
				log.error("==> {} list buckets error the cause:", LOG_PREFIX, e);
				throw new BucketException(e);
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
		return execute(bosClient -> {
			try {
				CreateBucketResponse bucket = bosClient
					.createBucket(ConverterUtils.toTarget(arguments, new ArgumentsToCreateBucketRequestConverter()));
				return ConverterUtils.toTarget(bucket, new CreateBucketResponseToDomainConverter());
			}
			catch (BceClientException e) {
				log.error("==> {}  create bucket error the cause:", LOG_PREFIX, e);
				throw new BucketException(e);
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
		return execute(bosClient -> {
			try {
				bosClient.setBucketAcl(arguments.getBucketName(), ACL_CONVERTER.convert(arguments.getAcl()));
				return true;
			}
			catch (BceClientException e) {
				log.error("==> {} set bucket acl error the cause:", LOG_PREFIX, e);
				return false;
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
		execute(bosClient -> {
			try {
				bosClient.deleteBucket(new DeleteBucketRequest(arguments.getBucketName()));
			}
			catch (BceClientException e) {
				log.error("==> {} del bucket error the cause:", LOG_PREFIX, e);
				throw new BucketException(e);
			}
		});
	}

}
