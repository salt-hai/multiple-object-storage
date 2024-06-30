package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.ListPartsRequest;
import salthai.top.object.storage.core.arguments.multipart.ListPartsArguments;
import salthai.top.object.storage.core.function.Converter;

/**
 * 列举分片参数模型转为 bos 列举分片请求参数转换器
 *
 * @author Kuang HaiBo 2023/12/11 11:21
 */
public class ArgumentToListPartsRequestConverter implements Converter<ListPartsArguments, ListPartsRequest> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source the source object to convert, which must be an instance of {@code S}
	 * (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public ListPartsRequest convert(ListPartsArguments source) {
		ListPartsRequest listPartsRequest = new ListPartsRequest(source.getBucketName(), source.getObjectName(),
				source.getUploadId());
		listPartsRequest.setMaxParts(source.getMaxParts());
		listPartsRequest.setPartNumberMarker(source.getPartNumberMarker());
		return listPartsRequest;
	}

}
