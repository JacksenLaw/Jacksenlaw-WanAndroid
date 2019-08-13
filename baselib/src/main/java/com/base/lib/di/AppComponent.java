package com.base.lib.di;

import com.base.lib.app.BaseApplication;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * 作者： LuoM
 * 时间： 2019/5/2 0002
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Singleton
@Component(modules = {AppModule.class,HttpModule.class})
public interface AppComponent {

    Retrofit providesRetrofit();

    BaseApplication providesApplication();

}
