package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.PartETag;
import salthai.top.object.storage.baidu.converter.ArgumentsToGenericObjectRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.CompleteMultipartUploadArguments;
import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成分片上传请求装换器
 *
 * @author Kuang HaiBo 2023/12/7 17:13
 */
public class ArgumentToCompleteMultipartUploadRequestConverter extends
		ArgumentsToGenericObjectRequestConverter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param completeMultipartUploadArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CompleteMultipartUploadRequest getInstance(
			CompleteMultipartUploadArguments completeMultipartUploadArguments) {
		CompleteMultipartUploadRequest uploadArguments = new CompleteMultipartUploadRequest();
		uploadArguments.setUploadId(completeMultipartUploadArguments.getUploadId());
		uploadArguments.setKey(completeMultipartUploadArguments.getObjectName());
		uploadArguments.setBucketName(completeMultipartUploadArguments.getBucketName());
		uploadArguments.setPartETags(getETags(completeMultipartUploadArguments.getParts()));
		return uploadArguments;
	}

	/**
	 * 转为 bos 完成分片上传所需 e tag
	 * @param parts 此次完成分片的对象
	 * @return eTag
	 */
	static List<PartETag> getETags(List<PartSummaryDomain> parts) {
		if (parts.isEmpty()) {
			return new ArrayList<>();
		}
		List<PartETag> tags = new ArrayList<>(parts.size());
		parts.forEach(partSummaryDomain -> {
			PartETag tag = new PartETag();
			tag.setETag(partSummaryDomain.getEtag());
			tag.setPartNumber(partSummaryDomain.getPartNumber());
			tags.add(tag);
		});
		return tags;
	}

}
