package com.hengtacc.object.storage.core.constants;

/**
 * 系统常量
 *
 * @author Kuang HaiBo 2023/11/3 10:24
 */
public class StorageConstants {

	/**
	 * 默认的媒体类型
	 */
	public final static String DEFAULT_MINE_TYPE = "application/octet-stream";

	/**
	 * 最小分片号
	 */
	public final static int MIN_PART_NUMBER = 1;

	/**
	 * 最大分片号
	 */
	public final static int MAX_PART_NUMBER = 10000;

	// ==============配置============
	/**
	 * 配置全局前缀
	 */
	public static final String PROPERTY_PREFIX = "hengtacc.object.storage";

	/**
	 * 供应商类型
	 * @see com.hengtacc.object.storage.core.enums.Provider
	 */
	public static final String ITEM_PROVIDER = PROPERTY_PREFIX + ".provider";

	/**
	 * 阿里云 oss 配置
	 */
	public static final String ALI_YUN_PROVIDER = PROPERTY_PREFIX + ".aliyun";

	/**
	 * 华为云 obs
	 */
	public static final String HUA_WEI_PROVIDER = PROPERTY_PREFIX + ".huawei";

	/**
	 * 百度 bos
	 */
	public static final String BAI_DU_PROVIDER = PROPERTY_PREFIX + ".baidu";

}
