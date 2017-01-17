package com.mersens.changetheme;

import android.view.View;

import java.util.List;

/**
 * Created by Mersens on 2017/1/10 16:26
 * Email:626168564@qq.com
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mList;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mList=skinAttrs;
        this.mView=view;
    }


    public List<SkinAttr> getList() {
        return mList;
    }

    public void setList(List<SkinAttr> List) {
        this.mList = List;
    }

    public View getView() {
        return mView;
    }

    public void setView(View View) {
        this.mView = View;
    }
    public  void apply(){
        for(SkinAttr skinAttr:mList){
            skinAttr.apply(mView);
        }
    }

}
