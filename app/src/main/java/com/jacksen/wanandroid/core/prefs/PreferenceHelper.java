package com.jacksen.wanandroid.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.app.WanAndroidApp;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： PreferenceImpl 的实现
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class PreferenceHelper implements PreferenceImpl {

    private SharedPreferences mPreferences;

    @Inject
    public PreferenceHelper() {
        if (mPreferences == null) {
            mPreferences = WanAndroidApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        }
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }
    
    @Override
    public void setLoginPassword(String password) {
        mPreferences.edit().putString(Constants.PASSWORD, password).apply();
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public void setLoginState(boolean isLogin) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public boolean getLoginState() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public void setCookie(String domain, String cookie) {
        mPreferences.edit().putString(domain, cookie).apply();
    }

    @Override
    public String getCookie(String domain) {
        return mPreferences.getString(Constants.COOKIE, "");
    }

    @Override
    public void setCurrentPage(int position) {
        mPreferences.edit().putInt(Constants.CURRENT_PAGE, position).apply();
    }

    @Override
    public int getCurrentPage() {
        return mPreferences.getInt(Constants.CURRENT_PAGE, 0);
    }

    @Override
    public void setProjectCurrentPage(int position) {
        mPreferences.edit().putInt(Constants.PROJECT_CURRENT_PAGE, position).apply();
    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferences.getInt(Constants.PROJECT_CURRENT_PAGE, 0);
    }

    @Override
    public boolean isNoCacheModel() {
        return mPreferences.getBoolean(Constants.AUTO_CACHE_STATE, true);
    }

    @Override
    public boolean isNoImageModel() {
        return mPreferences.getBoolean(Constants.NO_IMAGE_STATE, false);
    }

    @Override
    public boolean isNightMode() {
        return mPreferences.getBoolean(Constants.NIGHT_MODE_STATE, false);
    }

    @Override
    public void setNightMode(boolean nightModel) {
        mPreferences.edit().putBoolean(Constants.NIGHT_MODE_STATE, nightModel).apply();
    }

    @Override
    public void setNoImageModel(boolean b) {
        mPreferences.edit().putBoolean(Constants.NO_IMAGE_STATE, b).apply();
    }

    @Override
    public void setNoCacheModel(boolean b) {
        mPreferences.edit().putBoolean(Constants.AUTO_CACHE_STATE, b).apply();
    }


}
