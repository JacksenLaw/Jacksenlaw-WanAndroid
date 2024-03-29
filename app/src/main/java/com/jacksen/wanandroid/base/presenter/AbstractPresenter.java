package com.jacksen.wanandroid.base.presenter;

import android.arch.lifecycle.LifecycleOwner;

import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： presenter 基类
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface AbstractPresenter<T extends AbstractView> extends LifecycleOwner {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 注入lifecycle，处理view层生命周期
     *
     * @param lifecycleOwner lifecycleOwner
     */
    void injectLifecycleOwner(LifecycleOwner lifecycleOwner);

    /**
     * 注册事件
     */
    void injectEvent();

    /**
     * 回收View
     */
    void detachView();

    /**
     * 显示正在加载的view
     */
    void showLoadingView();


    /**
     * 设置夜间模式
     *
     * @param nightModel nightModel
     */
    void setNightMode(boolean nightModel);

    /**
     * 获取夜间模式
     *
     * @return if is night mode
     */
    boolean isNightMode();

    /**
     * 设置自动缓存
     *
     * @param autoCacheState autoCacheState
     */
    void setNoCacheModel(boolean autoCacheState);

    /**
     * 获取自动缓存设置
     *
     * @return if auto cache state
     */
    boolean isNoCacheModel();

    /**
     * 保存登录状态
     *
     * @param loginStatus login state
     */
    void setLoginState(boolean loginStatus);

    /**
     * 获取登录 状态
     *
     * @return if is login
     */
    boolean isLogin();

    /**
     * 设置无图模式
     */
    void setNoImageModel(boolean noImageState);

    /**
     * 是否是无图模式
     *
     * @return boolean
     */
    boolean isNoImageModel();

    /**
     * 保存账号
     *
     * @param account account
     */
    void setLoginAccount(String account);

    /**
     * 获取账号
     *
     * @return String
     */
    String getLoginAccount();

    /**
     * 设置当前页面下标
     *
     * @param page page
     */
    void setCurrentPage(int page);

    /**
     * 获取当前页面下标
     *
     * @return current page
     */
    int getCurrentPage();

}
