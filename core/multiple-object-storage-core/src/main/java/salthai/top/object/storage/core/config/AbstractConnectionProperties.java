package salthai.top.object.storage.core.config;

/**
 * 对客户端链接参数的抽象
 *
 * @author Kuang HaiBo 2023/12/8 14:41
 */
@Deprecated
public class AbstractConnectionProperties {

	/**
	 * 最大连接数
	 */
	protected Integer maxConnections = 1024;

	/**
	 * 建立连接的超时时间（单位：毫秒）。默认为50000毫秒。
	 */
	protected Integer connectionTimeout = 50000;

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(Integer maxConnections) {
		this.maxConnections = maxConnections;
	}

	@Override
	public String toString() {
		return "AbstractConnectionProperties{" + "maxConnections=" + maxConnections + ", connectionTimeout="
				+ connectionTimeout + '}';
	}

}
