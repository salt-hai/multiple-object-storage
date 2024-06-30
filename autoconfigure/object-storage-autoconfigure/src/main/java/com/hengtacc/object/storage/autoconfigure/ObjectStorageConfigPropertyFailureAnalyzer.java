package com.hengtacc.object.storage.autoconfigure;

import com.hengtacc.object.storage.core.exceptions.ObjectStorageConfigPropertyException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * 应对配置属性错误时的异常错误分析类
 *
 * @author Kuang HaiBo 2024/5/14 15:47
 */
public class ObjectStorageConfigPropertyFailureAnalyzer
		extends AbstractFailureAnalyzer<ObjectStorageConfigPropertyException> {

	/**
	 * Returns an analysis of the given {@code rootFailure}, or {@code null} if no
	 * analysis was possible.
	 * @param rootFailure the root failure passed to the analyzer
	 * @param cause the actual found cause
	 * @return the analysis or {@code null}
	 */
	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, ObjectStorageConfigPropertyException cause) {
		return new FailureAnalysis(cause.getDescription(), cause.getAction(), cause.getExCause());
	}

}
