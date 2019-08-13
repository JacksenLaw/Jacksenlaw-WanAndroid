package com.jacksen.wanandroid.app.receiver.observer;

/**
 * 作者： LuoM
 * 时间： 2019/1/21 0021
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface SubjectNetObserver {
    /**
     * 通知观察者网络已变化
     * @param isConnected isConnected
     */
    void onNetWorkChange(boolean isConnected);
}
