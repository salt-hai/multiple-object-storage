package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.amazon.converter.S3ResponseToBaseRespondDomainConverter;
import salthai.top.object.storage.core.domain.object.DelObjectDomain;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

/**
 * S3 DeleteObjectResponse 转 DelObjectDomain
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class DeleteObjectResponseToDomainConverter
		extends S3ResponseToBaseRespondDomainConverter<DeleteObjectResponse, DelObjectDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param response 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public DelObjectDomain getInstance(DeleteObjectResponse response) {
		// 单个删除成功时返回空对象
		return new DelObjectDomain();
	}

}
