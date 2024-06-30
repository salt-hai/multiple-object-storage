package com.hengtacc.object.storage.core.domain.multipart;

import com.hengtacc.object.storage.core.model.domain.ObjectStorageDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 列出正在进行的分片上传响应值
 *
 * @author Kuang HaiBo 2023/11/16 17:04
 */
public class ListMultipartUploadsDomain implements ObjectStorageDomain {

	/**
	 * 桶名
	 */
	private String bucketName;

	/**
	 * 分隔符
	 */
	private String delimiter;

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * 要返回的最大上传次数
	 */
	private Integer maxUploads;

	/**
	 * 指示结果中开始列出的位置的关键标记.
	 * <p>
	 * 与上传ID标记一起，指定列表开始后的多部分上传.
	 * <p>
	 * 如果未指定 uploadId 标记，则列表中只会包括按字典顺序大于指定对象标记的对象.
	 * <p>
	 * 如果指定了 uploadId 标记，则对于等于对象标记的密钥的任何多部分上传也可以包括在内，前提是这些分片上传的uploadIdD在字典上大于指定的标记.
	 */
	private String keyMarker;

	/**
	 * 指示结果中开始列出的位置的上载ID标记
	 */
	private String uploadIdMarker;

	/**
	 * 已完成
	 */
	private boolean isTruncated;

	/**
	 * 下一个key标记
	 */
	private String nextKeyMarker;

	/**
	 * 下一个上传 key标识
	 */
	private String nextUploadIdMarker;

	/**
	 * 正在进行的分片上传对象
	 */
	private List<UploadDomain> multipartUploads = new ArrayList<>();

	/**
	 * 通用前缀
	 */
	private List<String> commonPrefixes = new ArrayList<>();

	public boolean isTruncated() {
		return isTruncated;
	}

	public void setTruncated(boolean truncated) {
		isTruncated = truncated;
	}

	public String getNextKeyMarker() {
		return nextKeyMarker;
	}

	public void setNextKeyMarker(String nextKeyMarker) {
		this.nextKeyMarker = nextKeyMarker;
	}

	public String getNextUploadIdMarker() {
		return nextUploadIdMarker;
	}

	public void setNextUploadIdMarker(String nextUploadIdMarker) {
		this.nextUploadIdMarker = nextUploadIdMarker;
	}

	public List<UploadDomain> getMultipartUploads() {
		return multipartUploads;
	}

	public void setMultipartUploads(List<UploadDomain> multipartUploads) {
		if (Objects.isNull(multipartUploads) || multipartUploads.isEmpty()) {
			return;
		}
		this.multipartUploads = multipartUploads;
	}

	public List<String> getCommonPrefixes() {
		return commonPrefixes;
	}

	public void setCommonPrefixes(List<String> commonPrefixes) {
		if (Objects.isNull(commonPrefixes) || commonPrefixes.isEmpty()) {
			return;
		}
		this.commonPrefixes = commonPrefixes;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getMaxUploads() {
		return maxUploads;
	}

	public void setMaxUploads(Integer maxUploads) {
		this.maxUploads = maxUploads;
	}

	public String getKeyMarker() {
		return keyMarker;
	}

	public void setKeyMarker(String keyMarker) {
		this.keyMarker = keyMarker;
	}

	public String getUploadIdMarker() {
		return uploadIdMarker;
	}

	public void setUploadIdMarker(String uploadIdMarker) {
		this.uploadIdMarker = uploadIdMarker;
	}

	@Override
	public String toString() {
		return "ListMultipartUploadsDomain{" + "bucketName='" + bucketName + '\'' + ", delimiter='" + delimiter + '\''
				+ ", prefix='" + prefix + '\'' + ", maxUploads=" + maxUploads + ", keyMarker='" + keyMarker + '\''
				+ ", uploadIdMarker='" + uploadIdMarker + '\'' + ", isTruncated=" + isTruncated + ", nextKeyMarker='"
				+ nextKeyMarker + '\'' + ", nextUploadIdMarker='" + nextUploadIdMarker + '\'' + ", multipartUploads="
				+ multipartUploads + ", commonPrefixes=" + commonPrefixes + '}';
	}

}
