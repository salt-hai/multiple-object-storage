package salthai.top.object.storage.aliyun.converter.arguments;

import cn.hutool.core.map.MapUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import salthai.top.object.storage.aliyun.converter.BaseArgumentsToWebServiceRequestConverter;
import salthai.top.object.storage.core.arguments.object.PutObjectArguments;

/**
 * 上传请求 转阿里云
 *
 * @author Kuang HaiBo 2023/11/8 11:40
 */
public class ArgumentsToPutObjectRequestConverter
		extends BaseArgumentsToWebServiceRequestConverter<PutObjectArguments, PutObjectRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param putObjectArguments 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public PutObjectRequest getInstance(PutObjectArguments putObjectArguments) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(putObjectArguments.getBucketName(),
				putObjectArguments.getObjectName(), putObjectArguments.getInputStream());
		// fix 请求头设置错误
		if (MapUtil.isNotEmpty(putObjectArguments.getRequestHeaders())) {
			putObjectRequest.setHeaders(putObjectArguments.getRequestHeaders());
		}
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(putObjectArguments.getContentType());
		objectMetadata.setContentLength(putObjectArguments.getObjectSize());
		if (MapUtil.isNotEmpty(putObjectArguments.getMetadata())) {
			putObjectArguments.getMetadata().forEach(objectMetadata::setHeader);
		}
		return putObjectRequest;
	}

}
