package com.jacksen.wanandroid.widget.webview.impl;

/**
 * 作者： LuoM
 * 时间： 2019/8/11
 * 描述：
 * 版本： v1.0.0
 * 更新时间： 2019/8/11
 * 本次更新内容：
 */
public class WebConfigImpl implements WebConfig {

    private boolean isNightModel;
    private boolean isNoImageModel;
    private boolean isNoCacheModel;

    @Override
    public void setNightModel(boolean nightModel) {
        isNightModel = nightModel;
    }

    @Override
    public boolean isNightModel() {
        return isNightModel;
    }

    @Override
    public void setNoImageModel(boolean noImageModel) {
        isNoImageModel = noImageModel;
    }

    @Override
    public boolean isNoImageModel() {
        return isNoImageModel;
    }

    @Override
    public void setNoCacheModel(boolean noCacheModel) {
        isNoCacheModel = noCacheModel;
    }

    @Override
    public boolean isNoCacheModel() {
        return isNoCacheModel;
    }
}
