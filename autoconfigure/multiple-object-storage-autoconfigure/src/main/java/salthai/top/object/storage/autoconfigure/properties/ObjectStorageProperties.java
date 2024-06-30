package salthai.top.object.storage.autoconfigure.properties;

import salthai.top.object.storage.core.constants.StorageConstants;
import salthai.top.object.storage.core.enums.Provider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import salthai.top.object.storage.autoconfigure.oss.OssClientConfigurationCustomizer;

import java.time.Duration;
import java.util.Objects;

/**
 * 存储配置文件
 * <p>
 * 1.可以通过该配置文件以确定使用那种存储方式{@link #provider} 2.可以通过该配置文件以决定功能是否启用{@link #enable}
 * 3.有细化的供应商配置请使用如{@link OssClientConfigurationCustomizer} 该接口对不同的服务商进行配置
 * </p>
 *
 * @author Kuang HaiBo 2023/11/6 15:21
 */
@ConfigurationProperties(prefix = StorageConstants.PROPERTY_PREFIX)
public class ObjectStorageProperties {

	/**
	 * 启用该项目
	 */
	private boolean enable;

	/**
	 * 对该供应商的链接客户端 使用https, http 2.0
	 * <p>
	 * 若该配置启用，则相应的客户端会使用 https
	 * </p>
	 */
	private Boolean enableHttps;

	/**
	 * 供应商 没有默认值，
	 */
	private Provider provider;

	/**
	 * 连接池
	 */
	private final Pool pool = new Pool();

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Pool getPool() {
		return pool;
	}

	public void setEnableHttps(Boolean enableHttps) {
		this.enableHttps = enableHttps;
	}

	public boolean isEnableHttps() {
		if (Objects.isNull(enableHttps)) {
			return false;
		}
		return enableHttps;
	}

	/**
	 * 存有 http协议配置
	 * @return true: 存在
	 */
	public boolean haveHttpProtocolConfig() {
		return Objects.nonNull(enableHttps);
	}

	/**
	 * 对象池的配置
	 */
	public static class Pool {

		/**
		 * Whether to enable the pool. Enabled automatically if "commons-pool2" is
		 * available.
		 */
		private Boolean enabled;

		/**
		 * Maximum number of "idle" connections in the pool. Use a negative value to
		 * indicate an unlimited number of idle connections.
		 * @see org.apache.commons.pool2.impl.GenericObjectPoolConfig#setMaxIdle(int)
		 */
		private int maxIdle = 8;

		/**
		 * Target for the minimum number of idle connections to maintain in the pool. This
		 * setting only has an effect if both it and time between eviction runs are
		 * positive.
		 * @see org.apache.commons.pool2.impl.GenericObjectPoolConfig#setMinIdle(int)
		 */
		private int minIdle = 0;

		/**
		 * Maximum number of connections that can be allocated by the pool at a given
		 * time. Use a negative value for no limit.
		 * @see org.apache.commons.pool2.impl.GenericObjectPoolConfig#setMaxTotal(int)
		 */
		private int maxActive = 8;

		/**
		 * Maximum amount of time a connection allocation should block before throwing an
		 * exception when the pool is exhausted. Use a negative value to block
		 * indefinitely.
		 * @see org.apache.commons.pool2.impl.GenericObjectPoolConfig#setMaxWait(Duration)
		 */
		private Duration maxWait = Duration.ofMillis(-1);

		/**
		 * Time between runs of the idle object evictor thread. When positive, the idle
		 * object evictor thread starts, otherwise no idle object eviction is performed.
		 * @see org.apache.commons.pool2.impl.GenericObjectPoolConfig#setTimeBetweenEvictionRuns(Duration)
		 */
		private Duration timeBetweenEvictionRuns;

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public Duration getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(Duration maxWait) {
			this.maxWait = maxWait;
		}

		public Duration getTimeBetweenEvictionRuns() {
			return timeBetweenEvictionRuns;
		}

		public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
			this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
		}

		@Override
		public String toString() {
			return "Pool{" + "enabled=" + enabled + ", maxIdle=" + maxIdle + ", minIdle=" + minIdle + ", maxActive="
					+ maxActive + ", maxWait=" + maxWait + ", timeBetweenEvictionRuns=" + timeBetweenEvictionRuns + '}';
		}

	}

	@Override
	public String toString() {
		return "ObjectStorageProperties{" + "enable=" + enable + ", provider=" + provider + ", pool=" + pool + '}';
	}

}
