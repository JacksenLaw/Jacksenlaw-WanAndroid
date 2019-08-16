package com.jacksen.wanandroid.di.module;

import com.jacksen.wanandroid.di.component.BaseFragmentComponent;
import com.jacksen.wanandroid.di.module.fragment.CollectFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.FilterFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.HomePageFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.KnowledgeDetailFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.KnowledgeFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.NavigationFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.ProjectListFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.TodoCompletedFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.TodoFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.UsefulSitesDialogFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.WxFragmentModule;
import com.jacksen.wanandroid.di.module.fragment.WxListFragmentModule;
import com.jacksen.wanandroid.view.ui.knowledge.fragment.KnowledgeDetailFragment;
import com.jacksen.wanandroid.view.ui.knowledge.fragment.KnowledgeFragment;
import com.jacksen.wanandroid.view.ui.main.fragment.CollectFragment;
import com.jacksen.wanandroid.view.ui.main.fragment.UsefulSitesDialogFragment;
import com.jacksen.wanandroid.view.ui.mainpager.fragment.HomePageFragment;
import com.jacksen.wanandroid.view.ui.navi.fragment.NavigationFragment;
import com.jacksen.wanandroid.view.ui.project.fragment.ProjectFragment;
import com.jacksen.wanandroid.view.ui.project.fragment.ProjectListFragment;
import com.jacksen.wanandroid.view.ui.todo.fragment.FilterFragment;
import com.jacksen.wanandroid.view.ui.todo.fragment.TodoCompletedFragment;
import com.jacksen.wanandroid.view.ui.todo.fragment.TodoFragment;
import com.jacksen.wanandroid.view.ui.wx.fragment.WxFragment;
import com.jacksen.wanandroid.view.ui.wx.fragment.WxListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@SuppressWarnings("unused")
@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {

    @ContributesAndroidInjector(modules = HomePageFragmentModule.class)
    abstract HomePageFragment contributesHomePageFragmentInject();

    @ContributesAndroidInjector(modules = KnowledgeFragmentModule.class)
    abstract KnowledgeFragment contributesKnowledgeFragmentInject();

    @ContributesAndroidInjector(modules = ProjectFragmentModule.class)
    abstract ProjectFragment contributesProjectFragmentInject();

    @ContributesAndroidInjector(modules = CollectFragmentModule.class)
    abstract CollectFragment contributesCollectFragmentInject();

    @ContributesAndroidInjector(modules = WxFragmentModule.class)
    abstract WxFragment contributesWxFragmentInject();

    @ContributesAndroidInjector(modules = NavigationFragmentModule.class)
    abstract NavigationFragment contributesNavigationFragmentInject();

    @ContributesAndroidInjector(modules = UsefulSitesDialogFragmentModule.class)
    abstract UsefulSitesDialogFragment contributesUsefulSitesDialogFragmentInject();

    @ContributesAndroidInjector(modules = KnowledgeDetailFragmentModule.class)
    abstract KnowledgeDetailFragment contributesKnowledgeDetailFragmentInject();

    @ContributesAndroidInjector(modules = WxListFragmentModule.class)
    abstract WxListFragment contributesWxListFragmentInject();

    @ContributesAndroidInjector(modules = ProjectListFragmentModule.class)
    abstract ProjectListFragment contributesProjectListFragmentInject();

    @ContributesAndroidInjector(modules = TodoFragmentModule.class)
    abstract TodoFragment contributesTodoFragmentInject();

    @ContributesAndroidInjector(modules = TodoCompletedFragmentModule.class)
    abstract TodoCompletedFragment contributesTodoCompletedFragmentInject();

    @ContributesAndroidInjector(modules = FilterFragmentModule.class)
    abstract FilterFragment contributesFilterFragmentInject();

}
