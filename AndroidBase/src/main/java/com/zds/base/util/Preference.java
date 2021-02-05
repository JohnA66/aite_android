package com.zds.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 作   者：赵大帅
 * 描   述: 应用程序配置保存数据
 * 日   期: 2017/9/6 17:08
 * 更新日期: 2017/9/6
 */
public class Preference {

    public static boolean getBoolPreferences(Context context, String key,
                                             boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(key, defValue);
    }

    public static int getIntPreferences(Context context, String key,
                                        int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                key, defValue);
    }

    public static long getLongPreferences(Context context, String key,
                                          long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(
                key, defValue);
    }

    public static String getStringPreferences(Context context, String key,
                                              String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, defValue);
    }

    public static void saveBoolPreferences(Context context, String key,
                                           boolean defValue) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, defValue);
        editor.commit();
    }

    public static void saveIntPreferences(Context context, String key,
                                          int defValue) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putInt(key, defValue);
        editor.commit();

    }

    public static void saveLongPreferences(Context context, String key,
                                           long defValue) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putLong(key, defValue);
        editor.commit();
    }

    public static void saveStringPreferences(Context context, String key,
                                             String defValue) {

        if (context != null) {
            SharedPreferences.Editor editor = PreferenceManager
                    .getDefaultSharedPreferences(context).edit();
            editor.putString(key, defValue);
            editor.commit();
        }
    }
}
