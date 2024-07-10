package salthai.top.object.storage.amazon.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salthai.top.object.storage.amazon.BaseS3Operations;
import salthai.top.object.storage.amazon.S3Constants;
import salthai.top.object.storage.amazon.client.S3ClientPackage;
import salthai.top.object.storage.amazon.converter.argument.CreateBucketArgumentsToCreateBucketRequestConverter;
import salthai.top.object.storage.amazon.converter.argument.DelBucketArgumentsToDeleteBucketRequestConverter;
import salthai.top.object.storage.amazon.converter.argument.SetBucketAclArgumentsToPutBucketAclRequestConverter;
import salthai.top.object.storage.amazon.converter.domain.BucketToBucketDomainConverter;
import salthai.top.object.storage.amazon.converter.domain.OwnerToOwnerDomainConverter;
import salthai.top.object.storage.core.arguments.bucket.CreateBucketArguments;
import salthai.top.object.storage.core.arguments.bucket.DelBucketArguments;
import salthai.top.object.storage.core.arguments.bucket.SetBucketAclArguments;
import salthai.top.object.storage.core.domain.base.OwnerDomain;
import salthai.top.object.storage.core.domain.bucket.BucketDomain;
import salthai.top.object.storage.core.exceptions.BucketException;
import salthai.top.object.storage.core.exceptions.ObjectStorageException;
import salthai.top.object.storage.core.operations.BucketOperation;
import salthai.top.object.storage.core.provider.ProviderClientManager;
import salthai.top.object.storage.core.utils.ConverterUtils;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * S3 bucket 操作
 *
 * @author Kuang HaiBo 2024/1/11 11:26
 */
public class S3BucketOperations extends BaseS3Operations implements BucketOperation {

	private final static Logger log = LoggerFactory.getLogger(S3BucketOperations.class);

	private static final String LOG_PREFIX = S3Constants.LOG_PREFIX;

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
		return listBuckets().stream().map(BucketDomain::getBucketName).collect(Collectors.toSet()).contains(bucketName);
	}

	/**
	 * 返回当前帐户的所有存储桶实例列表
	 * @return 存储桶 {@link BucketDomain} 列表
	 * @throws ObjectStorageException 列举时出现异常
	 */
	@Override
	public List<BucketDomain> listBuckets() throws ObjectStorageException {
		return execute(s3ClientPackage -> {
			try {
				ListBucketsResponse listBucketsResponse = s3ClientPackage.getS3Client().listBuckets();
				if (!listBucketsResponse.hasBuckets() || listBucketsResponse.buckets().isEmpty()) {
					return new ArrayList<>();
				}
				Owner owner = listBucketsResponse.owner();
				List<Bucket> buckets = listBucketsResponse.buckets();

				OwnerDomain ownerDomain = ConverterUtils.toTarget(owner, new OwnerToOwnerDomainConverter());
				List<BucketDomain> bucketDomains = ConverterUtils.toTargets(buckets,
						new BucketToBucketDomainConverter());
				for (BucketDomain target : bucketDomains) {
					target.setOwnerAttribute(ownerDomain);
				}
				return bucketDomains;
			}
			catch (S3Exception e) {
				log.error("==> {} list buckets error the cause:", LOG_PREFIX, e);
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
		return execute(s3ClientPackage -> {
			try {
				s3ClientPackage.getS3Client()
					.createBucket(ConverterUtils.toTarget(arguments,
							new CreateBucketArgumentsToCreateBucketRequestConverter()));
				BucketDomain bucketDomain = new BucketDomain();
				// response not have the other field,
				bucketDomain.setBucketName(arguments.getBucketName());
				return bucketDomain;
			}
			catch (S3Exception e) {
				log.error("==> {} create bucket error the cause:", LOG_PREFIX, e);
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
		return execute(s3ClientPackage -> {
			try {
				s3ClientPackage.getS3Client()
					.putBucketAcl(ConverterUtils.toTarget(arguments,
							new SetBucketAclArgumentsToPutBucketAclRequestConverter()));
				return true;
			}
			catch (S3Exception e) {
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
		execute(s3ClientPackage -> {
			try {
				s3ClientPackage.getS3Client()
					.deleteBucket(
							ConverterUtils.toTarget(arguments, new DelBucketArgumentsToDeleteBucketRequestConverter()));
			}
			catch (S3Exception e) {
				log.error("==> {} del bucket error the cause:", LOG_PREFIX, e);
				throw new BucketException("del bucket error the cause:", e);
			}
		});
	}

}
