package salthai.top.object.storage.baidu.converter.arguments;

import cn.hutool.core.util.StrUtil;
import com.baidubce.services.bos.model.ListObjectsRequest;
import salthai.top.object.storage.baidu.converter.ArgumentsToGenericBucketRequestConverter;
import salthai.top.object.storage.core.arguments.object.ListObjectsArguments;

import java.util.Objects;

/**
 * 列举对象请求参数转换器
 *
 * @author HaiBo Kuang 2023/11/19 16:32
 */
public class ArgumentsToListObjectsRequestConverter
		extends ArgumentsToGenericBucketRequestConverter<ListObjectsArguments, ListObjectsRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param arguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsRequest getInstance(ListObjectsArguments arguments) {
		ListObjectsRequest request = new ListObjectsRequest(arguments.getBucketName());
		if (StrUtil.isNotBlank(arguments.getPrefix())) {
			request.setPrefix(arguments.getPrefix());
		}
		if (StrUtil.isNotBlank(arguments.getMarker())) {
			request.setMarker(arguments.getMarker());
		}
		if (StrUtil.isNotBlank(arguments.getDelimiter())) {
			request.setDelimiter(arguments.getDelimiter());
		}
		if (Objects.nonNull(arguments.getMaxKeys())) {
			request.setMaxKeys(arguments.getMaxKeys());
		}
		return request;
	}

}
