package com.ums.android.activity;


import android.app.Application;

import android.content.Context;
import android.support.v7.appcompat.BuildConfig;

import org.xutils.x;

public class UMSApplication extends Application {

    private final static String TAG = "UMSApplication";
    public static UMSApplication appContext;
    public static UMSApplication getInstance(){
        // 这里不用判断instance是否为空
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

}
