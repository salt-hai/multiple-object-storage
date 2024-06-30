package com.hengtacc.object.storage.autoconfigure;

import com.hengtacc.object.storage.core.constants.StorageConstants;
import com.hengtacc.object.storage.core.enums.Provider;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

import java.util.Objects;

/**
 * 确认配置的使用
 *
 * @author HaiBo Kuang 2023/11/11 19:32
 */
public class ProviderCondition extends SpringBootCondition {

	/**
	 *
	 * 确定匹配的结果以及合适的日志输出
	 * @param context the condition context
	 * @param metadata the annotation metadata
	 * @return the condition outcome
	 */
	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String sourceClass = "";
		if (metadata instanceof ClassMetadata) {
			sourceClass = ((ClassMetadata) metadata).getClassName();
		}
		BindResult<Provider> currentProviderType = Binder.get(context.getEnvironment())
			.bind(StorageConstants.ITEM_PROVIDER, Provider.class);
		ConditionMessage.Builder message = ConditionMessage.forCondition("ObjectStorageItemProvider", sourceClass);
		if (!currentProviderType.isBound()) {
			// 可以不指定供应商,依据添加的依赖自动配置
			return ConditionOutcome.match("automatic item provider");
		}
		if (metadata instanceof AnnotationMetadata) {
			if (Objects.equals(currentProviderType.get(),
					ProviderConfigurations.getType(((AnnotationMetadata) metadata).getClassName()))) {
				return ConditionOutcome.match(message.because(currentProviderType.get() + "specified  provider type"));
			}
			return ConditionOutcome
				.noMatch(message.because(currentProviderType.get() + " provider type not have config"));
		}
		return ConditionOutcome.noMatch(message.because("unknown object storage  provider type"));
	}

}
