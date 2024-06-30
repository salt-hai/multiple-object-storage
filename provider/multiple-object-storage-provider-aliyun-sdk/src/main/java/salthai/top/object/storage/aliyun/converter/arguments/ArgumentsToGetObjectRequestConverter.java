package salthai.top.object.storage.aliyun.converter.arguments;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.model.GetObjectRequest;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.object.GetObjectArguments;

import java.util.Objects;

/**
 * 转换为 oss get object {@link com.aliyun.oss.model.GetObjectRequest}
 *
 * @author Kuang HaiBo 2023/11/8 16:44
 */
public class ArgumentsToGetObjectRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<GetObjectArguments, GetObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectRequest getInstance(GetObjectArguments arguments) {
		GetObjectRequest request = new GetObjectRequest(arguments.getBucketName(), arguments.getObjectName());
		if (StrUtil.isNotBlank(arguments.getVersionId())) {
			request.setVersionId(arguments.getVersionId());
		}
		if (Objects.nonNull(arguments.getModifiedSince())) {
			request.setModifiedSinceConstraint(arguments.getModifiedSince());
		}
		if (Objects.nonNull(arguments.getUnmodifiedSince())) {
			request.setUnmodifiedSinceConstraint(arguments.getUnmodifiedSince());
		}
		if (Objects.nonNull(arguments.getMatchEtag())) {
			request.setMatchingETagConstraints(arguments.getMatchEtag());
		}
		if (Objects.nonNull(arguments.getNotMatchEtag())) {
			request.setNonmatchingETagConstraints(arguments.getNotMatchEtag());
		}
		return request;
	}

}
