package com.hengtacc.object.storage.core.diagnostics;

/**
 * 异常分析包装
 *
 * @author Kuang HaiBo 2024/5/14 16:25
 */
public interface FailureAnalysisWraps {

	/**
	 * 描述
	 * @return 描述
	 */
	String getDescription();

	/**
	 * 应该执行的操作
	 * @return 动作
	 */
	String getAction();

	/**
	 * 报告异常
	 * @return 异常
	 */
	Throwable getExCause();

}
