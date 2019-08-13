package com.jacksen.wanandroid.di.module;

import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.core.prefs.PreferenceHelper;
import com.jacksen.wanandroid.core.prefs.PreferenceImpl;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.db.DbHelper;
import com.jacksen.wanandroid.model.db.DbHelperImpl;
import com.jacksen.wanandroid.model.http.HttpHelper;
import com.jacksen.wanandroid.model.http.HttpImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Module
public class AppModule {

    private final WanAndroidApp application;

    public AppModule(WanAndroidApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    WanAndroidApp provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpImpl provideHttpHelper(HttpHelper httpHelper) {
        return httpHelper;
    }

    @Provides
    @Singleton
    DbHelperImpl provideDbHelper(DbHelper dbHelper) {
        return dbHelper;
    }

    @Provides
    @Singleton
    PreferenceImpl providePreferenceHelper(PreferenceHelper httpHelper) {
        return httpHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(PreferenceImpl mPreferenceImpl, HttpImpl httpImpl, DbHelperImpl dbHelperImpl) {
        return new DataManager(mPreferenceImpl, httpImpl, dbHelperImpl);
    }

}
