package com.hengtacc.object.storage.core.arguments.multipart;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import com.hengtacc.object.storage.core.arguments.base.BasePartArguments;
import com.hengtacc.object.storage.core.constants.StorageConstants;

import java.io.InputStream;

/**
 * 分片上传参数
 *
 * @author Kuang HaiBo 2023/11/16 16:13
 */
public class UploadPartArguments extends BasePartArguments {

	/**
	 * 不为空。 描述该分片相对于分片上传中其他分片的位置的分片号。分片号必须介于1和10000之间（包括1和10000）。
	 */
	private int partNumber;

	/**
	 * 此分片的大小，以字节为单位
	 */
	private long partSize;

	/**
	 * // * 不为空, 包含要为分片上载的数据的流。必须仅指定一个 File 或 InputStream 作为此操作的输入。
	 */
	private InputStream inputStream;

	/**
	 * 没有可以不填,本部分内容的可选但推荐的MD5哈希。如果指定，当数据到达 服务商 时，该值将被发送到服务商以验证数据的完整性。
	 */
	private String md5Digest;

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		Validator.isBetween(partNumber, StorageConstants.MIN_PART_NUMBER, StorageConstants.MAX_PART_NUMBER);
		this.partNumber = partNumber;
	}

	public Long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		Assert.isFalse(partSize < 0, "part size must > 0");
		this.partSize = partSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMd5Digest() {
		return md5Digest;
	}

	public void setMd5Digest(String md5Digest) {
		this.md5Digest = md5Digest;
	}

	@Override
	public String toString() {
		return "UploadPartArguments{" + "partNumber=" + partNumber + ", partSize=" + partSize + ", inputStream="
				+ inputStream + ", md5Digest='" + md5Digest + '\'' + "} " + super.toString();
	}

}
