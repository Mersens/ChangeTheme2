package com.mersens.changetheme;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mersens on 2017/1/12 09:58
 * Email:626168564@qq.com
 */

public class PrefUtils {
    private Context mContext;
    public PrefUtils(Context context){
        this.mContext=context;
    }

    public void savePluginPath(String path){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PATH,path).apply();

    }
    public void savePluginPkg(String pkg){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PKG,pkg).apply();

    }


    public String getPluginPath(){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PATH,"");

    }

    public String getPluginPkg(){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PKG,"");

    }
    public void saveSuffix(String pkg){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.SUFFIX,pkg).apply();

    }


    public String getSuffix(){
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.SUFFIX,"");

    }

    public void clear() {
        SharedPreferences sp= mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
