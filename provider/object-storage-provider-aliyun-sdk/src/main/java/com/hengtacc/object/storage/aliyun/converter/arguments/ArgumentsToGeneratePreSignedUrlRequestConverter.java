package com.hengtacc.object.storage.aliyun.converter.arguments;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.hengtacc.object.storage.core.arguments.object.GenPreSignedUrlArguments;
import com.hengtacc.object.storage.core.function.Converter;

import java.util.Date;
import java.util.Map;

/**
 * 参数转成oss 生成url 转换器
 *
 * @author Kuang HaiBo 2023/11/9 10:47
 */
public class ArgumentsToGeneratePreSignedUrlRequestConverter
		implements Converter<GenPreSignedUrlArguments, GeneratePresignedUrlRequest> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param arguments the source object to convert, which must be an instance of
	 * {@code S} (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially
	 * {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired
	 * target type
	 */
	@Override
	public GeneratePresignedUrlRequest convert(GenPreSignedUrlArguments arguments) {
		GeneratePresignedUrlRequest ossReq = new GeneratePresignedUrlRequest(arguments.getBucketName(),
				arguments.getObjectName());
		ossReq.setMethod(HttpMethod.valueOf(arguments.getMethod().name()));
		if (MapUtil.isNotEmpty(arguments.getExtraHeaders())) {
			ossReq.setHeaders(arguments.getExtraHeaders());
		}
		if (MapUtil.isNotEmpty(arguments.getExtraQueryParams())) {
			ossReq.setQueryParameter(arguments.getExtraQueryParams());
		}
		if (StrUtil.isNotBlank(arguments.getVersionId())) {
			Map<String, String> queryParameter = ossReq.getQueryParameter();
			queryParameter.put(OSSHeaders.OSS_VERSION_ID, arguments.getVersionId());
			ossReq.setQueryParameter(queryParameter);
		}
		ossReq.setContentType(arguments.getContentType());
		ossReq.setContentMD5(arguments.getContentMD5());
		Date expiration = new Date();
		expiration.setTime(expiration.getTime() + arguments.getExpiration().toMillis());
		ossReq.setExpiration(expiration);
		return ossReq;
	}

}
