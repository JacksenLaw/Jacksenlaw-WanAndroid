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
     * 显示正常页面
     */
    void showNormal();

    /**
     * 显示错误页面
     */
    void showError();

    /**
     * 显示加载页面
     */
    void showLoading();

    /**
     * 重载页面
     */
    void reload();

    /**
     * 显示toast消息
     *
     * @param message Message
     */
    void showToast(String message);

    /**
     * 显示SnackBar消息
     *
     * @param view view
     * @param message Message
     */
    void showSnackBar(View view, String message);

    /**
     * 获取根布局
     * @return view
     */
    View getRootView();
}
