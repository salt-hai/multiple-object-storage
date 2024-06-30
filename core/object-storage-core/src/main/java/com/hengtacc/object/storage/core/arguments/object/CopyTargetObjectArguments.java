package com.hengtacc.object.storage.core.arguments.object;

import cn.hutool.core.lang.Assert;
import com.hengtacc.object.storage.core.model.arguments.ObjectStorageArguments;

import java.util.Map;

/**
 * 拷贝- 目标对象参数
 *
 * @author Kuang HaiBo 2023/11/9 15:38
 */
public class CopyTargetObjectArguments implements ObjectStorageArguments {

	/**
	 * 目标桶名
	 */
	private String bucketName;

	/**
	 * 目标对象名称
	 */
	private String objectName;

	/**
	 * 目标服务器的加密算法。
	 */
	private String serverSideEncryption;

	/**
	 * 目标服务器的加密密钥ID。
	 */
	private String serverSideEncryptionKeyID;

	/**
	 * Target object's metadata information. 目标对象元数据
	 */
	private Map<String, String> objectMetadata;

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

	public String getServerSideEncryption() {
		return serverSideEncryption;
	}

	public void setServerSideEncryption(String serverSideEncryption) {
		this.serverSideEncryption = serverSideEncryption;
	}

	public String getServerSideEncryptionKeyID() {
		return serverSideEncryptionKeyID;
	}

	public void setServerSideEncryptionKeyID(String serverSideEncryptionKeyID) {
		this.serverSideEncryptionKeyID = serverSideEncryptionKeyID;
	}

	public Map<String, String> getObjectMetadata() {
		return objectMetadata;
	}

	public void setObjectMetadata(Map<String, String> objectMetadata) {
		this.objectMetadata = objectMetadata;
	}

	@Override
	public String toString() {
		return "CopyTargetObjectArguments{" + "bucketName='" + bucketName + '\'' + ", objectName='" + objectName + '\''
				+ ", serverSideEncryption='" + serverSideEncryption + '\'' + ", serverSideEncryptionKeyID='"
				+ serverSideEncryptionKeyID + '\'' + ", objectMetadata=" + objectMetadata + '}';
	}

}
