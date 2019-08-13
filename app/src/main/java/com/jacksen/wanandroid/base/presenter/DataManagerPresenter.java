package com.jacksen.wanandroid.base.presenter;

import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.model.DataManager;

/**
 * 作者： LuoM
 * 时间： 2019/3/31 0031
 * 描述： 当前app数据
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class DataManagerPresenter<T extends AbstractView> implements AbstractPresenter<T> {

    protected DataManager dataManager;

    public DataManagerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 设置夜间模式
     *
     * @param nightModel nightModel
     */
    public void setNightMode(boolean nightModel) {
        dataManager.setNightMode(nightModel);
    }

    /**
     * 获取夜间模式
     *
     * @return if is night mode
     */
    public boolean isNightMode() {
        return dataManager.isNightMode();
    }

    /**
     * 设置自动缓存
     *
     * @param autoCacheState autoCacheState
     */
    public void setNoCacheModel(boolean autoCacheState) {
        dataManager.setNoCacheModel(autoCacheState);
    }

    /**
     * 获取自动缓存设置
     *
     * @return if auto cache state
     */
    public boolean isNoCacheModel() {
        return dataManager.isNoCacheModel();
    }

    /**
     * 保存登录状态
     *
     * @param loginStatus login state
     */
    public void setLoginState(boolean loginStatus) {
        dataManager.setLoginState(loginStatus);
    }

    /**
     * 获取登录 状态
     *
     * @return if is login
     */
    public boolean getLoginState() {
        return dataManager.getLoginState();
    }

    /**
     * 设置无图模式
     */
    public void setNoImageModel(boolean noImageState) {
        dataManager.setNoImageModel(noImageState);
    }

    /**
     * 是否是无图模式
     *
     * @return boolean
     */
    public boolean isNoImageModel() {
        return dataManager.isNoImageModel();
    }

    /**
     * 保存账号
     *
     * @param account account
     */
    public void setLoginAccount(String account) {
        dataManager.setLoginAccount(account);
    }

    /**
     * 获取账号
     *
     * @return String
     */
    @Override
    public String getLoginAccount() {
        return dataManager.getLoginAccount();
    }

    /**
     * 设置当前该展示哪个fragment
     */
    @Override
    public void setCurrentPage(int page) {
        dataManager.setCurrentPage(page);
    }

    /**
     * 获取当前页面应该展示哪个fragment
     *
     * @return current page
     */
    @Override
    public int getCurrentPage() {
        return dataManager.getCurrentPage();
    }
}
