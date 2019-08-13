package com.jacksen.wanandroid.widget.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.widget.webview.impl.WebConfigImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/8/11
 * 描述：
 * 版本： v1.0.0
 * 更新时间： 2019/8/11
 * 本次更新内容：
 */
public class SafeWebView extends WebView {

    private WebConfigImpl mWebConfig;
    private WebView mWebView;
    //承载WebView 的 ViewGroup
    private ViewGroup mViewGroup;
    private int mIndex = -1;

    private SafeWebViewClient safeWebViewClient;

    public SafeWebView(Context context) {
        super(context);
    }

    public SafeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SafeWebView(Context context, ViewGroup viewGroup, WebConfigImpl webConfig) {
        super(context);
        mWebView = this;
        mWebConfig = webConfig;
        mViewGroup = viewGroup;
        initWebView();
        initSettings();
    }

    public ViewGroup getViewGroup() {
        return mViewGroup;
    }

    private void initWebView() {

        if (mViewGroup != null) {
            if (mIndex == -1) {
                mViewGroup.addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mViewGroup.addView(mWebView, mIndex, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }

        //解决JS漏洞
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mWebView.removeJavascriptInterface("accessibility");
            mWebView.removeJavascriptInterface("accessibilityTraversal");
        }

        mWebView.setWebChromeClient(new SafeWebViewChromeClient());
        safeWebViewClient = new SafeWebViewClient(this, mWebConfig);
        mWebView.setWebViewClient(safeWebViewClient);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSettings() {
        WebSettings webSettings = super.getSettings();
        if (webSettings == null) return;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //4.2以下关闭JavascriptInterface，4.2 以上需要添加注解 @JavascriptInterface
            webSettings.setJavaScriptEnabled(false);// 支持 Js 使用
        } else {
            webSettings.setJavaScriptEnabled(true);// 支持 Js 使用
        }

        if (mWebConfig.isNightModel()) {
            mWebView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWeb_Night));
        }
        if (mWebConfig.isNoImageModel()) {
            webSettings.setLoadsImagesAutomatically(false); // 支持自动加载图片
            webSettings.setBlockNetworkImage(true);//是否阻塞加载网络图片  协议http or https
        } else {
            webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
            webSettings.setBlockNetworkImage(false);//是否阻塞加载网络图片  协议http or https
        }
        if (!mWebConfig.isNoCacheModel()) {
            webSettings.setAppCacheEnabled(false);// 支持启用缓存模式
            webSettings.setDomStorageEnabled(false);// 开启DOM缓存,默认状态下是不支持LocalStorage的
            webSettings.setDatabaseEnabled(false);// 开启数据库缓存
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setAppCacheEnabled(true);// 支持启用缓存模式
            webSettings.setDomStorageEnabled(true);// 开启DOM缓存,默认状态下是不支持LocalStorage的
            webSettings.setDatabaseEnabled(true);// 开启数据库缓存
            if (NetworkUtils.isConnected()) {
                webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }

        //为安全着想全部关闭 - start
        webSettings.setAllowFileAccess(false); //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(false); //通过 file mUrl 加载的 Javascript 读取其他的本地文件 .建议关闭
            webSettings.setAllowUniversalAccessFromFileURLs(false);//允许通过 file mUrl 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        }
        //为安全着想全部关闭 - end
        webSettings.setNeedInitialFocus(true);//在requestFocus()调用时告诉WebView是否需要设置节点
        webSettings.setDefaultTextEncodingName("gb2312");//设置编码格式
        webSettings.setLoadWithOverviewMode(false);//设置WebView是否以overview模式加载页面
        webSettings.setDefaultFontSize(16);//设置默认字体大小
        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        webSettings.setGeolocationEnabled(true);//是否启用地理定位
        webSettings.setBuiltInZoomControls(true);//设置WebView是否应该使用其内置的缩放机制
        webSettings.setSupportZoom(false);// 支持缩放
        if (webSettings.supportZoom()) {
            webSettings.setTextZoom(100); //设置页面的文本缩放百分比,默认为100
        }
        webSettings.setUseWideViewPort(true);//设置加载进来的页面自适应手机屏幕
        webSettings.setLoadWithOverviewMode(true);//设置WebView加载的页面的模式
        webSettings.setUserAgentString(webSettings.getUserAgentString().concat("agentweb/3.1.0"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //适配5.0不允许http和https混合使用情况
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void onDestroy() {
        if (mWebView == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        if (mWebView.getHandler() != null) {
            mWebView.getHandler().removeCallbacksAndMessages(null);
        }
        mWebView.removeAllViews();
        ViewGroup mViewGroup;
        if ((mViewGroup = ((ViewGroup) mWebView.getParent())) != null) {
            mViewGroup.removeView(mWebView);
        }
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.setTag(null);
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;
        safeWebViewClient = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            if (safeWebViewClient.mErrorView.getVisibility() == VISIBLE) {
                safeWebViewClient.mErrorView.setVisibility(GONE);
                mWebView.loadUrl(safeWebViewClient.urls.get(safeWebViewClient.urls.size() - 1));
                safeWebViewClient.urls.remove(safeWebViewClient.urls.size() - 1);
            } else {
                mWebView.goBack();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private static class SafeWebViewClient extends WebViewClient {

        private SafeWebView mWebView;
        private WebConfigImpl mWebConfig;
        private View mErrorView;

        //历史访问url
        private List<String> urls = new ArrayList<>();

        public SafeWebViewClient(SafeWebView mWebView, WebConfigImpl mWebConfig) {
            this.mWebView = mWebView;
            this.mWebConfig = mWebConfig;
        }

        /**
         * 当WebView得页面Scale值发生改变时回调
         */
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        /**
         * 是否在 WebView 内加载页面
         *
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /**
         * WebView 开始加载页面时回调，一次Frame加载对应一次回调
         *
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            urls.add(url);
            if (mWebConfig.isNightModel()) {
                //动态创建一个蒙层，用来夜间模式的遮盖
                FrameLayout frameLayout = new FrameLayout(view.getContext());
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                frameLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.black));
                frameLayout.setAlpha(0.8f);
                view.addView(frameLayout);
            }
        }

        /**
         * WebView 完成加载页面时回调，一次Frame加载对应一次回调
         *
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
         *
         * @param view WebView
         * @param url  url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        /**
         * WebView 可以拦截某一次的 request 来返回我们自己加载的数据，这个方法在后面缓存会有很大作用。
         *
         * @param view    WebView
         * @param request 当前产生 request 请求
         * @return WebResourceResponse
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        /**
         * WebView 访问 url 出错
         *
         * @param view
         * @param request
         * @param error
         */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (mErrorView == null) {
                mErrorView = LayoutInflater.from(view.getContext()).inflate(R.layout.webview_error_view, mWebView.getViewGroup());
                mErrorView.findViewById(R.id.error_reload_tv).setOnClickListener(v -> view.reload());
            }
        }

        /**
         * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
         *
         * @param view
         * @param handler
         * @param error
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if (mErrorView == null) {
                mErrorView = LayoutInflater.from(view.getContext()).inflate(R.layout.webview_error_view, mWebView.getViewGroup());
                mErrorView.findViewById(R.id.error_reload_tv).setOnClickListener(v -> view.reload());
            }
        }
    }

    private static class SafeWebViewChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        /**
         * 当前 WebView 加载网页进度
         *
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        /**
         * Js 中调用 alert() 函数，产生的对话框
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        /**
         * 处理 Js 中的 Confirm 对话框
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 处理 JS 中的 Prompt对话框
         *
         * @param view
         * @param url
         * @param message
         * @param defaultValue
         * @param result
         * @return
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        /**
         * 接收web页面的icon
         *
         * @param view
         * @param icon
         */
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        /**
         * 接收web页面的 Title
         *
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

}
