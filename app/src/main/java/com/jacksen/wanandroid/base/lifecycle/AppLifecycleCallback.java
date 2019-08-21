package com.jacksen.wanandroid.base.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jacksen.wanandroid.core.manager.ActivityCollector;
import com.jacksen.wanandroid.util.KLog;

import java.util.Objects;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： ActivityLifecycleCallbacks
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class AppLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    private static final int APP_STATUS_UNKNOWN = -1;
    private static final int APP_STATUS_LIVE = 0;

    private int appStatus = APP_STATUS_UNKNOWN;

    private boolean isForeground = true;
    private int appCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        KLog.i(activity.getClass().getSimpleName() + " is created");
        //Activity的管理，将Activity压入栈
        ActivityCollector.addActivity(activity);

        if (appStatus == APP_STATUS_UNKNOWN) {
            appStatus = APP_STATUS_LIVE;
            startLauncherActivity(activity);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
            KLog.i("localTime --> " + savedInstanceState.getLong("localTime"));
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        KLog.d(activity.getClass().getSimpleName() + " is started");
        appCount++;
        if (!isForeground) {
            isForeground = true;
            KLog.i("app into foreground");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        KLog.i(activity.getClass().getSimpleName() + " is resumed");
        ActivityCollector.setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        KLog.i(activity.getClass().getSimpleName() + " is paused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        KLog.i(activity.getClass().getSimpleName() + " is stopped");
        appCount--;
        if (!isForGroundAppValue()) {
            isForeground = false;
            KLog.i("app into background ");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        KLog.i(activity.getClass().getSimpleName() + " is saveInstanceState");
        outState.putBoolean("saveStateKey", true);
        outState.putLong("localTime", System.currentTimeMillis());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        KLog.i(activity.getClass().getSimpleName() + " is destroyed");
        ActivityCollector.finishActivity(activity);
    }

    private boolean isForGroundAppValue() {
        return appCount > 0;
    }

    private void startLauncherActivity(Activity activity) {
        try {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            assert launchIntent != null;
            String launcherClassName;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                launcherClassName = Objects.requireNonNull(launchIntent.getComponent()).getClassName();
            } else {
                assert launchIntent.getComponent() != null;
                launcherClassName = launchIntent.getComponent().getClassName();
            }
            String className = activity.getComponentName().getClassName();

            if (TextUtils.isEmpty(launcherClassName) || launcherClassName.equals(className)) {
                return;
            }

            KLog.i("launcher ClassName --> " + launcherClassName);
            KLog.i("current ClassName --> " + className);

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(launchIntent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
