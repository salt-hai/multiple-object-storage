package com.hengtacc.object.storage.core.arguments.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * 基础的对象 条件读取 参数
 *
 * <p>
 * 条件读取可以按照一定的规则进行对象匹配,只支持少数对象存储,
 * </p>
 *
 * @author Kuang HaiBo 2023/11/7 10:28
 */
public abstract class ObjectConditionalReadArguments extends ObjectReadArguments {

	/**
	 * offset 查找偏移量 oss 不支持
	 */
	private Long offset;

	/**
	 * 对象长度 length oss 不支持
	 */
	private Long length;

	/**
	 * 反向匹配 eTag
	 */
	private List<String> notMatchEtag = new ArrayList<>();

	/**
	 * 正向匹配 eTag
	 */
	private List<String> matchEtag = new ArrayList<>();

	/**
	 * 修改时间匹配
	 */
	private Date modifiedSince;

	/**
	 * 修改时间反向匹配
	 */
	private Date unmodifiedSince;

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public List<String> getNotMatchEtag() {
		return notMatchEtag;
	}

	public void setNotMatchEtag(List<String> notMatchEtag) {
		if (Objects.isNull(notMatchEtag) || notMatchEtag.isEmpty()) {
			return;
		}
		this.notMatchEtag = notMatchEtag;
	}

	/**
	 * 新增 反向匹配 eTag
	 * @param etag eTag
	 */
	public void addNotMatchEtag(String etag) {
		this.notMatchEtag.add(etag);
	}

	public List<String> getMatchEtag() {
		return matchEtag;
	}

	public void setMatchEtag(List<String> matchEtag) {
		if (Objects.isNull(matchEtag) || matchEtag.isEmpty()) {
			return;
		}
		this.matchEtag = matchEtag;
	}

	/**
	 * 添加 匹配的etag
	 * @param etag etag
	 */
	public void addMatchEtag(String etag) {
		this.matchEtag.add(etag);
	}

	public Date getModifiedSince() {
		return modifiedSince;
	}

	public void setModifiedSince(Date modifiedSince) {
		this.modifiedSince = modifiedSince;
	}

	public Date getUnmodifiedSince() {
		return unmodifiedSince;
	}

	public void setUnmodifiedSince(Date unmodifiedSince) {
		this.unmodifiedSince = unmodifiedSince;
	}

	@Override
	public String toString() {
		return "ObjectConditionalReadArguments{" + "offset=" + offset + ", length=" + length + ", notMatchEtag="
				+ notMatchEtag + ", matchEtag=" + matchEtag + ", modifiedSince=" + modifiedSince + ", unmodifiedSince="
				+ unmodifiedSince + "} " + super.toString();
	}

}
