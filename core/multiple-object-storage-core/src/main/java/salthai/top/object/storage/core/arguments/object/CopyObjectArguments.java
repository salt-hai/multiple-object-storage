package salthai.top.object.storage.core.arguments.object;

import salthai.top.object.storage.core.arguments.base.BaseArguments;

import java.util.Objects;

/**
 * 对象拷贝 参数
 *
 * @author Kuang HaiBo 2023/11/9 13:57
 */
public class CopyObjectArguments extends BaseArguments {

	/**
	 * 来源
	 */
	private CopySourceObjectArguments source;

	/**
	 * 目标
	 */
	private CopyTargetObjectArguments target;

	public CopySourceObjectArguments getSource() {
		return source;
	}

	public void setSource(CopySourceObjectArguments source) {
		Objects.requireNonNull(source, "source object cant be null");
		this.source = source;
	}

	public CopyTargetObjectArguments getTarget() {
		return target;
	}

	public void setTarget(CopyTargetObjectArguments target) {
		Objects.requireNonNull(target, "target object cant be null");
		this.target = target;
	}

	@Override
	public String toString() {
		return "CopyObjectArguments{" + "source=" + source + ", target=" + target + "} " + super.toString();
	}

}
