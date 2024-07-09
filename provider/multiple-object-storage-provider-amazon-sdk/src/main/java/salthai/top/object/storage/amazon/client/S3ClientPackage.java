package salthai.top.object.storage.amazon.client;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.util.Objects;

/**
 * S3 客户端
 * <P>
 * AWS SDK for Java 2.x 提供了更高级的传输管理器
 * 使用该客户端{@link software.amazon.awssdk.services.s3.S3AsyncClient }进行构建
 * {@link S3TransferManager }, 部分上传将使用更高级的api
 * </P>
 *
 * @author Kuang HaiBo 2024/1/10 9:57
 */
public class S3ClientPackage implements AutoCloseable {

	/**
	 * 基础的 S3客户端用于 普通处理
	 */
	private final S3Client s3Client;

	/**
	 * 提供的高级文件传输工具， 部分上传使用
	 */
	private final S3TransferManager s3TransferManager;

	/**
	 * 预签名工具 生成可以 get put 或其他操作的url
	 */
	private final S3Presigner s3Presigner;

	public S3ClientPackage(S3Client s3Client, S3TransferManager s3TransferManager, S3Presigner s3Presigner) {
		this.s3Client = s3Client;
		this.s3TransferManager = s3TransferManager;
		this.s3Presigner = s3Presigner;
	}

	public S3Client getS3Client() {
		return s3Client;
	}

	public S3TransferManager getS3TransferManager() {
		return s3TransferManager;
	}

	public S3Presigner getS3Presigner() {
		return s3Presigner;
	}

	/**
	 * 自动关闭
	 * @throws Exception 关闭过程异常
	 */
	@Override
	public void close() throws Exception {
		if (Objects.nonNull(s3Client)) {
			s3Client.close();
		}
		if (Objects.nonNull(s3Presigner)) {
			s3Presigner.close();
		}
		if (Objects.nonNull(s3TransferManager)) {
			s3TransferManager.close();
		}
	}

}
