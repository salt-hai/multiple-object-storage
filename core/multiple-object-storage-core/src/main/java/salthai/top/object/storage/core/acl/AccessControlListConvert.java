package salthai.top.object.storage.core.acl;

import salthai.top.object.storage.core.function.Converter;

/**
 * acl转换接口,由统一的acl枚举转换成对方的
 *
 * @param <Target> 代表对方acl 属性
 * @author Kuang HaiBo 2023/11/7 16:51
 */
public interface AccessControlListConvert<Target> extends Converter<AccessControlList, Target> {

}
