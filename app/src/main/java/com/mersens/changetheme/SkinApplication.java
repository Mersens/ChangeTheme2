package com.mersens.changetheme;

import android.app.Application;

/**
 * Created by Mersens on 2017/1/12 09:41
 * Email:626168564@qq.com
 */

public class SkinApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);

    }
}
