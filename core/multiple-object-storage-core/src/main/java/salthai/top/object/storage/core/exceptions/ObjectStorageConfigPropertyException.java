package salthai.top.object.storage.core.exceptions;

import cn.hutool.core.util.StrUtil;
import salthai.top.object.storage.core.diagnostics.FailureAnalysisWraps;

/**
 * 配置属性异常类
 *
 * @author Kuang HaiBo 2024/5/14 15:33
 */
public class ObjectStorageConfigPropertyException extends ObjectStorageException implements FailureAnalysisWraps {

	/**
	 * 异常的配置名称
	 */
	private final String propertiesName;

	/**
	 * 错误原因
	 */
	private final String failMessage;

	/**
	 * 该异常的处理建议
	 */
	private String adviceMessage;

	/**
	 * 以异常配置键构建
	 * @param propertiesName 异常键
	 * @param failMessage 错误信息
	 * @param adviceMessage 该异常的处理建议
	 */
	public ObjectStorageConfigPropertyException(String propertiesName, String failMessage, String adviceMessage) {
		super(getFailureAnalysisDescription(propertiesName, failMessage));
		this.propertiesName = propertiesName;
		this.failMessage = failMessage;
		this.adviceMessage = adviceMessage;
	}

	/**
	 * 以异常配置键构建
	 * @param failMessage 错误信息
	 * @param propertiesName 异常键
	 */
	public ObjectStorageConfigPropertyException(String propertiesName, String failMessage) {
		super(getFailureAnalysisDescription(propertiesName, failMessage));
		this.propertiesName = propertiesName;
		this.failMessage = failMessage;
	}

	/**
	 * 获取构建用于错误分析的描述
	 * @return 描述
	 */
	static String getFailureAnalysisDescription(String propertiesName, String failMessage) {
		return ("configuration property name '" + propertiesName + "' is not valid,error message '" + failMessage
				+ "'");
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public String getFailMessage() {
		return failMessage;
	}

	/**
	 * 断言给定的配置文件不为空白字符 仅仅针对字符串配置
	 * @param propertiesName 属性名称
	 * @param propertiesValue 属性值
	 */
	public static void assertNotBlank(String propertiesName, String propertiesValue) {
		if (StrUtil.isBlank(propertiesValue)) {
			throw new ObjectStorageConfigPropertyException(propertiesName, "cant be null or empty");
		}
	}

	/**
	 * 描述
	 * @return 描述
	 */
	@Override
	public String getDescription() {
		return getMessage();
	}

	/**
	 * 应该执行的操作
	 * @return 动作
	 */
	@Override
	public String getAction() {
		return StrUtil.isNotBlank(adviceMessage) ? adviceMessage : "check you configuration";
	}

	/**
	 * 报告异常
	 * @return 异常
	 */
	@Override
	public Throwable getExCause() {
		return this;
	}

}
