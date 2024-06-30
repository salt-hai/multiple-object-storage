package salthai.top.object.storage.core.arguments.multipart;

import salthai.top.object.storage.core.arguments.base.BasePartArguments;
import salthai.top.object.storage.core.domain.multipart.PartSummaryDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 完成分片上传
 *
 * @author Kuang HaiBo 2023/11/16 16:26
 */
public class CompleteMultipartUploadArguments extends BasePartArguments {

	/**
	 * 完成分段上传时要使用的分片信息, 不能为空
	 */
	private List<PartSummaryDomain> parts = new ArrayList<>();

	public List<PartSummaryDomain> getParts() {
		return parts;
	}

	public void setParts(List<PartSummaryDomain> parts) {
		if (Objects.isNull(parts) || parts.isEmpty()) {
			return;
		}
		this.parts = parts;
	}

	/**
	 * 添加分片信息
	 * @param part 分片信息
	 */
	public void addPart(PartSummaryDomain part) {
		Objects.requireNonNull(part, "add target part cant be null");
		this.parts.add(part);
	}

	@Override
	public String toString() {
		return "CompleteMultipartUploadArguments{" + "parts=" + parts + "} " + super.toString();
	}

}
