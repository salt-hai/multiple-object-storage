package com.hengtacc.object.storage.core.domain.multipart;

import com.hengtacc.object.storage.core.domain.base.MultipartUploadDomain;
import com.hengtacc.object.storage.core.domain.base.OwnerDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 罗列分片响应
 *
 * @author Kuang HaiBo 2023/11/16 16:57
 */
public class ListPartsDomain extends MultipartUploadDomain {

	/**
	 * 所有者
	 */
	private OwnerDomain owner;

	/**
	 * 初始化者
	 */
	private OwnerDomain initiator;

	/**
	 * 存储类
	 */
	private String storageClass;

	/**
	 * 最大分片数
	 */
	private Integer maxParts;

	/**
	 * 分片数标记
	 */
	private Integer partNumberMarker;

	/**
	 * 下一分片数标记
	 */
	private Integer nextPartNumberMarker;

	/**
	 * 已完成
	 */
	private boolean isTruncated;

	/**
	 * 分片数据
	 */
	private List<PartSummaryDomain> parts = new ArrayList<>();

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

	public Integer getMaxParts() {
		return maxParts;
	}

	public void setMaxParts(Integer maxParts) {
		this.maxParts = maxParts;
	}

	public Integer getPartNumberMarker() {
		return partNumberMarker;
	}

	public void setPartNumberMarker(Integer partNumberMarker) {
		this.partNumberMarker = partNumberMarker;
	}

	public Integer getNextPartNumberMarker() {
		return nextPartNumberMarker;
	}

	public void setNextPartNumberMarker(Integer nextPartNumberMarker) {
		this.nextPartNumberMarker = nextPartNumberMarker;
	}

	public Boolean getTruncated() {
		return isTruncated;
	}

	public void setTruncated(Boolean truncated) {
		isTruncated = truncated;
	}

	public List<PartSummaryDomain> getParts() {
		return parts;
	}

	public void setParts(List<PartSummaryDomain> parts) {
		if (Objects.isNull(parts) || parts.isEmpty()) {
			return;
		}
		this.parts = parts;
	}

	public void addParts(PartSummaryDomain part) {
		if (Objects.nonNull(part)) {
			this.parts.add(part);
		}
	}

	@Override
	public String toString() {
		return "ListPartsDomain{" + "owner=" + owner + ", initiator=" + initiator + ", storageClass='" + storageClass
				+ '\'' + ", maxParts=" + maxParts + ", partNumberMarker=" + partNumberMarker + ", nextPartNumberMarker="
				+ nextPartNumberMarker + ", isTruncated=" + isTruncated + ", parts=" + parts + "} " + super.toString();
	}

}
