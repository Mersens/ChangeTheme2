package com.mersens.changetheme;

/**
 * Created by Mersens on 2017/1/12 09:24
 * Email:626168564@qq.com
 */

public interface ISkinChangeCallBack {

   void onStart();
    void onError(Exception e);
    void onComplate();

    public static DefaultISkinChangeCallBack DEFAULT_CALLBACK=new DefaultISkinChangeCallBack();

    public class DefaultISkinChangeCallBack implements ISkinChangeCallBack{

        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onComplate() {

        }
    }

}
