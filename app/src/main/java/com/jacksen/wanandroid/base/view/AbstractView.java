package com.jacksen.wanandroid.base.view;

import android.view.View;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： view 基类
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface AbstractView {

    /**
     * Use night mode
     *
     * @param isNightMode if is night mode
     */
    void useNightModel(boolean isNightMode);

    /**
     * Show error message
     *
     * @param errorMsg error message
     */
    void showErrorMsg(String errorMsg);

    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    /**
     * Reload
     */
    void reload();

    /**
     * Show toast
     *
     * @param message Message
     */
    void showToast(String message);

    /**
     * Show snackBar
     *
     * @param view view
     * @param message Message
     */
    void showSnackBar(View view, String message);

    void showLoginView();

    void showLoginOutView();

    View getRootView();
}
