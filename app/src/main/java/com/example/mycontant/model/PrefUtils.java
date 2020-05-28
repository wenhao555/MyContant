package com.example.mycontant.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ywh on 2018/4/26.
 */

public class PrefUtils {
    public static final String PREF_NAME = "config";


    //text
    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        return sp.getString(key, defaultValue);

    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }


}
