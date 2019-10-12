package com.jacksen.wanandroid.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.utils.Utils;
import com.jacksen.aspectj.core.login.ILogin;
import com.jacksen.aspectj.core.login.LoginSDK;
import com.jacksen.wanandroid.BuildConfig;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.receiver.NetWorkBroadcastReceiver;
import com.jacksen.wanandroid.base.lifecycle.AppLifecycleCallback;
import com.jacksen.wanandroid.di.component.AppComponent;
import com.jacksen.wanandroid.di.component.DaggerAppComponent;
import com.jacksen.wanandroid.di.module.AppModule;
import com.jacksen.wanandroid.di.module.HttpModule;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import org.litepal.LitePal;

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

        initLoginSdk();

    }

    private void initLoginSdk() {
        LoginSDK.getInstance().init(this, new ILogin() {
            @Override
            public void login(Context applicationContext, int userDefine) {
                switch (userDefine) {
                    case 0:
                        CommonUtils.showMessage(getString(R.string.login_tint));
                        Intent intent = new Intent(applicationContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case 1:
                        CommonUtils.showMessage(getString(R.string.login_tint));
                        break;
                    case 2:
                        CommonUtils.showMessage(getString(R.string.login_tint));
//                        new AlertDialog.Builder(MyApplication.this)...
                        break;
                    default:
                        CommonUtils.showMessage(getString(R.string.login_tint));
                        break;
                }
            }

            @Override
            public boolean isLogin(Context applicationContext) {
                return getAppComponent().getDataManager().isLogin();
            }

            @Override
            public void clearLoginState(Context applicationContext) {
                getAppComponent().getDataManager().setLoginState(false);
                getAppComponent().getDataManager().setLoginAccount("");
                getAppComponent().getDataManager().setLoginPassword("");
            }
        });
    }

    private void initSkinConfig() {
        SkinManager.getInstance().init(this);
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(BuildConfig.DEBUG);
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
}
