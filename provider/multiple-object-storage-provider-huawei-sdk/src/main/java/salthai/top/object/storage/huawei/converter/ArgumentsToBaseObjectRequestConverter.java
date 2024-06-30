package salthai.top.object.storage.huawei.converter;

import salthai.top.object.storage.core.arguments.base.ObjectArguments;
import com.obs.services.model.BaseObjectRequest;

/**
 * 对于继承于{@link ObjectArguments }参数统一设置 对象信息
 *
 * @author Kuang HaiBo 2023/11/15 17:01
 */
public abstract class ArgumentsToBaseObjectRequestConverter<S extends ObjectArguments, T extends BaseObjectRequest>
		extends ArgumentsToGenericRequestConverter<S, T> {

	/**
	 * 此处给obs设置一些统一参数 ，
	 * @param source 源对象
	 * @param target 转换后的对象
	 */
	@Override
	public void prepare(S source, T target) {
		target.setObjectKey(source.getObjectName());
		target.setBucketName(source.getBucketName());
		super.prepare(source, target);
	}

}
