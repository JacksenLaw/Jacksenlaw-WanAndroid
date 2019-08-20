package com.jacksen.wanandroid.core.prefs;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 本地存储
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface PreferenceHelper {

    /**
     * Set login account
     *
     * @param account Account
     */
    void setLoginAccount(String account);

    /**
     * Set login password
     *
     * @param password Password
     */
    void setLoginPassword(String password);

    /**
     * Get login account
     *
     * @return account
     */
    String getLoginAccount();

    /**
     * Get login password
     *
     * @return password
     */
    String getLoginPassword();

    /**
     * Set login status
     *
     * @param isLogin IsLogin
     */
    void setLoginState(boolean isLogin);

    /**
     * Get login status
     *
     * @return login status
     */
    boolean isLogin();

    /**
     * Set cookie
     *
     * @param domain Domain
     * @param cookie Cookie
     */
    void setCookie(String domain, String cookie);

    /**
     * Get cookie
     *
     * @param domain Domain
     * @return cookie
     */
    String getCookie(String domain);

    /**
     * Set current page
     *
     * @param position Position
     */
    void setCurrentPage(int position);

    /**
     * Get current page
     *
     * @return current page
     */
    int getCurrentPage();

    /**
     * Set project current page
     *
     * @param position Position
     */
    void setProjectCurrentPage(int position);

    /**
     * Get project current page
     *
     * @return current page
     */
    int getProjectCurrentPage();

    /**
     * 获取自动缓存
     * @return boolean
     */
    boolean isNoCacheModel();

    /**
     * Get no image state
     *
     * @return if has image state
     */
    boolean isNoImageModel();

    /**
     * Get night mode state
     *
     * @return if is night mode
     */
    boolean isNightMode();

    /**
     * Set night mode state
     *
     * @param b current night mode state
     */
    void setNightMode(boolean b);

    /**
     * Set no image state
     *
     * @param b current no image state
     */
    void setNoImageModel(boolean b);

    /**
     * Set auto cache state
     *
     * @param b current auto cache state
     */
    void setNoCacheModel(boolean b);

}
