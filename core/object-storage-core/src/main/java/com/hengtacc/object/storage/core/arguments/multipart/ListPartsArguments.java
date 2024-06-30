package com.hengtacc.object.storage.core.arguments.multipart;

import com.hengtacc.object.storage.core.arguments.base.BasePartArguments;

/**
 * 罗列分片上传分片信息
 *
 * @author Kuang HaiBo 2023/11/16 16:56
 */
public class ListPartsArguments extends BasePartArguments {

	/**
	 * 可选的 分片列表中要返回的最大分片数 默认 1000
	 */
	private Integer maxParts;

	/**
	 * 可选的分片号标记，指示要列出分片的结果中的位置
	 */
	private Integer partNumberMarker;

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

	@Override
	public String toString() {
		return "ListPartsArguments{" + "maxParts=" + maxParts + ", partNumberMarker=" + partNumberMarker + "} "
				+ super.toString();
	}

}
