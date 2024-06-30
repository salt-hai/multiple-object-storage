package salthai.top.object.storage.core.arguments.multipart;

import salthai.top.object.storage.core.arguments.base.BucketArguments;

/**
 * 列出正在进行的分片上传
 *
 * @author Kuang HaiBo 2023/11/16 16:59
 */
public class ListMultipartUploadsArguments extends BucketArguments {

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
	 * 可选参数，指示要应用于响应的编码方法。 对象键可以包含任何Unicode字符；但是，XML1.0解析器无法解析某些字符，例如ASCII值为0到10的字符。
	 * 对于XML1.0中不支持的字符，可以添加此参数来请求AmazonS3对响应中的密钥进行编码.
	 */
	private String encodingType;

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

	public String getEncodingType() {
		return encodingType;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	@Override
	public String toString() {
		return "ListMultipartUploadsArguments{" + "delimiter='" + delimiter + '\'' + ", prefix='" + prefix + '\''
				+ ", maxUploads=" + maxUploads + ", keyMarker='" + keyMarker + '\'' + ", uploadIdMarker='"
				+ uploadIdMarker + '\'' + ", encodingType='" + encodingType + '\'' + "} " + super.toString();
	}

}
