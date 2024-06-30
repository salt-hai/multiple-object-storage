package com.hengtacc.object.storage.amazon.operations;

import com.hengtacc.object.storage.amazon.BaseS3Operations;
import com.hengtacc.object.storage.amazon.client.S3ClientPackage;
import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.DelBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;
import com.hengtacc.object.storage.core.operations.BucketOperation;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * S3 bucket 操作
 *
 * @author Kuang HaiBo 2024/1/11 11:26
 */
public class S3BucketOperations extends BaseS3Operations implements BucketOperation {

	private final static Logger log = LoggerFactory.getLogger(S3BucketOperations.class);

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public S3BucketOperations(ProviderClientManager<S3ClientPackage> clientManager) {
		super(clientManager);
	}

	/**
	 * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
	 * @param bucketName 存储桶名称
	 * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
	 */
	@Override
	public boolean doesBucketExist(String bucketName) {
		return false;
	}

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link BucketDomain} 列表
	 * @throws ObjectStorageException 列举时出现异常
	 */
	@Override
	public List<BucketDomain> listBuckets() throws ObjectStorageException {
		return null;
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
		return null;
	}

	/**
	 * 设置bucket acl
	 * @param arguments 设置参数
	 * @return ture；成功
	 * @throws ObjectStorageException 设置时出现异常
	 */
	@Override
	public boolean setBucketAcl(SetBucketAclArguments arguments) throws ObjectStorageException {
		return false;
	}

	/**
	 * 删除存储桶实例
	 * @param arguments 参数实体 {@link DelBucketArguments}
	 * @throws ObjectStorageException 删除过程异常
	 * <p>
	 */
	@Override
	public void delBucket(DelBucketArguments arguments) throws ObjectStorageException {

	}

}
