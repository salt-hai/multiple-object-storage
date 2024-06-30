package salthai.top.object.storage.core.domain.base;

import salthai.top.object.storage.core.model.domain.ObjectStorageDomain;

/**
 * 分片领域对象
 *
 * @author Kuang HaiBo 2023/11/8 10:37
 */
public class BasePartDomain implements ObjectStorageDomain {

	/**
	 * 分片序号
	 */
	private int partNumber;

	/**
	 * 新对象的 etag值
	 */
	private String etag;

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	@Override
	public String toString() {
		return "BasePartDomain{" + "partNumber=" + partNumber + ", etag='" + etag + '\'' + '}';
	}

}
