package salthai.top.object.storage.core.arguments.multipart;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import salthai.top.object.storage.core.arguments.base.BasePartArguments;
import salthai.top.object.storage.core.constants.StorageConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传分片拷贝请求参数实体
 *
 * @author Kuang HaiBo 2023/11/16 16:19
 */
public class UploadPartCopyArguments extends BasePartArguments {

	/**
	 * 不为空 描述该分片相对于分片上传中其他分片的位置的分片号。分片号必须介于1和10000之间（包括1和10000）。
	 */
	private int partNumber;

	/**
	 * 此分片的大小，以字节为单位
	 */
	private long partSize;

	/**
	 * 不为空 来源桶
	 */
	private String sourceBucketName;

	/**
	 * 不为空 来源对象
	 */
	private String sourceObjectName;

	/**
	 * 来源对象版本id 没有指定就是用最新的
	 */
	private String sourceObjectVersionId;

	/**
	 * 不为空 ETag值反向匹配约束列表，该列表将复制请求约束为仅在源对象的ETag与任何指定的ETag约束值不匹配时执行。
	 */
	private final List<String> nonMatchingEtagConstraints = new ArrayList<>();

	/**
	 * ETag值匹配约束列表，该列表约束复制请求仅在源对象的ETag与指定的ETag值之一匹配时执行。
	 */
	private List<String> matchingEtagConstraints = new ArrayList<>();

	/**
	 * 可选字段，用于将复制请求限制为仅在源对象自指定日期以来已被修改时才执行。
	 */
	private Date modifiedSinceConstraint;

	/**
	 * 可选字段，用于将复制请求限制为仅在源对象自指定日期以来未修改时执行
	 */
	private Date unmodifiedSinceConstraint;

	public List<String> getNonMatchingEtagConstraints() {
		return nonMatchingEtagConstraints;
	}

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		Validator.isBetween(partNumber, StorageConstants.MIN_PART_NUMBER, StorageConstants.MAX_PART_NUMBER);
		this.partNumber = partNumber;
	}

	public String getSourceBucketName() {
		return sourceBucketName;
	}

	public void setSourceBucketName(String sourceBucketName) {
		Assert.notBlank(sourceBucketName, "source bucket name cant be null or empty");
		this.sourceBucketName = sourceBucketName;
	}

	public String getSourceObjectName() {
		return sourceObjectName;
	}

	public void setSourceObjectName(String sourceObjectName) {
		Assert.notBlank(sourceBucketName, "destination object name cant be null or empty");
		this.sourceObjectName = sourceObjectName;
	}

	public List<String> getMatchingEtagConstraints() {
		return matchingEtagConstraints;
	}

	public void setMatchingEtagConstraints(List<String> matchingEtagConstraints) {
		this.matchingEtagConstraints = matchingEtagConstraints;
	}

	public Date getModifiedSinceConstraint() {
		return modifiedSinceConstraint;
	}

	public void setModifiedSinceConstraint(Date modifiedSinceConstraint) {
		this.modifiedSinceConstraint = modifiedSinceConstraint;
	}

	public Date getUnmodifiedSinceConstraint() {
		return unmodifiedSinceConstraint;
	}

	public void setUnmodifiedSinceConstraint(Date unmodifiedSinceConstraint) {
		this.unmodifiedSinceConstraint = unmodifiedSinceConstraint;
	}

	public String getSourceObjectVersionId() {
		return sourceObjectVersionId;
	}

	public void setSourceObjectVersionId(String sourceObjectVersionId) {
		this.sourceObjectVersionId = sourceObjectVersionId;
	}

	public long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		Assert.isFalse(partSize < 0, "part size must > 0");
		this.partSize = partSize;
	}

	@Override
	public String toString() {
		return "UploadPartCopyArguments{" + "partNumber=" + partNumber + ", partSize=" + partSize
				+ ", sourceBucketName='" + sourceBucketName + '\'' + ", sourceObjectName='" + sourceObjectName + '\''
				+ ", sourceObjectVersionId='" + sourceObjectVersionId + '\'' + ", nonMatchingEtagConstraints="
				+ nonMatchingEtagConstraints + ", matchingEtagConstraints=" + matchingEtagConstraints
				+ ", modifiedSinceConstraint=" + modifiedSinceConstraint + ", unmodifiedSinceConstraint="
				+ unmodifiedSinceConstraint + "} " + super.toString();
	}

}
