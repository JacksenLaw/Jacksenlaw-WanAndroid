package com.base.lib.di;

import com.base.lib.app.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 作者： LuoM
 * 时间： 2019/5/2 0002
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Module
public class AppModule {
    private BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    BaseApplication providesApplication(){
        return application;
    }
}
