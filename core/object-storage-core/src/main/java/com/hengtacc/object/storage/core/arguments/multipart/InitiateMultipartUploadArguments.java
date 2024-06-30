package com.hengtacc.object.storage.core.arguments.multipart;

import com.hengtacc.object.storage.core.arguments.base.ObjectArguments;
import com.hengtacc.object.storage.core.constants.StorageConstants;

import java.util.Map;

/**
 * 初始化分片请求
 *
 * @author Kuang HaiBo 2023/11/16 16:05
 */
public class InitiateMultipartUploadArguments extends ObjectArguments {

	/**
	 * 媒体类型 默认是 application/octet-stream
	 */
	protected String contentType = StorageConstants.DEFAULT_MINE_TYPE;

	/**
	 * 元数据可以为空 元数据
	 */
	private Map<String, String> metadata;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "InitiateMultipartUploadArguments{" + "contentType='" + contentType + '\'' + ", metadata=" + metadata
				+ "} " + super.toString();
	}

}
