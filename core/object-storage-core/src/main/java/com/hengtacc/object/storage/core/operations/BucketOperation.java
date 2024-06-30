package com.hengtacc.object.storage.core.operations;

import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.DelBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;

import java.util.List;

/**
 * 兼容的对象桶操作
 *
 * @author Kuang HaiBo 2023/11/2 17:16
 */
public interface BucketOperation {

	/**
	 * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
	 * @param bucketName 存储桶名称
	 * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
	 */
	boolean doesBucketExist(String bucketName);

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link com.hengtacc.object.storage.core.domain.bucket.BucketDomain} 列表
	 * @throws ObjectStorageException 列举时出现异常
	 */
	List<BucketDomain> listBuckets() throws ObjectStorageException;

	/**
	 * 创建一个 bucket
	 * @param bucketName 桶名称
	 * @return 存储桶信息
	 * @throws ObjectStorageException 创建bucket时出现异常
	 */
	default BucketDomain createBucket(String bucketName) throws ObjectStorageException {
		return createBucket(new CreateBucketArguments(bucketName));
	}

	/**
	 * 创建存储桶实例
	 * <p>
	 * @param arguments 参数实体
	 * {@link com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments}
	 * @throws ObjectStorageException 创建bucket时出现异常
	 * @return 存储桶信息 {@link BucketDomain}
	 */
	BucketDomain createBucket(CreateBucketArguments arguments) throws ObjectStorageException;

	/**
	 * 设置bucket acl
	 * @param arguments 设置参数
	 * @throws ObjectStorageException 设置时出现异常
	 * @return ture；成功
	 */
	boolean setBucketAcl(SetBucketAclArguments arguments) throws ObjectStorageException;

	/**
	 * 删除存储桶实例
	 * <p>
	 * @throws ObjectStorageException 删除过程异常
	 * @param bucketName 存储桶名称
	 */
	default void delBucket(String bucketName) throws ObjectStorageException {
		DelBucketArguments delBucketArguments = new DelBucketArguments();
		delBucketArguments.setBucketName(bucketName);
		delBucket(delBucketArguments);
	}

	/**
	 * 删除存储桶实例
	 * @throws ObjectStorageException 删除过程异常
	 * <p>
	 * @param arguments 参数实体 {@link DelBucketArguments}
	 */
	void delBucket(DelBucketArguments arguments) throws ObjectStorageException;

}
