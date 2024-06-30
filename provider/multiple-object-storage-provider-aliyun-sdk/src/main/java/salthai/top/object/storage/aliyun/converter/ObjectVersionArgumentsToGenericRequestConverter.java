package salthai.top.object.storage.aliyun.converter;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.model.GenericRequest;
import salthai.top.object.storage.core.arguments.base.ObjectVersionArguments;

/**
 * 对象参数转oss通用参数 通过继承{@link BaseArgumentsToWebServiceRequestConverter} 来转成
 * {@link com.aliyun.oss.model.GenericRequest}
 * <p>
 * 该类可以生成{@link GenericRequest} 该类可以对bucket 进行一些操作 里面的 key ==> objectKey
 * </p>
 *
 * @author Kuang HaiBo 2023/11/8 11:58
 */
public class ObjectVersionArgumentsToGenericRequestConverter<Source extends ObjectVersionArguments>
		extends BaseArgumentsToWebServiceRequestConverter<Source, GenericRequest> {

	/**
	 * 获取最终生成对象实例
	 * @param source 源对象。传递源对象，方便参数设置
	 * @return 转换后的对象实例
	 */
	@Override
	public GenericRequest getInstance(Source source) {
		GenericRequest genericRequest = new GenericRequest(source.getBucketName());
		genericRequest.setKey(source.getObjectName());
		if (StrUtil.isNotBlank(source.getVersionId())) {
			genericRequest.setVersionId(source.getVersionId());
		}
		return genericRequest;
	}

}
