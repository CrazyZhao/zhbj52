package com.itheima.zhbj52.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * 
 * @author baoliang.zhao
 * 
 */
public class PrefUtils {

	public static final String PREF_NAME = "config";

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	public static void setBoolean(Context ctx, String key, boolean value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public static int getInt(Context ctx, String key, int defaultValue) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static void setInt(Context ctx, String key, int value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
}
