package com.jacksen.wanandroid.core.manager;

import android.app.Activity;
import android.os.Process;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ActivityCollector {

    private static Stack<Activity> activityStack = new Stack<Activity>();

    private static WeakReference<Activity> currentActivityWeakRef;

    public static void setCurrentActivity(Activity activity) {
        currentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (currentActivityWeakRef != null) {
            currentActivity = currentActivityWeakRef.get();
        }

        return currentActivity;
    }

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
//    public static Activity getCurrentActivity() {
//        return activityStack.lastElement();
//    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        try {
            finishAllActivity();
            Process.killProcess(Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}