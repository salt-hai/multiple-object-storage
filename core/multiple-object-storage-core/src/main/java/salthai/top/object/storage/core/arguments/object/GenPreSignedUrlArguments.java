package salthai.top.object.storage.core.arguments.object;

import salthai.top.object.storage.core.arguments.base.ObjectVersionArguments;
import salthai.top.object.storage.core.enums.HttpMethod;

import java.time.Duration;

/**
 * 生成预签名 utr
 *
 * @author Kuang HaiBo 2023/11/9 10:42
 */
public class GenPreSignedUrlArguments extends ObjectVersionArguments {

	/**
	 * 可以获取 各种http类型的 预签名url,需要注意：有些服务商生成预签名url时并不支持全部的http请求方式如阿里云oss只支持Get&Put
	 */
	private HttpMethod method = HttpMethod.PUT;

	/**
	 * 过期时间 默认 一个小时
	 */
	private Duration expiration = Duration.ofHours(1);

	/**
	 * Content-Type to url sign
	 */
	private String contentType;

	/**
	 * Content-MD5
	 */
	private String contentMD5;

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Duration getExpiration() {
		return expiration;
	}

	public void setExpiration(Duration expiration) {
		this.expiration = expiration;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	@Override
	public String toString() {
		return "GenPreSignedUrlArguments{" + "method=" + method + ", expiration=" + expiration + ", contentType='"
				+ contentType + '\'' + ", contentMD5='" + contentMD5 + '\'' + "} " + super.toString();
	}

}
