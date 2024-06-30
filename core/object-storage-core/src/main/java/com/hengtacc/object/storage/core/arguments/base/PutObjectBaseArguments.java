package com.hengtacc.object.storage.core.arguments.base;

import com.hengtacc.object.storage.core.constants.StorageConstants;

/**
 * 执行 put object 的基础参数
 *
 * <p>
 * 该对象并不会直接使用,作为一个中间类,用于子类继承, 所以对于 objectSize：普通上传不要超过5GB partSize 只有分片才会用到
 * </p>
 *
 * @author Kuang HaiBo 2023/11/7 10:52
 */
public abstract class PutObjectBaseArguments extends ObjectWriteArguments {

	/**
	 * 对于普通文件 请不要超过5GB,大文件用分片上传, 该大小尽量指定
	 */
	protected long objectSize;

	/**
	 * 分片上传时 请指定分片大小, 对于某些如minio的sdk 是支持自动分片的,所以这个作为冗余参数放在这里
	 */
	protected long partSize;

	/**
	 * 媒体类型 默认是 application/octet-stream
	 */
	protected String contentType = StorageConstants.DEFAULT_MINE_TYPE;

	public long getObjectSize() {
		return objectSize;
	}

	public void setObjectSize(long objectSize) {
		this.objectSize = objectSize;
	}

	public long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		this.partSize = partSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "PutObjectBaseArguments{" + "objectSize=" + objectSize + ", partSize=" + partSize + ", contentType='"
				+ contentType + '\'' + "} " + super.toString();
	}

}
