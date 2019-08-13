package com.jacksen.wanandroid.di.module;

import com.jacksen.wanandroid.di.component.BaseActivityComponent;
import com.jacksen.wanandroid.di.module.activity.ArticleDetailActivityModule;
import com.jacksen.wanandroid.di.module.activity.KnowledgeHierarchyDetailActivityModule;
import com.jacksen.wanandroid.di.module.activity.LoginActivityModule;
import com.jacksen.wanandroid.di.module.activity.MainActivityModule;
import com.jacksen.wanandroid.di.module.activity.RegisterActivityModule;
import com.jacksen.wanandroid.di.module.activity.SearchActivityModule;
import com.jacksen.wanandroid.di.module.activity.SearchListActivityModule;
import com.jacksen.wanandroid.di.module.activity.SettingActivityModule;
import com.jacksen.wanandroid.di.module.activity.SplashActivityModule;
import com.jacksen.wanandroid.di.module.activity.TodoActivityModule;
import com.jacksen.wanandroid.di.scope.ActivityScope;
import com.jacksen.wanandroid.view.ui.knowledge.activity.KnowledgeDetailActivity;
import com.jacksen.wanandroid.view.ui.main.activity.AboutUsActivity;
import com.jacksen.wanandroid.view.ui.main.activity.ArticleDetailActivity;
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;
import com.jacksen.wanandroid.view.ui.main.activity.MainActivity;
import com.jacksen.wanandroid.view.ui.main.activity.RegisterActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SearchActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SearchListActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SettingActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SplashActivity;
import com.jacksen.wanandroid.view.ui.todo.activity.TodoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity contributesSplashActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributesRegisterActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributesLoginActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = ArticleDetailActivityModule.class)
    abstract ArticleDetailActivity contributesArticleDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = AboutUsActivityModule.class)
    abstract AboutUsActivity contributesAboutUsActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = SettingActivityModule.class)
    abstract SettingActivity contributesSettingActivityInject();

    @ActivityScope
    @ContributesAndroidInjector(modules = KnowledgeHierarchyDetailActivityModule.class)
    abstract KnowledgeDetailActivity contributesKnowledgeHierarchyDetailActivityInject();

    @ActivityScope
    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity contributesSearchActivityInject();

    @ActivityScope
    @ContributesAndroidInjector(modules = SearchListActivityModule.class)
    abstract SearchListActivity contributesSearchListActivityInject();

    @ContributesAndroidInjector(modules = TodoActivityModule.class)
    abstract TodoActivity contributesTodoActivityInject();
}
