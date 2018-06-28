package com.ums.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences类
 */
public class Preferences {

	/**
	 * shared文件名叫parenting.xml
	 */
	private static String SHARED_NAME = "parenting";

	/**
	 * 获取SharedPreference对象
	 *
	 * @return
	 */
	private static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	/**
	 * 获String键值
	 * @param key
	 * @return
	 */
	public static String getString(String key, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		return sharedPrefrence.getString(key, "");
	}

	/**
	 * 获String键值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultValue, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		return sharedPrefrence.getString(key, defaultValue);
	}

	/**
	 * 设置string键
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putString(String key, String value, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Editor editor = sharedPrefrence.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	/**
	 * 获取int键
	 * @param key
	 * @return
	 */
	public static int getInt(String key, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		return sharedPrefrence.getInt(key, 0);
	}

	/**
	 * 设置int键
	 * @param key
	 * @param value
	 * @return
	 */

	public static boolean putInt(String key, int value, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Editor editor = sharedPrefrence.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * 获取boolean键
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		return sharedPrefrence.getBoolean(key, false);
	}

	/**
	 * 设置boolean键
	 * 
	 * @param key
	 * @param value
	 */
	public static boolean putBoolean(String key, boolean value, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Editor editor = sharedPrefrence.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * 移除boolean键
	 * @param key
	 * @return
	 */
	public static boolean remove(String key, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Editor editor = sharedPrefrence.edit();
		editor.remove(key);
		return editor.commit();
	}

	public static Set<String> getStringSet(String key, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Set<String> stringSet = new HashSet<String>();
		return sharedPrefrence.getStringSet(key, stringSet);
	}
	public static boolean putStringSet(String key,Set<String> stringSet, Context context) {
		SharedPreferences sharedPrefrence = getSharedPreferences(context);
		Editor editor = sharedPrefrence.edit();
		editor.putStringSet(key,stringSet);
		return editor.commit();
	}

}
