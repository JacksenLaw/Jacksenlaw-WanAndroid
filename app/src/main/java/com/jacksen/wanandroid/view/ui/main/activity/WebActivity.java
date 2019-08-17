package com.jacksen.wanandroid.view.ui.main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.detail.ArticleDetailContract;
import com.jacksen.wanandroid.presenter.detail.ArticleDetailPresenter;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.view.ui.main.web.CustomSettings;
import com.just.agentweb.AgentWeb;

import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 文章详情
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WebActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.left_title_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_toolbar_tv)
    TextView mToolbarTitle;
    @BindView(R.id.web_content)
    FrameLayout mWebContent;

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
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
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
        super.initToolbar();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbarTitle.setText(Html.fromHtml(title));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void getBundleData() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = (String) bundle.get(Constants.ARTICLE_TITLE);
            articleLink = (String) bundle.get(Constants.ARTICLE_LINK);
            articleId = ((int) bundle.get(Constants.ARTICLE_ID));
            isCommonSite = ((boolean) bundle.get(Constants.IS_COMMON_SITE));
            isCollect = ((boolean) bundle.get(Constants.IS_COLLECT));
            isCollectPage = ((boolean) bundle.get(Constants.IS_COLLECT_PAGE));
        }
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        getBundleData();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, R.id.error_reload_tv)
                .setAgentWebWebSettings(new CustomSettings(mPresenter.isNightMode(), mPresenter.isNoImageModel(), mPresenter.isNoCacheModel()))
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

}
