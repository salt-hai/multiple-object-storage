package com.hengtacc.object.storage.core.domain.bucket;

import com.hengtacc.object.storage.core.domain.base.OwnerDomain;
import com.hengtacc.object.storage.core.model.domain.ObjectStorageDomain;

import java.util.Date;

/**
 * 桶
 *
 * @author Kuang HaiBo 2023/11/8 10:46
 */
public class BucketDomain implements ObjectStorageDomain {

	/**
	 * 存储桶名称
	 */
	private String bucketName;

	/**
	 * 存储桶所有者信息
	 */
	private OwnerDomain ownerAttribute;

	/**
	 * 存储桶创建时间
	 */
	private Date creationDate;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public OwnerDomain getOwnerAttribute() {
		return ownerAttribute;
	}

	public void setOwnerAttribute(OwnerDomain ownerAttribute) {
		this.ownerAttribute = ownerAttribute;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "BucketDomain{" + "bucketName='" + bucketName + '\'' + ", ownerAttribute=" + ownerAttribute
				+ ", creationDate=" + creationDate + '}';
	}

}
