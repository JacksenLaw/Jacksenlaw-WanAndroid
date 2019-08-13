package com.jacksen.wanandroid.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.utils.Utils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.receiver.NetWorkBroadcastReceiver;
import com.jacksen.wanandroid.base.lifecycle.AppLifecycleCallback;
import com.jacksen.wanandroid.di.component.AppComponent;
import com.jacksen.wanandroid.di.component.DaggerAppComponent;
import com.jacksen.wanandroid.di.module.AppModule;
import com.jacksen.wanandroid.di.module.HttpModule;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： WanAndroidApp
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WanAndroidApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    private static volatile AppComponent appComponent;

    private static WanAndroidApp instance;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            layout.setPrimaryColors(getInstance().getResources().getColor(R.color.colorPrimary));
            //指定为Delivery Header，默认是贝塞尔雷达Header
            return new DeliveryHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //默认是 BallPulseFooter
            return new BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary));
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSkinConfig();

        registerActivityLifecycleCallbacks(new AppLifecycleCallback());

        sendBroadcast();

        instance = this;

        DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build().inject(this);

        Utils.init(this);

        LitePal.initialize(this);

    }

    private void initSkinConfig() {
        SkinManager.getInstance().init(this);
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(true);
//        SkinConfig.addSupportAttr("tabLayoutIndicator", new TabLayoutIndicatorAttr());
//        SkinConfig.addSupportAttr("button", new RadioButtonAttr());
        SkinConfig.enableGlobalSkinApply();
    }

    private void sendBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGE");
        intentFilter.addAction("android.net.conn.STATE_CHANGE");
        registerReceiver(new NetWorkBroadcastReceiver(), intentFilter);
    }

    /**
     * 可使用此方法获取未在注入范围内的AppComponent中的数据
     *
     * @return AppComponent
     */
    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    public static synchronized WanAndroidApp getInstance() {
        return instance;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

    /**
     * 从assets目录下拷贝文件
     *
     * @param context            上下文
     * @param assetsFilePath     文件的路径名如：SBClock/0001cuteowl/cuteowl_dot.png
     * @param targetFileFullPath 目标文件路径如：/sdcard/SBClock/0001cuteowl/cuteowl_dot.png
     */
    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);
            copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(InputStream in, String targetPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
