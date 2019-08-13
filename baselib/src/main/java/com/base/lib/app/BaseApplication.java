package com.base.lib.app;

import android.app.Application;

/**
 * 作者： LuoM
 * 时间： 2019/5/2 0002
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
