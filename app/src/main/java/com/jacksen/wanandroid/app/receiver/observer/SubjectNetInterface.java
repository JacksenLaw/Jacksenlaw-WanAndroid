package com.jacksen.wanandroid.app.receiver.observer;

/**
 * 作者： LuoM
 * 时间： 2019/1/21 0021
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface SubjectNetInterface {
    /**
     * 添加观察者
     *
     * @param observer observer
     */
    void addObserver(SubjectNetObserver observer);

    /**
     * 通知所有观察者
     */
    void notifyObservers(boolean hasNet);

    /**
     * 移除观察者
     *
     * @param observer observer
     */
    void removeObserver(SubjectNetObserver observer);
}
