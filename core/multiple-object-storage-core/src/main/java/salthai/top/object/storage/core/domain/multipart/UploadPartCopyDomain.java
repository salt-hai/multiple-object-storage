package salthai.top.object.storage.core.domain.multipart;

import salthai.top.object.storage.core.domain.base.BasePartDomain;

/**
 * 上传分片拷贝 响应
 *
 * @author Kuang HaiBo 2023/11/16 16:22
 */
public class UploadPartCopyDomain extends BasePartDomain {

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
		return "UploadPartCopyDomain{" + "partSize=" + partSize + "} " + super.toString();
	}

}
