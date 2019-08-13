package com.jacksen.wanandroid.view.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.detail.ArticleDetailContract;
import com.jacksen.wanandroid.presenter.detail.ArticleDetailPresenter;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.util.StatusBarUtils;
import com.jacksen.wanandroid.widget.webview.impl.WebConfigImpl;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultDownloadImpl;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.WebViewClient;

import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 文章详情
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_content)
    FrameLayout mWebContent;
    @BindView(R.id.web_layer)
    FrameLayout mWebLayer;

    //    private SafeWebView mWebView;
    private AgentWeb mAgentWeb;

    private Bundle bundle;
    private MenuItem mCollectItem;
    private int articleId;
    private String articleLink;
    private String title;

    private boolean isCollect;
    private boolean isCommonSite;
    private boolean isCollectPage;

    @Override
    protected void onPause() {
//        mWebView.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
//        mWebView.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
//        mWebView.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected boolean getSwipeBackEnable() {
        return false;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initToolbar() {
        getBundleData();
        mToolbar.setTitle(Html.fromHtml(title));
        setSupportActionBar(mToolbar);
        StatusBarUtils.with(this).setColor(ContextCompat.getColor(this, R.color.colorPrimary)).init();
        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressedSupport();
        });
    }

    private void getBundleData() {
        bundle = getIntent().getExtras();
        assert bundle != null;
        title = (String) bundle.get(Constants.ARTICLE_TITLE);
        articleLink = (String) bundle.get(Constants.ARTICLE_LINK);
        articleId = ((int) bundle.get(Constants.ARTICLE_ID));
        isCommonSite = ((boolean) bundle.get(Constants.IS_COMMON_SITE));
        isCollect = ((boolean) bundle.get(Constants.IS_COLLECT));
        isCollectPage = ((boolean) bundle.get(Constants.IS_COLLECT_PAGE));
    }

    @Override
    public void reload() {

    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        WebConfigImpl webConfig = new WebConfigImpl();
        webConfig.setNightModel(mPresenter.isNightMode());
        webConfig.setNoCacheModel(mPresenter.isNoCacheModel());
        webConfig.setNoImageModel(mPresenter.isNoImageModel());
//        mWebView = new SafeWebView(this,mWebContent,webConfig);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initEventAndData() {

//        mWebView.loadUrl(articleLink);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, R.id.error_reload_tv)
                .setAgentWebWebSettings(new CustomSettings())
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageFinished(view, url);
                        if (mPresenter.isNightMode()) {
                            mWebLayer.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .createAgentWeb()
                .ready()
                .go(articleLink);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        bundle = getIntent().getExtras();
        assert bundle != null;
        isCommonSite = (boolean) bundle.get(Constants.IS_COMMON_SITE);
        if (!isCommonSite) {
            unCommonSiteEvent(menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_article_common, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_collect:
                mPresenter.doCollectEvent(mCollectItem.getTitle(), isCollectPage, articleId);
                break;
            case R.id.item_share:
                mPresenter.doShare(title, articleLink);
                break;
            case R.id.item_system_browser:
                mPresenter.doOpenWithSystemBrowser(articleLink);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu      Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (Constants.MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            supportFinishAfterTransition();
        }
    }

    @Override
    public void showCollectArticleData(boolean isCollect) {
        if (isCollect) {
            mCollectItem.setTitle(R.string.cancel_collect);
            mCollectItem.setIcon(R.drawable.ic_toolbar_like_p);
            CommonUtils.showMessage(getString(R.string.collect_success));
        } else {
            mCollectItem.setTitle(R.string.collect);
            mCollectItem.setIcon(R.drawable.ic_toolbar_like_n);
            CommonUtils.showMessage(getString(R.string.cancel_collect_success));
        }
    }

    private void unCommonSiteEvent(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acticle, menu);
        mCollectItem = menu.findItem(R.id.item_collect);
        if (isCollect) {
            mCollectItem.setTitle(getString(R.string.cancel_collect));
            mCollectItem.setIcon(R.drawable.ic_toolbar_like_p);
        } else {
            mCollectItem.setTitle(getString(R.string.collect));
            mCollectItem.setIcon(R.drawable.ic_toolbar_like_n);
        }
    }

    //WebDefaultSettingsManager 重命名为 AbsAgentWebSettings 并且抽象出bindAgentWebSupport方法
    private class CustomSettings extends AbsAgentWebSettings {
        public CustomSettings() {
            super();
        }

        @Override
        protected void bindAgentWebSupport(AgentWeb agentWeb) {

        }


        @Override
        public IAgentWebSettings toSetting(WebView webView) {
            super.toSetting(webView);
            if (mPresenter.isNightMode()) {
                webView.setBackgroundColor(ContextCompat.getColor(ArticleDetailActivity.this, R.color.colorWeb_Night));
            }
            if (mPresenter.isNoImageModel()) {
                getWebSettings().setLoadsImagesAutomatically(false);// 支持自动加载图片
                getWebSettings().setBlockNetworkImage(true);//是否阻塞加载网络图片  协议http or https
            }else{
                getWebSettings().setLoadsImagesAutomatically(true);// 支持自动加载图片
                getWebSettings().setBlockNetworkImage(false);//是否阻塞加载网络图片  协议http or https
            }
            if (!mPresenter.isNoCacheModel()) {
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
            getWebSettings().setDefaultTextEncodingName("gb2312");//设置编码格式
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

}
