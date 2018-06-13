package com.oliveoa.common;

import android.app.Application;
import android.content.Context;

/**
 * 返回Context对象
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
