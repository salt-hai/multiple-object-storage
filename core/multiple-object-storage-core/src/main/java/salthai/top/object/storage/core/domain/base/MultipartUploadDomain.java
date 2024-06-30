package salthai.top.object.storage.core.domain.base;

/**
 * 分片上传 领域对象
 *
 * @author Kuang HaiBo 2023/11/8 10:38
 */
public class MultipartUploadDomain extends BaseDomain {

	/**
	 * 上传ID
	 */

	private String uploadId;

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	@Override
	public String toString() {
		return "MultipartUploadDomain{" + "uploadId='" + uploadId + '\'' + "} " + super.toString();
	}

}
