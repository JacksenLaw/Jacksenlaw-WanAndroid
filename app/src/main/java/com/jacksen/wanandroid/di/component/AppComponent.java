package com.jacksen.wanandroid.di.component;

import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.di.module.AbstractAllActivityModule;
import com.jacksen.wanandroid.di.module.AbstractAllFragmentModule;
import com.jacksen.wanandroid.di.module.AppModule;
import com.jacksen.wanandroid.di.module.HttpModule;
import com.jacksen.wanandroid.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AbstractAllActivityModule.class,
        AbstractAllFragmentModule.class,
        AppModule.class,
        HttpModule.class})
public interface AppComponent {
    /**
     * 注入MyApp实例
     *
     * @param myApp myApp
     */
    void inject(WanAndroidApp myApp);

    /**
     * 提供App的Context
     *
     * @return GeeksApp context
     */
    WanAndroidApp getContext();

    /**
     * 数据中心
     *
     * @return DataManager
     */
    DataManager getDataManager();
}
