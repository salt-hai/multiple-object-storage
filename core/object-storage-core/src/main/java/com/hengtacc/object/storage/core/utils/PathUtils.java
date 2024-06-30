package com.hengtacc.object.storage.core.utils;

import java.util.Objects;

/**
 * 路径工具
 *
 * @author Kuang HaiBo 2023/10/31 11:37
 */
public class PathUtils {

	/**
	 * 文件路径拼接
	 * @param first 路径
	 * @param more 子路径/或文件
	 * @return 拼接结果 eg： 兼容 win first: /user/user-info/ or /user/user-info more： user.png
	 * or /user.png result: /user/user-info/user.png
	 */
	public static String join(String first, String... more) {
		String path;
		if (Objects.isNull(more) || more.length == 0) {
			path = first;
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append(first);
			for (String segment : more) {
				if (segment.startsWith("/")) {
					segment = segment.substring(1);
				}
				if (!segment.isEmpty()) {
					if (sb.toString().endsWith("/")) {
						sb.append(segment);
					}
					else {
						sb.append('/').append(segment);
					}
				}
			}
			path = sb.toString();
		}
		return path;
	}

}
