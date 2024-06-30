package com.hengtacc.object.storage.core.domain.multipart;

import com.hengtacc.object.storage.core.domain.base.OwnerDomain;
import com.hengtacc.object.storage.core.model.domain.ObjectStorageDomain;

import java.util.Date;

/**
 * 分片上传列表返回条目域对象
 *
 * @author Kuang HaiBo 2023/11/16 17:06
 */
public class UploadDomain implements ObjectStorageDomain {

	/**
	 * 存储此upload的密钥
	 */
	private String key;

	/**
	 * 此分片上传的唯一ID
	 */
	private String uploadId;

	/**
	 * 此分片上传的拥有者 ,oss 获取不到
	 */
	private OwnerDomain owner;

	/**
	 * 此分片上传的发起者 ,oss获取不到
	 */
	private OwnerDomain initiator;

	/**
	 * 存储类，指示如何存储此分片上传中的数据.
	 */
	private String storageClass;

	/**
	 * 启动此分片上传的时间
	 */
	private Date initiated;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public OwnerDomain getOwner() {
		return owner;
	}

	public void setOwner(OwnerDomain owner) {
		this.owner = owner;
	}

	public OwnerDomain getInitiator() {
		return initiator;
	}

	public void setInitiator(OwnerDomain initiator) {
		this.initiator = initiator;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}

	public Date getInitiated() {
		return initiated;
	}

	public void setInitiated(Date initiated) {
		this.initiated = initiated;
	}

	@Override
	public String toString() {
		return "UploadDomain{" + "key='" + key + '\'' + ", uploadId='" + uploadId + '\'' + ", owner=" + owner
				+ ", initiator=" + initiator + ", storageClass='" + storageClass + '\'' + ", initiated=" + initiated
				+ '}';
	}

}
