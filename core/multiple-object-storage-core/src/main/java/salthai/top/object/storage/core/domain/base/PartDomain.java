package salthai.top.object.storage.core.domain.base;

import java.util.Date;

/**
 * 分片 领域
 *
 * @author Kuang HaiBo 2023/11/8 10:41
 */
public class PartDomain extends BasePartDomain {

	/**
	 * 此分片的大小，以字节为单位
	 */
	private long partSize;

	/**
	 * 新对象的上次修改日期
	 */
	private Date lastModifiedDate;

	public long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		this.partSize = partSize;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "PartDomain{" + "partSize=" + partSize + ", lastModifiedDate=" + lastModifiedDate + "} "
				+ super.toString();
	}

}
