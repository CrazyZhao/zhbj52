package com.itheima.zhbj52.utils;

import android.content.Context;

/**
 * 缓存(Json) (URL,Json数据)
 * 
 * @author baoliang.zhao
 * 
 */
public class CacheUtils {

	/**
	 * 设置缓存 key是url,value是json
	 */
	public static void setCache(Context ctx, String key, String json) {
		PrefUtils.setString(ctx, key, json);
	}

	/**
	 * 获取缓存
	 */
	public static String getCache(Context ctx, String key) {
		return PrefUtils.getString(ctx, key, null);
	}
}
