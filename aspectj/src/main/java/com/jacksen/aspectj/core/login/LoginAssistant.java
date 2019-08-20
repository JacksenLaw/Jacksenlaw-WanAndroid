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
public class LoginAssistant {
    private LoginAssistant() {}

    private static LoginAssistant instance;

    public static LoginAssistant getInstance() {
        if (instance == null) {
            synchronized (LoginAssistant.class) {
                if (instance == null) {
                    instance = new LoginAssistant();
                }
            }
        }
        return instance;
    }

    private ILogin iLogin;

    public ILogin getLogin() {
        return iLogin;
    }

    public void setLogin(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    private Context applicationContext;

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void serverTokenInvalidation(int userDefine) {
        if (iLogin == null)
            return;
        iLogin.clearLoginState(applicationContext);
        iLogin.login(applicationContext, userDefine);
    }
}
