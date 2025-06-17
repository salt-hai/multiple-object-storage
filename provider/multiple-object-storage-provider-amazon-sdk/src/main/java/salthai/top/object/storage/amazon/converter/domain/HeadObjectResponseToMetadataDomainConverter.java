package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.amazon.converter.S3ResponseToBaseRespondDomainConverter;
import salthai.top.object.storage.core.domain.object.ObjectMetadataDomain;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import java.util.Date;

/**
 * 转为元数据对象
 *
 * @author Kuang HaiBo 2025/5/19 16:33
 */
public class HeadObjectResponseToMetadataDomainConverter
		extends S3ResponseToBaseRespondDomainConverter<HeadObjectResponse, ObjectMetadataDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param headObjectResponse 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ObjectMetadataDomain getInstance(HeadObjectResponse headObjectResponse) {
		ObjectMetadataDomain metadataDomain = new ObjectMetadataDomain();
		metadataDomain.setContentLength(headObjectResponse.contentLength());
		metadataDomain.setContentType(headObjectResponse.contentType());
		metadataDomain.setLastModified(Date.from(headObjectResponse.lastModified()));
		metadataDomain.setEtag(headObjectResponse.eTag());
		metadataDomain.setVersionId(headObjectResponse.versionId());
		return metadataDomain;
	}

}
