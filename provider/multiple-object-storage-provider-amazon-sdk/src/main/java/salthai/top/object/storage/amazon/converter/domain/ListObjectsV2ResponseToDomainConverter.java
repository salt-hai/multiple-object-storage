package salthai.top.object.storage.amazon.converter.domain;

import salthai.top.object.storage.amazon.converter.S3ResponseToBaseRespondDomainConverter;
import salthai.top.object.storage.core.domain.object.ListObjectsDomain;
import salthai.top.object.storage.core.domain.object.ObjectDomain;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * S3 ListObjectsV2Response 转 ListObjectsDomain
 *
 * @author Kuang HaiBo 2025/1/14
 */
public class ListObjectsV2ResponseToDomainConverter
		extends S3ResponseToBaseRespondDomainConverter<ListObjectsV2Response, ListObjectsDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param response 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsDomain getInstance(ListObjectsV2Response response) {
		ListObjectsDomain domain = new ListObjectsDomain();

		// 设置基本信息
		domain.setBucketName(response.name());
		domain.setPrefix(response.prefix());
		domain.setDelimiter(response.delimiter());
		domain.setMaxKeys(response.maxKeys());
		domain.setEncodingType(response.encodingType());
		domain.setTruncated(response.isTruncated());

		// 设置下一页标记
		if (response.nextContinuationToken() != null) {
			domain.setNextMarker(response.nextContinuationToken());
		}

		// 转换对象列表
		List<S3Object> s3Objects = response.contents();
		if (s3Objects != null && !s3Objects.isEmpty()) {
			List<ObjectDomain> objectDomains = s3Objects.stream().map(this::convertToObject).collect(Collectors.toList());
			domain.setSummaries(objectDomains);
		}

		// 转换公共前缀（子目录）
		List<String> commonPrefixes = response.commonPrefixes();
		if (commonPrefixes != null && !commonPrefixes.isEmpty()) {
			List<String> prefixes = commonPrefixes.stream()
				.map(prefix -> prefix.prefix())
				.collect(Collectors.toList());
			domain.setCommonPrefixes(prefixes);
		}

		return domain;
	}

	/**
	 * 转换 S3Object 为 ObjectDomain
	 * @param s3Object S3对象
	 * @return ObjectDomain
	 */
	private ObjectDomain convertToObject(S3Object s3Object) {
		ObjectDomain domain = new ObjectDomain();
		domain.setObjectName(s3Object.key());
		domain.setObjectSize(s3Object.size());
		domain.setLastModified(Date.from(Instant.ofEpochSecond(s3Object.lastModified().getEpochSecond())));
		domain.setEtag(s3Object.eTag());
		domain.setStorageClass(s3Object.storageClassAsString());
		return domain;
	}

}
