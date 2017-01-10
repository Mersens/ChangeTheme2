package com.mersens.changetheme;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mersens on 2017/1/10 16:27
 * Email:626168564@qq.com
 */

public enum  SkinAttrType {

    BACKGROUND("background"){
        @Override
        public void apply(View view, String resName) {
            Drawable drawable=SkinManager.getInstance().getmResourceManager().getDrawableByName(resName);
            if(drawable!=null){
                view.setBackgroundDrawable(drawable);
            }
        }
    },SRC("src"){
        @Override
        public void apply(View view, String resName) {
            Drawable drawable=SkinManager.getInstance().getmResourceManager().getDrawableByName(resName);
            if(drawable!=null){
                if(view instanceof ImageView){
                    ImageView imageView=(ImageView) view;
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    },TEXT_COLOR("textColor"){
        @Override
        public void apply(View view, String resName) {
            ColorStateList color=SkinManager.getInstance().getmResourceManager().getColorByName(resName);
            if(color!=null){
                if(view instanceof TextView){
                    TextView textView=(TextView) view;
                    textView.setTextColor(color);
                }
            }
        }
    };

    String resType;
    SkinAttrType(String type){
        resType=type;
    }
    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }



    public abstract void apply(View view, String resName);



}
