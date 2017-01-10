package com.mersens.changetheme;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Mersens
 * @title SharePreferenceUtil
 * @description:SharePreference工具类，数据存储
 * @time 2016年4月6日
 */
public class SharePreferenceUtil {
    private static SharePreferenceUtil sp;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFERENCE_NAME = "_sharedinfo";
    private static final String INDEX = "index";

    private SharePreferenceUtil() {

    }

    public static synchronized SharePreferenceUtil getInstance(Context context) {
        if (sp == null) {
            sp = new SharePreferenceUtil();
            mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
        return sp;
    }

    public int getIndex() {
        return mSharedPreferences.getInt(INDEX, 0);
    }

    public void setIndex(int n) {
        editor.putInt(INDEX, n);
        editor.commit();
    }



}
