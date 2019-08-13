package com.base.lib.presenter;

import com.base.lib.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： presenter 基类
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface AbstractPresenter<T extends AbstractView> {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * show loading view
     */
    void showLoadingView();

    /**
     * 回收View
     */
    void detachView();


    /**
     * 设置夜间模式
     *
     * @param nightState nightState
     */
    void setNightModeState(boolean nightState);

    /**
     * 获取夜间模式
     *
     * @return if is night mode
     */
    boolean getNightModeState();

    /**
     * 设置自动缓存
     *
     * @param autoCacheState autoCacheState
     */
    void setAutoCacheState(boolean autoCacheState);

    /**
     * 获取自动缓存设置
     *
     * @return if auto cache state
     */
    boolean getAutoCacheState();

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
    boolean getLoginState();

    /**
     * 设置无图模式
     */
    void setNoImageState(boolean noImageState);

    /**
     * 是否是无图模式
     *
     * @return boolean
     */
    boolean getNoImageState();

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

    void setCurrentPage(int page);

    /**
     * 获取当前页面下标
     *
     * @return current page
     */
    int getCurrentPage();

}
