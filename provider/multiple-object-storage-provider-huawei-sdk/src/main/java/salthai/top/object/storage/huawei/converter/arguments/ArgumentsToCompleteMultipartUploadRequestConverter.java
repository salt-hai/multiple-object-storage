package salthai.top.object.storage.huawei.converter.arguments;

import salthai.top.object.storage.core.arguments.multipart.CompleteMultipartUploadArguments;
import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;
import salthai.top.object.storage.huawei.converter.ArgumentsToBaseObjectRequestConverter;
import com.obs.services.model.CompleteMultipartUploadRequest;
import com.obs.services.model.PartEtag;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成分片上传请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/20 14:38
 */
public class ArgumentsToCompleteMultipartUploadRequestConverter extends
		ArgumentsToBaseObjectRequestConverter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CompleteMultipartUploadRequest getInstance(CompleteMultipartUploadArguments arguments) {
		CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest();
		request.setUploadId(arguments.getUploadId());
		List<PartSummaryDomain> parts = arguments.getParts();
		List<PartEtag> partEtag = new ArrayList<>(parts.size());
		parts.forEach(partSummaryDomain -> {
			PartEtag etag = new PartEtag();
			etag.setEtag(partSummaryDomain.getEtag());
			etag.setPartNumber(partSummaryDomain.getPartNumber());
			partEtag.add(etag);
		});
		request.setPartEtag(partEtag);
		return request;
	}

}
