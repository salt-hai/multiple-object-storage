package com.hengtacc.object.storage.core.arguments.base;

import java.util.Objects;

/**
 * 基础分片参数
 *
 * @author Kuang HaiBo 2023/11/7 10:18
 */
public abstract class BasePartArguments extends ObjectArguments {

	/**
	 * 分片上传ID
	 */
	private String uploadId;

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		Objects.requireNonNull(uploadId, "uploadId  cant be null");
		this.uploadId = uploadId;
	}

	@Override
	public String toString() {
		return "BasePartArguments{" + "uploadId='" + uploadId + '\'' + "} " + super.toString();
	}

}
