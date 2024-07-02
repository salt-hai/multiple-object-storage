package salthai.top.object.storage.core.arguments.object;

import jakarta.validation.constraints.NotNull;
import salthai.top.object.storage.core.acl.AccessControlList;
import salthai.top.object.storage.core.arguments.base.ObjectVersionArguments;

import java.util.Objects;

/**
 * 设置 对象acl
 *
 * @author Kuang HaiBo 2023/11/7 15:59
 */
public class SetObjectAclArguments extends ObjectVersionArguments {

	/**
	 * 对象acl，该acl 只列举了常用的三个,如需更复杂的acl设置需要使用不同厂商的原生sdk方法
	 */
	@NotNull(message = "acl 信息不允许为空")
	private AccessControlList acl;

	public AccessControlList getAcl() {
		return acl;
	}

	public void setAcl(AccessControlList acl) {
		Objects.requireNonNull(acl, "acl cant be null");
		this.acl = acl;
	}

	@Override
	public String toString() {
		return "SetObjectAclArguments{" + "acl=" + acl + "} " + super.toString();
	}

}
