package com.jacksen.wanandroid.view.ui.main.web;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultDownloadImpl;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebListenerManager;

/**
 * 作者： LuoM
 * 时间： 2019/8/17
 * 描述：
 * 版本： v1.0.0
 * 更新时间： 2019/8/17
 * 本次更新内容：
 */
public class CustomSettings extends AbsAgentWebSettings {

    private boolean isNightMode;
    private boolean isNoImageModel;
    private boolean isNoCacheModel;

    public CustomSettings(boolean isNightMode, boolean isNoImageModel, boolean isNoCacheModel) {
        super();
        this.isNightMode = isNightMode;
        this.isNoImageModel = isNoImageModel;
        this.isNoCacheModel = isNoCacheModel;
    }

    @Override
    protected void bindAgentWebSupport(AgentWeb agentWeb) {

    }


    @Override
    public IAgentWebSettings toSetting(WebView webView) {
        super.toSetting(webView);
        if (isNightMode) {
            webView.setBackgroundColor(ContextCompat.getColor(WanAndroidApp.getInstance(), R.color.colorWeb_Night));
        }
        if (isNoImageModel) {
            getWebSettings().setLoadsImagesAutomatically(false);// 不支持自动加载图片
            getWebSettings().setBlockNetworkImage(true);//阻塞加载网络图片  协议http or https
        } else {
            getWebSettings().setLoadsImagesAutomatically(true);// 支持自动加载图片
            getWebSettings().setBlockNetworkImage(false);//不阻塞加载网络图片  协议http or https
        }
        if (!isNoCacheModel) {
            getWebSettings().setAppCacheEnabled(false);// 支持启用缓存模式
            getWebSettings().setDomStorageEnabled(false);// 开启DOM缓存,默认状态下是不支持LocalStorage的
            getWebSettings().setDatabaseEnabled(false);// 开启数据库缓存
            getWebSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            getWebSettings().setAppCacheEnabled(true);// 支持启用缓存模式
            getWebSettings().setDomStorageEnabled(true);// 开启DOM缓存,默认状态下是不支持LocalStorage的
            getWebSettings().setDatabaseEnabled(true);// 开启数据库缓存
            if (NetworkUtils.isConnected()) {
                getWebSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                getWebSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }
        getWebSettings().setAllowFileAccess(false); //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWebSettings().setAllowFileAccessFromFileURLs(false); //通过 file mUrl 加载的 Javascript 读取其他的本地文件 .建议关闭
            getWebSettings().setAllowUniversalAccessFromFileURLs(false);//允许通过 file mUrl 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        }
        getWebSettings().setNeedInitialFocus(true);
        getWebSettings().setSupportMultipleWindows(true);
        getWebSettings().setSupportZoom(true);
        getWebSettings().setLoadWithOverviewMode(true);
            getWebSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        getWebSettings().setDefaultFontSize(16);
        getWebSettings().setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        getWebSettings().setGeolocationEnabled(true);
        getWebSettings().setUserAgentString(getWebSettings().getUserAgentString().concat("agentweb/3.1.0"));
        return this;
    }

    @Override
    public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {
        return super.setDownloader(webView,
                DefaultDownloadImpl.create((Activity) webView.getContext()
                        , webView, mAgentWeb.getPermissionInterceptor()));
    }
}
