package com.hengtacc.object.storage.core.domain.object;

import com.hengtacc.object.storage.core.domain.base.BaseResponseDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 对象列表
 *
 * @author Kuang HaiBo 2023/11/8 10:44
 */
public class ListObjectsDomain extends BaseResponseDomain {

	/**
	 * 桶名称
	 */
	private String bucketName;

	/**
	 * 区域
	 */
	private String region;

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * 关键字
	 */
	private String marker;

	/**
	 * 分隔符 默认 ""
	 */
	private String delimiter = "";

	/**
	 * 最多返回多少 默认最多1000
	 */
	private Integer maxKeys;

	/**
	 * 编码类型
	 */
	private String encodingType;

	/**
	 * 对象列表
	 */
	private final List<ObjectDomain> summaries = new ArrayList<>();

	/**
	 * 子目录
	 */
	private final List<String> commonPrefixes = new ArrayList<>();

	/**
	 * 指示这是否是一个完整的列表，
	 */
	private boolean isTruncated;

	/**
	 * 获取要在下一个 listObjects 请求中使用的标记，以便查看下一页结果。 如果对象列表未被截断(isTruncated=false)，则此方法将返回
	 * null。对于截断的请求，此值等于此列表中包含的对象键和通用前缀的最大字典值。
	 *
	 */
	private String nextMarker;

	public List<ObjectDomain> getSummaries() {
		return summaries;
	}

	/**
	 * 新增对象
	 * @param summary 对象
	 */
	public void addSummary(ObjectDomain summary) {
		if (Objects.nonNull(summary)) {
			this.summaries.add(summary);
		}
	}

	public void setSummaries(List<ObjectDomain> summaries) {
		this.summaries.clear();
		if (summaries != null && !summaries.isEmpty()) {
			this.summaries.addAll(summaries);
		}
	}

	public void addCommonPrefix(String commonPrefix) {
		this.commonPrefixes.add(commonPrefix);
	}

	public void setCommonPrefixes(List<String> commonPrefixes) {
		this.commonPrefixes.clear();
		if (commonPrefixes != null && !commonPrefixes.isEmpty()) {
			this.commonPrefixes.addAll(commonPrefixes);
		}
	}

	public boolean isTruncated() {
		return isTruncated;
	}

	public void setTruncated(boolean truncated) {
		isTruncated = truncated;
	}

	public String getNextMarker() {
		return nextMarker;
	}

	public void setNextMarker(String nextMarker) {
		this.nextMarker = nextMarker;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Integer getMaxKeys() {
		return maxKeys;
	}

	public void setMaxKeys(Integer maxKeys) {
		this.maxKeys = maxKeys;
	}

	public String getEncodingType() {
		return encodingType;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	public List<String> getCommonPrefixes() {
		return commonPrefixes;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "ListObjectsDomain{" + "bucketName='" + bucketName + '\'' + ", region='" + region + '\'' + ", prefix='"
				+ prefix + '\'' + ", marker='" + marker + '\'' + ", delimiter='" + delimiter + '\'' + ", maxKeys="
				+ maxKeys + ", encodingType='" + encodingType + '\'' + ", summaries=" + summaries + ", commonPrefixes="
				+ commonPrefixes + ", isTruncated=" + isTruncated + ", nextMarker='" + nextMarker + '\'' + "} "
				+ super.toString();
	}

}
