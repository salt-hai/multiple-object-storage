package salthai.top.object.storage.aliyun.converter.arguments;

import cn.hutool.core.collection.CollectionUtil;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.PartETag;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.CompleteMultipartUploadArguments;
import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 完成分片上传参数转oss请求参数
 *
 * @author Kuang HaiBo 2023/11/17 17:22
 */
public class ArgumentsToCompleteMultipartUploadRequestConverter extends
		BaseArgumentsToWebServiceRequestConverter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CompleteMultipartUploadRequest getInstance(CompleteMultipartUploadArguments arguments) {
		return new CompleteMultipartUploadRequest(arguments.getBucketName(), arguments.getObjectName(),
				arguments.getUploadId(), getPartETags(arguments.getParts()));
	}

	/**
	 * 转为 partETags
	 * @param attributes 完成分段上传时要使用的分片信息
	 * @return partETags
	 */
	static List<PartETag> getPartETags(List<PartSummaryDomain> attributes) {
		if (CollectionUtil.isNotEmpty(attributes)) {
			return attributes.stream()
				.map(item -> new PartETag(item.getPartNumber(), item.getEtag()))
				.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}
