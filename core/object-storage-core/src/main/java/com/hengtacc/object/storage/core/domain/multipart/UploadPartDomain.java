package com.hengtacc.object.storage.core.domain.multipart;

import com.hengtacc.object.storage.core.domain.base.BasePartDomain;

/**
 * 分片上传 响应参数
 *
 * @author Kuang HaiBo 2023/11/16 16:17
 */
public class UploadPartDomain extends BasePartDomain {

	/**
	 * 分片大小
	 */
	private long partSize;

	public long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		this.partSize = partSize;
	}

	@Override
	public String toString() {
		return "UploadPartDomain{" + "partSize=" + partSize + "} " + super.toString();
	}

}
