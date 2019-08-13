package com.jacksen.wanandroid.app.receiver.observer;

import com.jacksen.wanandroid.util.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/1/21 0021
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NetObserverManager implements SubjectNetInterface {

    private List<SubjectNetObserver> mObservers = new ArrayList<>();

    private static NetObserverManager mInstance;

    public static NetObserverManager getInstance() {
        if (null == mInstance) {
            synchronized (NetObserverManager.class) {
                if (null == mInstance) {
                    mInstance = new NetObserverManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void addObserver(SubjectNetObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void notifyObservers(boolean isConnected) {
        for (SubjectNetObserver mObserver : mObservers) {
            KLog.i(mObserver.toString());
            mObserver.onNetWorkChange(isConnected);
        }
    }

    @Override
    public void removeObserver(SubjectNetObserver observer) {
        mObservers.remove(observer);
    }
}
