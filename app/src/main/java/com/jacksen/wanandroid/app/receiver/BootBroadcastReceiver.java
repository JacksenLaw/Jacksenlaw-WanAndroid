package com.jacksen.wanandroid.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jacksen.wanandroid.util.KLog;

import java.util.Objects;

/**
 * 监听开机广播
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    public void onReceive(Context context, Intent intent) {
        KLog.i("BootBroadcastReceiver:" + intent.toString());
        KLog.i("BootBroadcastReceiver: this BroadcastReceiver is received");
        if (Objects.equals(intent.getAction(), ACTION)) {
            KLog.i("BootBroadcastReceiver: SystemDownloadService is start");
//            ServiceManager.startSystemDownloadService(context);
        }
//        ServiceManager.startService(context);
    }

}
