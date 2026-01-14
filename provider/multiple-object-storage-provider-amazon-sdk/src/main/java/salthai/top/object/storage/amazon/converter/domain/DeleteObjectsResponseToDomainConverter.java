package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.amazon.converter.S3ResponseToBaseRespondDomainConverter;
import salthai.top.object.storage.core.domain.object.DelObjectDomain;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.DeletedObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * S3 DeleteObjectsResponse 转 List<DelObjectDomain>
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class DeleteObjectsResponseToDomainConverter
		extends S3ResponseToBaseRespondDomainConverter<DeleteObjectsResponse, List<DelObjectDomain>> {

	/**
	 * 获取最终生成对象实例
	 * @param response 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public List<DelObjectDomain> getInstance(DeleteObjectsResponse response) {
		// 将删除成功的对象转换为 DelObjectDomain 列表
		return response.deleted().stream().map(DeletedObject::key).map(DelObjectDomain::new).collect(Collectors.toList());
	}

}
