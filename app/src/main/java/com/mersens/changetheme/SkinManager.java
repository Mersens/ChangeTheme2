package com.mersens.changetheme;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * Created by Mersens on 2017/1/10 16:41
 * Email:626168564@qq.com
 */

public class SkinManager {
    ResourceManager mResourceManager;
    private Context mContext;
    private static SkinManager sInstance;
    private SkinManager(){
    }
    public static SkinManager getInstance(){
        if(sInstance==null){
            synchronized (SkinManager.class){
                if(sInstance==null){
                    sInstance=new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context){
        mContext=context.getApplicationContext();

    }

    public  ResourceManager getmResourceManager(){
        return mResourceManager;
    }
    private void loadPlugin(String skin_plugin_path, String skin_pkg) {
        try {
            AssetManager assetManager=AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,skin_plugin_path);
            Resources resource=mContext.getResources();
            Resources mResource=new Resources(assetManager,resource.getDisplayMetrics(),resource.getConfiguration());
            mResourceManager=new ResourceManager(mResource,skin_pkg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
