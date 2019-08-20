package com.jacksen.wanandroid.core.prefs;

import java.io.Serializable;

/**
 * 作者： LuoM
 * 创建时间：2019/8/20/0020
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/20/0020
 * 更新内容：
 */
public class PreferenceModel<T> implements Serializable {
    private int saveTime;
    private T value;
    private long currentTime;

    public PreferenceModel() {
    }

    public PreferenceModel(int saveTime, T value, long currentTime) {
        this.saveTime = saveTime;
        this.value = value;
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(int saveTime) {
        this.saveTime = saveTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
