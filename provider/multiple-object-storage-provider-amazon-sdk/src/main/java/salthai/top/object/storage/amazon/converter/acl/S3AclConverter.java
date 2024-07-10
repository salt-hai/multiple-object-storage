package salthai.top.object.storage.amazon.converter.acl;

import salthai.top.object.storage.core.acl.AccessControlList;
import salthai.top.object.storage.core.acl.AccessControlListConvert;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;

/**
 * 转为S3 Acl对象
 *
 * @author Kuang HaiBo 2024/7/10 17:08
 */
public class S3AclConverter implements AccessControlListConvert<BucketCannedACL> {

	public static final S3AclConverter INSTANCE = new S3AclConverter();

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source the source object to convert, which must be an instance of {@code S}
	 * (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public BucketCannedACL convert(AccessControlList source) {
		return BucketCannedACL.fromValue(source.getCannedAclString());
	}

}
