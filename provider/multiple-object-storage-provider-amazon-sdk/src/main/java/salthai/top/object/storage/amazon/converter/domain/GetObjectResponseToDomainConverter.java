package salthai.top.object.storage.amazon.converter.domain;

import software.amazon.awssdk.services.s3.model.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import salthai.top.object.storage.amazon.converter.S3ResponseToBaseRespondDomainConverter;
import salthai.top.object.storage.core.domain.object.GetObjectDomain;

import java.io.ByteArrayInputStream;

/**
 * S3 ResponseBytes<GetObjectResponse> 转 GetObjectDomain
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class GetObjectResponseToDomainConverter
		extends S3ResponseToBaseRespondDomainConverter<ResponseBytes<GetObjectResponse>, GetObjectDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param responseBytes 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GetObjectDomain getInstance(ResponseBytes<GetObjectResponse> responseBytes) {
		GetObjectDomain domain = new GetObjectDomain();
		GetObjectResponse response = responseBytes.response();
		domain.setObjectName(response.key());
		domain.setBucketName(response.bucket());
		// 将字节数组转换为输入流
		domain.setObjectContent(new ByteArrayInputStream(responseBytes.asByteArray()));
		return domain;
	}

}
