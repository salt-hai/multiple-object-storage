package salthai.top.object.storage.core.arguments.object;

import cn.hutool.core.lang.Assert;
import salthai.top.object.storage.core.model.arguments.ObjectStorageArguments;

import java.util.Date;
import java.util.List;

/**
 * 拷贝对象源参数
 *
 * @author Kuang HaiBo 2023/11/9 14:55
 */
public class CopySourceObjectArguments implements ObjectStorageArguments {

	/**
	 * 来源对象桶
	 */
	private String bucketName;

	/**
	 * 来源对象名
	 */
	private String objectName;

	/**
	 * 可选版本Id，用于指定要复制的源对象的版本。如果未指定，将复制源对象的最新版本
	 */
	private String versionId;

	/**
	 * 源对象 反向匹配 eTag
	 */
	private List<String> notMatchEtag;

	/**
	 * 源对象 正向匹配 eTag
	 */
	private List<String> matchEtag;

	/**
	 * 源对象 修改时间匹配
	 */
	private Date modifiedSince;

	/**
	 * 源 对象 修改时间反向匹配
	 */
	private Date unmodifiedSince;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		Assert.notBlank(bucketName, "bucket name cant be null or empty");
		this.bucketName = bucketName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		Assert.notBlank(objectName, "object name cant be null or empty");
		this.objectName = objectName;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public List<String> getNotMatchEtag() {
		return notMatchEtag;
	}

	public void setNotMatchEtag(List<String> notMatchEtag) {
		this.notMatchEtag = notMatchEtag;
	}

	public List<String> getMatchEtag() {
		return matchEtag;
	}

	public void setMatchEtag(List<String> matchEtag) {
		this.matchEtag = matchEtag;
	}

	public Date getModifiedSince() {
		return modifiedSince;
	}

	public void setModifiedSince(Date modifiedSince) {
		this.modifiedSince = modifiedSince;
	}

	public Date getUnmodifiedSince() {
		return unmodifiedSince;
	}

	public void setUnmodifiedSince(Date unmodifiedSince) {
		this.unmodifiedSince = unmodifiedSince;
	}

	@Override
	public String toString() {
		return "CopySourceObjectArguments{" + "bucketName='" + bucketName + '\'' + ", objectName='" + objectName + '\''
				+ ", versionId='" + versionId + '\'' + ", notMatchEtag=" + notMatchEtag + ", matchEtag=" + matchEtag
				+ ", modifiedSince=" + modifiedSince + ", unmodifiedSince=" + unmodifiedSince + '}';
	}

}
