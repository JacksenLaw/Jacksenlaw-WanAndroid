package com.jacksen.wanandroid.core.prefs;

import com.jacksen.wanandroid.app.Constants;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： PreferenceHelper 的实现
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class PreferenceImpl implements PreferenceHelper {

    private PreferenceUtils mPreferences;

    @Inject
    public PreferenceImpl() {
        if (mPreferences == null) {
//            mPreferences = new SPUtils(Constants.MY_SHARED_PREFERENCE);
            mPreferences = PreferenceUtils.getInstance();
            //WanAndroidApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        }
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferences.putString(Constants.ACCOUNT, account, PreferenceUtils.TIME_DAY * 7);
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferences.putString(Constants.PASSWORD, password, PreferenceUtils.TIME_DAY * 7);
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public void setLoginState(boolean isLogin) {
        mPreferences.putBoolean(Constants.LOGIN_STATUS, isLogin, PreferenceUtils.TIME_DAY * 7);
    }

    @Override
    public boolean isLogin() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public void setCookie(String domain, String cookie) {
        mPreferences.putString(domain, cookie);
    }

    @Override
    public String getCookie(String domain) {
        return mPreferences.getString(Constants.COOKIE, "");
    }

    @Override
    public void setCurrentPage(int position) {
        mPreferences.putInt(Constants.CURRENT_PAGE, position);
    }

    @Override
    public int getCurrentPage() {
        return mPreferences.getInt(Constants.CURRENT_PAGE, 0);
    }

    @Override
    public void setProjectCurrentPage(int position) {
        mPreferences.putInt(Constants.PROJECT_CURRENT_PAGE, position);
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
        mPreferences.putBoolean(Constants.NIGHT_MODE_STATE, nightModel);
    }

    @Override
    public void setNoImageModel(boolean b) {
        mPreferences.putBoolean(Constants.NO_IMAGE_STATE, b);
    }

    @Override
    public void setNoCacheModel(boolean b) {
        mPreferences.putBoolean(Constants.AUTO_CACHE_STATE, b);
    }


}
