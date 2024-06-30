package salthai.top.object.storage.aliyun.converter.domain;

import com.aliyun.oss.model.ObjectListing;
import salthai.top.object.storage.aliyun.converter.GenericResultToBaseResponseDomainConverter;
import salthai.top.object.storage.core.domain.object.ListObjectsDomain;
import salthai.top.object.storage.core.utils.ConverterUtils;

/**
 * 列表查询响应转换
 *
 * @author Kuang HaiBo 2023/11/15 10:43
 */
public class ObjectListingToDomainConverter
		extends GenericResultToBaseResponseDomainConverter<ObjectListing, ListObjectsDomain> {

	/**
	 * 获取最终生成对象实例
	 * @param objectListing 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public ListObjectsDomain getInstance(ObjectListing objectListing) {
		ListObjectsDomain domain = new ListObjectsDomain();
		domain.setBucketName(objectListing.getBucketName());
		domain.setDelimiter(objectListing.getDelimiter());
		domain.setMarker(objectListing.getMarker());
		domain.setPrefix(objectListing.getPrefix());
		domain.setEncodingType(objectListing.getEncodingType());
		domain.setTruncated(objectListing.isTruncated());
		domain.setNextMarker(objectListing.getNextMarker());
		domain.setCommonPrefixes(objectListing.getCommonPrefixes());
		domain.setSummaries(
				ConverterUtils.toTargets(objectListing.getObjectSummaries(), new OssObjectSummaryToDomainConverter()));
		return domain;
	}

}
