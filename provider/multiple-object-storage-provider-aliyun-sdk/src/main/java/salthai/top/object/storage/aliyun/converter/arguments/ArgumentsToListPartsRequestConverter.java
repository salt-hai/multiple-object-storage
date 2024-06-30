package salthai.top.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.ListPartsRequest;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.ListPartsArguments;

import java.util.Objects;

/**
 * 用于列出正在进行的分段上传的各个部分的请求转换类
 *
 * @author HaiBo Kuang 2023/11/19 14:50
 */
public class ArgumentsToListPartsRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<ListPartsArguments, ListPartsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListPartsRequest getInstance(ListPartsArguments arguments) {
		ListPartsRequest request = new ListPartsRequest(arguments.getBucketName(), arguments.getObjectName(),
				arguments.getUploadId());
		if (Objects.nonNull(arguments.getMaxParts())) {
			request.setMaxParts(arguments.getMaxParts());
		}
		if (Objects.nonNull(arguments.getPartNumberMarker())) {
			request.setPartNumberMarker(arguments.getPartNumberMarker());
		}
		return request;
	}

}
