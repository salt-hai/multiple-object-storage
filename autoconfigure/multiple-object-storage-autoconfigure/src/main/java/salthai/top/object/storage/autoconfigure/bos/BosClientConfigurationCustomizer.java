package salthai.top.object.storage.autoconfigure.bos;

import com.baidubce.services.bos.BosClientConfiguration;

/**
 * 用于自定义配置bos客户端的接口
 *
 * @author Kuang HaiBo 2024/6/21 17:07
 */
public interface BosClientConfigurationCustomizer {

	/**
	 * Customize the {@link BosClientConfiguration},使用该配置可以自定义bos的配置
	 * @param bosClientConfiguration the builder to customize
	 */
	void customize(BosClientConfiguration bosClientConfiguration);

}
