package salthai.top.object.storage.core.arguments.object;

import jakarta.validation.constraints.NotEmpty;
import salthai.top.object.storage.core.arguments.base.BucketArguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 批量删除
 *
 * @author Kuang HaiBo 2023/11/8 15:55
 */
public class DelObjectsArguments extends BucketArguments {

	/**
	 * 需要删除哪一些
	 */
	@NotEmpty(message = "删除对象不能为空")
	private List<DelObjectPreArguments> objects = new ArrayList<>();

	/**
	 * 是否启用安静模式进行响应，默认为false
	 */
	private Boolean quiet = false;

	public List<DelObjectPreArguments> getObjects() {
		return objects;
	}

	public void setObjects(List<DelObjectPreArguments> objects) {
		if (Objects.isNull(objects) || objects.isEmpty()) {
			return;
		}
		this.objects = objects;
	}

	public Boolean getQuiet() {
		return quiet;
	}

	public void setQuiet(Boolean quiet) {
		this.quiet = quiet;
	}

	/**
	 * 获取删除的对象名称
	 * @return 删除的对象名称
	 */
	public List<String> getObjectNames() {
		return objects.stream().map(DelObjectPreArguments::getObjectName).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "DelObjectsArguments{" + "objects=" + objects + ", quiet=" + quiet + "} " + super.toString();
	}

}
