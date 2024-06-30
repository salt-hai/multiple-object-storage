package com.hengtacc.object.storage.core.arguments.object;

import com.hengtacc.object.storage.core.arguments.base.BucketArguments;
import com.hengtacc.object.storage.core.domain.object.ListObjectsDomain;

/**
 * 列表查询
 *
 * @author Kuang HaiBo 2023/11/15 9:57
 */
public class ListObjectsArguments extends BucketArguments {

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * 关键字
	 */
	private String marker;

	/**
	 * 对文件名称进行分组的一个字符。所有名称包含指定的前缀且第一次出现delimiter字符之间的文件作为一组元素
	 * {@link ListObjectsDomain#getCommonPrefixes()}（commonPrefixes）. 分隔符 默认 "/"
	 */
	private String delimiter = "/";

	/**
	 * 最多返回多少 默认最多1000
	 */
	private Integer maxKeys = 1000;

	/**
	 * 编码类型
	 */
	private String encodingType;

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

	@Override
	public String toString() {
		return "ListObjectArguments{" + "prefix='" + prefix + '\'' + ", marker='" + marker + '\'' + ", delimiter='"
				+ delimiter + '\'' + ", maxKeys=" + maxKeys + ", encodingType='" + encodingType + '\'' + "} "
				+ super.toString();
	}

}
