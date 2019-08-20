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
public class LoginSDK {
    private static LoginSDK instance;
    private Context applicationContext;

    private LoginSDK() {}

    public static LoginSDK getInstance() {
        if (instance == null) {
            synchronized (LoginSDK.class) {
                if (instance == null) {
                    instance = new LoginSDK();
                }
            }
        }
        return instance;
    }

    public void init(Context context, ILogin iLogin) {
        applicationContext = context.getApplicationContext();
        LoginAssistant.getInstance().setApplicationContext(context);
        LoginAssistant.getInstance().setLogin(iLogin);
    }

    /**
     * 单点登录，验证token失效的统一接入入口
     */
    public void serverTokenInvalidation(int userDefine) {
        LoginAssistant.getInstance().serverTokenInvalidation(userDefine);
    }
}
