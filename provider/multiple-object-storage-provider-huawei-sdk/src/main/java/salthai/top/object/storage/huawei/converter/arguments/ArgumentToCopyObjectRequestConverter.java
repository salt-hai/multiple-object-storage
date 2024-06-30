package salthai.top.object.storage.huawei.converter.arguments;

import cn.hutool.core.map.MapUtil;
import salthai.top.object.storage.core.arguments.object.CopyObjectArguments;
import salthai.top.object.storage.huawei.converter.ArgumentsToGenericRequestConverter;
import com.obs.services.model.CopyObjectRequest;
import com.obs.services.model.ObjectMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象拷贝参数转换器
 *
 * @author Kuang HaiBo 2023/12/15 16:49
 */
public class ArgumentToCopyObjectRequestConverter
		extends ArgumentsToGenericRequestConverter<CopyObjectArguments, CopyObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param copyObjectArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public CopyObjectRequest getInstance(CopyObjectArguments copyObjectArguments) {
		CopyObjectRequest request = new CopyObjectRequest();
		// 目标对象
		request.setDestinationBucketName(copyObjectArguments.getTarget().getBucketName());
		request.setDestinationObjectKey(copyObjectArguments.getTarget().getObjectName());
		Map<String, String> targetObjectMetadataMap = copyObjectArguments.getTarget().getObjectMetadata();
		if (MapUtil.isNotEmpty(targetObjectMetadataMap)) {
			// 目标对象元数据
			ObjectMetadata targetObjectMetadata = new ObjectMetadata();
			Map<String, Object> mapMetadata = new HashMap<>(targetObjectMetadataMap);
			targetObjectMetadata.setMetadata(mapMetadata);
			request.setNewObjectMetadata(targetObjectMetadata);
		}
		// 源对象
		request.setBucketName(copyObjectArguments.getSource().getBucketName());
		request.setObjectKey(copyObjectArguments.getSource().getObjectName());
		request.setVersionId(copyObjectArguments.getSource().getVersionId());

		request.setIfModifiedSince(copyObjectArguments.getSource().getModifiedSince());
		request.setIfUnmodifiedSince(copyObjectArguments.getSource().getUnmodifiedSince());
		return request;
	}

}
