package com.hengtacc.object.storage.aliyun.converter.arguments;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.hengtacc.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import com.hengtacc.object.storage.core.arguments.object.CopyObjectArguments;

import java.util.Objects;

/**
 * oss 拷贝请求转换
 *
 * @author Kuang HaiBo 2023/11/9 16:34
 */
public class ArgumentsToCopyObjectRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<CopyObjectArguments, CopyObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CopyObjectRequest getInstance(CopyObjectArguments arguments) {
		CopyObjectRequest request = new CopyObjectRequest(arguments.getSource().getBucketName(),
				arguments.getSource().getObjectName(), arguments.getTarget().getBucketName(),
				arguments.getTarget().getObjectName());
		if (StrUtil.isNotBlank(arguments.getSource().getVersionId())) {
			request.setSourceVersionId(arguments.getSource().getVersionId());
		}
		if (CollectionUtil.isNotEmpty(arguments.getSource().getMatchEtag())) {
			request.setMatchingETagConstraints(arguments.getSource().getMatchEtag());
		}
		if (CollectionUtil.isNotEmpty(arguments.getSource().getNotMatchEtag())) {
			request.setNonmatchingETagConstraints(arguments.getSource().getNotMatchEtag());
		}
		if (Objects.nonNull(arguments.getSource().getModifiedSince())) {
			request.setModifiedSinceConstraint(arguments.getSource().getModifiedSince());
		}
		if (Objects.nonNull(arguments.getSource().getUnmodifiedSince())) {
			request.setUnmodifiedSinceConstraint(arguments.getSource().getUnmodifiedSince());
		}
		if (StrUtil.isNotBlank(arguments.getTarget().getServerSideEncryption())) {
			request.setServerSideEncryption(arguments.getTarget().getServerSideEncryption());
		}
		if (StrUtil.isNotBlank(arguments.getTarget().getServerSideEncryptionKeyID())) {
			request.setServerSideEncryptionKeyId(arguments.getTarget().getServerSideEncryptionKeyID());
		}
		if (MapUtil.isNotEmpty(arguments.getTarget().getObjectMetadata())) {
			ObjectMetadata targetMetadata = new ObjectMetadata();
			// 设置 元数据
			arguments.getTarget().getObjectMetadata().forEach(targetMetadata::setHeader);
			request.setNewObjectMetadata(targetMetadata);
		}
		return request;
	}

}
