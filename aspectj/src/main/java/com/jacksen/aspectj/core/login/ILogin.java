package com.jacksen.aspectj.core.login;

import android.content.Context;

/**
 * 作者： LuoM
 * 创建时间：2019/8/19/0019
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/19/0019
 * 更新内容：
 */
public interface ILogin {
    /**
     * 登录事件接收
     */
    void login(Context applicationContext, int userDefine);

    /**
     * 判断是否登录
     *
     * @return boolean
     */
    boolean isLogin(Context applicationContext);

    /**
     * 清除登录状态
     */
    void clearLoginState(Context applicationContext);
}
