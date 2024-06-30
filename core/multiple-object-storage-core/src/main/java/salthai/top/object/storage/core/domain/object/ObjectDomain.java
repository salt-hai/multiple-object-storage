package salthai.top.object.storage.core.domain.object;

import salthai.top.object.storage.core.domain.bucket.BucketDomain;

import java.util.Date;

/**
 * 对象领域
 *
 * @author Kuang HaiBo 2023/11/8 10:45
 */
public class ObjectDomain extends BucketDomain {

	/**
	 * 存储此对象的密钥 objectKey
	 */
	private String objectName;

	/**
	 * ETag。此对象内容的十六进制编码MD5哈希
	 */
	private String eTag;

	/**
	 * 此对象的大小，以字节为单位
	 */
	private Long size;

	/**
	 * 对象最后一次被修改的日期
	 */
	private Date lastModified;

	/**
	 * 存储此对象的存储类
	 */
	private String storageClass;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getETag() {
		return eTag;
	}

	public void setETag(String eTag) {
		this.eTag = eTag;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}

	@Override
	public String toString() {
		return "ObjectDomain{" + "objectName='" + objectName + '\'' + ", eTag='" + eTag + '\'' + ", size=" + size
				+ ", lastModified=" + lastModified + ", storageClass='" + storageClass + '\'' + "} " + super.toString();
	}

}
