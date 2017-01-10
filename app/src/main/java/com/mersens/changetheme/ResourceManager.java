package com.mersens.changetheme;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by Mersens on 2017/1/10 15:14
 * Email:626168564@qq.com
 */

public class ResourceManager {

    private Resources mResource;
    private String mPkgName;

    public ResourceManager(Resources resources,String pkg){
        this.mResource=resources;
        this.mPkgName=pkg;
    }

    public Drawable getDrawableByName(String name){
        try {
            return mResource.getDrawable(mResource.getIdentifier(name,"drawable",mPkgName));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public ColorStateList getColorByName(String name){
        try {
            return  mResource.getColorStateList(mResource.getIdentifier(name,"color",mPkgName));

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
