package salthai.top.object.storage.core.arguments.bucket;

import cn.hutool.core.lang.Assert;
import salthai.top.object.storage.core.acl.AccessControlList;
import salthai.top.object.storage.core.arguments.base.BucketArguments;

/**
 * 设置bucket acl
 *
 * @author Kuang HaiBo 2023/11/16 10:45
 */
public class SetBucketAclArguments extends BucketArguments {

	/**
	 * bucket 的acl,
	 */
	private AccessControlList acl;

	public SetBucketAclArguments(String bucketName) {
		setBucketName(bucketName);
	}

	public SetBucketAclArguments(String bucketName, AccessControlList accessControlList) {
		setAcl(accessControlList);
		setBucketName(bucketName);
	}

	public AccessControlList getAcl() {
		return acl;
	}

	public void setAcl(AccessControlList acl) {
		Assert.notNull(acl, "acl cant be null");
		this.acl = acl;
	}

	public SetBucketAclArguments withAcl(AccessControlList acl) {
		setAcl(acl);
		return this;
	}

	@Override
	public String toString() {
		return "SetBucketAclArguments{" + "accessControlList=" + acl + "} " + super.toString();
	}

}
