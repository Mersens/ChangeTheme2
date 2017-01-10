package com.mersens.changetheme;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mersens on 2017/1/10 16:24
 * Email:626168564@qq.com
 */

public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(AttributeSet attributeSet, Context context){
        List<SkinAttr> mSkinAttrs=new ArrayList<SkinAttr>();
        SkinAttrType mSkinAttrType=null;
        SkinAttr mSkinAttr=null;
        for(int i=0,n=attributeSet.getAttributeCount();i<n;i++){
            String attrName=attributeSet.getAttributeName(i);
            String attrVal=attributeSet.getAttributeValue(i);
            if(attrVal.startsWith("@")){
                int id=Integer.parseInt(attrVal.substring(1));
                String resName=context.getResources().getResourceEntryName(id);
                if(resName.startsWith(Const.SKIN_PREFIX)){
                    mSkinAttrType=getSupportAttrType(attrName);
                    if(mSkinAttrType==null) break;
                    mSkinAttr=new SkinAttr(resName,mSkinAttrType);
                    mSkinAttrs.add(mSkinAttr);
                }
            }
        }
        return mSkinAttrs;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for(SkinAttrType type:SkinAttrType.values()){
            if(type.getResType().equals(attrName)){
                return type;
            }
        }
        return  null;
    }
}
