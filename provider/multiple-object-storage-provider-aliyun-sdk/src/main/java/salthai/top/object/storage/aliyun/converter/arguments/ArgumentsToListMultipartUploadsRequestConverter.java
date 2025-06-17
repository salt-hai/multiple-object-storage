package salthai.top.object.storage.aliyun.converter.arguments;

import com.aliyun.oss.model.ListMultipartUploadsRequest;
import org.apache.commons.lang3.StringUtils;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.multipart.ListMultipartUploadsArguments;

import java.util.Objects;

/**
 * 用于列出在存储桶下执行分段上传的请求参数 转换类
 *
 * @author HaiBo Kuang 2023/11/19 15:33
 */
public class ArgumentsToListMultipartUploadsRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListMultipartUploadsRequest getInstance(ListMultipartUploadsArguments arguments) {
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(arguments.getBucketName());
		if (StringUtils.isNotBlank(arguments.getDelimiter())) {
			request.setDelimiter(arguments.getDelimiter());
		}
		if (StringUtils.isNotBlank(arguments.getPrefix())) {
			request.setPrefix(arguments.getPrefix());
		}
		if (StringUtils.isNotBlank(arguments.getEncodingType())) {
			request.setEncodingType(arguments.getEncodingType());
		}
		if (StringUtils.isNotBlank(arguments.getUploadIdMarker())) {
			request.setUploadIdMarker(arguments.getUploadIdMarker());
		}
		if (Objects.nonNull(arguments.getMaxUploads())) {
			request.setMaxUploads(arguments.getMaxUploads());
		}
		if (StringUtils.isNotBlank(arguments.getKeyMarker())) {
			request.setKeyMarker(arguments.getKeyMarker());
		}
		return request;
	}

}
