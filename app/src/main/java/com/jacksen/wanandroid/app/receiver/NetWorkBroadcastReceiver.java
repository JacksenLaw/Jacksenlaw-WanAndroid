package com.jacksen.wanandroid.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jacksen.wanandroid.app.receiver.observer.NetObserverManager;
import com.jacksen.wanandroid.util.KLog;

/**
 * 作者： LuoM
 * 时间： 2019/1/21 0021
 * 描述： 网络状态变化的广播
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        KLog.i("==================================NetWorkBroadcastReceiver==============================");
        NetObserverManager.getInstance().notifyObservers(NetworkUtils.isConnected());
    }

}
