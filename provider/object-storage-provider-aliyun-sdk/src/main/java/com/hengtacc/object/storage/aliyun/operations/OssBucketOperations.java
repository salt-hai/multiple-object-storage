package com.hengtacc.object.storage.aliyun.operations;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GenericRequest;
import com.hengtacc.object.storage.aliyun.BaseOssOperations;
import com.hengtacc.object.storage.aliyun.OssConstants;
import com.hengtacc.object.storage.aliyun.converter.BucketArgumentsToGenericRequestConverter;
import com.hengtacc.object.storage.aliyun.converter.arguments.ArgumentsToCreateBucketRequestConverter;
import com.hengtacc.object.storage.aliyun.converter.arguments.ArgumentsToSetBucketAclRequestConverter;
import com.hengtacc.object.storage.aliyun.converter.domain.BucketToBucketDomainConverter;
import com.hengtacc.object.storage.core.arguments.bucket.CreateBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.DelBucketArguments;
import com.hengtacc.object.storage.core.arguments.bucket.SetBucketAclArguments;
import com.hengtacc.object.storage.core.domain.bucket.BucketDomain;
import com.hengtacc.object.storage.core.exceptions.BucketException;
import com.hengtacc.object.storage.core.exceptions.ObjectStorageException;
import com.hengtacc.object.storage.core.function.Converter;
import com.hengtacc.object.storage.core.operations.BucketOperation;
import com.hengtacc.object.storage.core.provider.ProviderClientManager;
import com.hengtacc.object.storage.core.utils.ConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * oss 桶操作
 *
 * @author Kuang HaiBo 2023/11/2 17:19
 */
public class OssBucketOperations extends BaseOssOperations implements BucketOperation {

	private final static Logger log = LoggerFactory.getLogger(OssBucketOperations.class);

	private static final String LOG_PREFIX = OssConstants.LOG_PREFIX;

	/**
	 * 以客户端管理器构建
	 * @param clientManager 客户端管理器
	 */
	public OssBucketOperations(ProviderClientManager<OSS> clientManager) {
		super(clientManager);
	}

	/**
	 * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
	 * @param bucketName 存储桶名称
	 * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
	 */
	@Override
	public boolean doesBucketExist(String bucketName) {
		return execute(oss -> {
			return oss.doesBucketExist(bucketName);
		});
	}

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link BucketDomain} 列表
	 */
	@Override
	public List<BucketDomain> listBuckets() throws ObjectStorageException {
		return execute(oss -> {
			try {
				List<Bucket> buckets = oss.listBuckets();
				return ConverterUtils.toTargets(buckets, new BucketToBucketDomainConverter());
			}
			catch (OSSException | ClientException e) {
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
	 */
	@Override
	public BucketDomain createBucket(CreateBucketArguments arguments) throws ObjectStorageException {
		return execute(oss -> {
			Converter<CreateBucketArguments, CreateBucketRequest> toReq = new ArgumentsToCreateBucketRequestConverter();
			try {
				Bucket bucket = oss.createBucket(toReq.convert(arguments));
				Converter<Bucket, BucketDomain> bucketToDomain = new BucketToBucketDomainConverter();
				return bucketToDomain.convert(bucket);
			}
			catch (OSSException | ClientException e) {
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
		return execute(oss -> {
			ArgumentsToSetBucketAclRequestConverter toReq = new ArgumentsToSetBucketAclRequestConverter();
			try {
				oss.setBucketAcl(toReq.convert(arguments));
				return true;
			}
			catch (OSSException | ClientException e) {
				throw new BucketException("set bucket acl  error the cause:", e);
			}
		});
	}

	/**
	 * 删除存储桶实例
	 * <p>
	 * @param arguments 参数实体 {@link DelBucketArguments}
	 */
	@Override
	public void delBucket(DelBucketArguments arguments) throws ObjectStorageException {
		execute(oss -> {
			Converter<DelBucketArguments, GenericRequest> toReq = new BucketArgumentsToGenericRequestConverter<>();
			try {
				oss.deleteBucket(toReq.convert(arguments));
			}
			catch (OSSException | ClientException e) {
				log.error("==> {} del bucket error the cause:", LOG_PREFIX, e);
				throw new BucketException("del bucket error the cause:", e);
			}
		});
	}

}
