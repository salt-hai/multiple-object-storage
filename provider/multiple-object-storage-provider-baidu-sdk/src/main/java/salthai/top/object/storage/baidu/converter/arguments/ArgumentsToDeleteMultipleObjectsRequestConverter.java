package salthai.top.object.storage.baidu.converter.arguments;

import com.baidubce.services.bos.model.DeleteMultipleObjectsRequest;
import salthai.top.object.storage.core.arguments.object.DelObjectsArguments;
import salthai.top.object.storage.core.function.Converter;

/**
 * 批量删除
 *
 * @author Kuang HaiBo 2023/11/14 14:18
 */
public class ArgumentsToDeleteMultipleObjectsRequestConverter
		implements Converter<DelObjectsArguments, DeleteMultipleObjectsRequest> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param delObjectsArguments the source object to convert, which must be an instance
	 * of {@code S} (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public DeleteMultipleObjectsRequest convert(DelObjectsArguments delObjectsArguments) {
		String bucketName = delObjectsArguments.getBucketName();
		DeleteMultipleObjectsRequest request = new DeleteMultipleObjectsRequest();
		request.setBucketName(bucketName);
		request.setObjectKeys(delObjectsArguments.getObjectNames());
		return request;
	}

}
