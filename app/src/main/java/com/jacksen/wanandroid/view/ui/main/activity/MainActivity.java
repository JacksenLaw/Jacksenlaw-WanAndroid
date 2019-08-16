package com.jacksen.wanandroid.view.ui.main.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bar.library.StatusBarUtil;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.main.MainContract;
import com.jacksen.wanandroid.presenter.main.MainPresenter;
import com.jacksen.wanandroid.util.BottomNavUtil;
import com.jacksen.wanandroid.util.KLog;
import com.jacksen.wanandroid.view.ui.knowledge.fragment.KnowledgeFragment;
import com.jacksen.wanandroid.view.ui.main.fragment.CollectFragment;
import com.jacksen.wanandroid.view.ui.mainpager.fragment.HomePageFragment;
import com.jacksen.wanandroid.view.ui.navi.fragment.NavigationFragment;
import com.jacksen.wanandroid.view.ui.project.fragment.ProjectFragment;
import com.jacksen.wanandroid.view.ui.wx.fragment.WxFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView mLeftNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;
    private TextView mUserName;

    private ArrayList<Fragment> mFragments;
    private HomePageFragment mMainPagerFragment;
    private KnowledgeFragment mKnowledgeHierarchyFragment;
    private WxFragment mWxArticleFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private CollectFragment collectFragment;

    //记录上一次打开的fragment，用来隐藏上次的fragment
    private int mLastFgIndex;
    //记录当前抽屉的点击,当抽屉完全关闭后根据此记录打开对应的activity页面
    private String currentLeftNaviClick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    protected void initListener() {
        mFloatingActionButton.setOnClickListener(v ->
                mPresenter.jumpToTheTop(mFragments.get(mPresenter.getCurrentPage()))
        );
    }

    @Override
    protected void initToolbar() {
        StatusBarUtil.setColorForDrawerLayout(mActivity,mDrawerLayout,getStatusBarColor());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleTv.setText(getString(R.string.home_pager));
        mToolbar.setNavigationOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        mFragments = new ArrayList<>();
        init();
        if (savedInstanceState == null) {
            initPager(false, Constants.TYPE_MAIN_PAGER);
        } else {
            initPager(true, mPresenter.getCurrentPage());
        }
    }

    private void init() {
        mPresenter.setCurrentPage(Constants.TYPE_MAIN_PAGER);
        initNavigationView();
        initBottomNavigationView();
        initDrawerLayout();
    }

    private void initPager(boolean isRecreate, int position) {
        initFragments(isRecreate);
        switchFragment(position);
    }

    private void initFragments(boolean isRecreate) {
        mMainPagerFragment = HomePageFragment.getInstance(isRecreate, null);
        mKnowledgeHierarchyFragment = KnowledgeFragment.getInstance(isRecreate, null);
        mWxArticleFragment = WxFragment.getInstance(isRecreate, null);
        mNavigationFragment = NavigationFragment.getInstance(isRecreate, null);
        mProjectFragment = ProjectFragment.getInstance(isRecreate, null);
        collectFragment = CollectFragment.getInstance(isRecreate, null);
        mFragments.add(mMainPagerFragment);
        mFragments.add(mKnowledgeHierarchyFragment);
        mFragments.add(mWxArticleFragment);
        mFragments.add(mNavigationFragment);
        mFragments.add(mProjectFragment);
        mFragments.add(collectFragment);
    }

    private void initNavigationView() {
        mUserName = mLeftNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        if (mPresenter.getLoginState()) {
            showLoginOutView();
        } else {
            showLoginView();
        }
        mLeftNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_item_wan_android:
                    startMainPager(mPresenter.getCurrentPage());
                    setCurrentLeftNaviClick(null);
                    break;
                case R.id.nav_item_my_collect:
                    startCollectFragment();
                    setCurrentLeftNaviClick(null);
                    break;
                case R.id.nav_item_my_todo:
                    setCurrentLeftNaviClick("TODO");
                    break;
                case R.id.nav_item_setting:
                    setCurrentLeftNaviClick("SETTING");
                    break;
                case R.id.nav_item_about_us:
                    setCurrentLeftNaviClick("ABOUT");
                    break;
                case R.id.nav_item_logout:
                    setCurrentLeftNaviClick("LOGOUT");
                    break;
                default:
                    setCurrentLeftNaviClick(null);
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    private void setCurrentLeftNaviClick(String leftNaviClick) {
        currentLeftNaviClick = leftNaviClick;
    }

    private void startMainPager(int currentPager) {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        switch (currentPager) {
            case Constants.TYPE_MAIN_PAGER:
                loadPager(getString(R.string.home_pager), 0, Constants.TYPE_MAIN_PAGER);
                break;
            case Constants.TYPE_KNOWLEDGE_PAGER:
                mBottomNavigationView.setSelectedItemId(R.id.tab_knowledge_hierarchy);
                break;
            case Constants.TYPE_WX_ARTICLE_PAGER:
                mBottomNavigationView.setSelectedItemId(R.id.tab_wx_article);
                break;
            case Constants.TYPE_NAVIGATION_PAGER:
                mBottomNavigationView.setSelectedItemId(R.id.tab_navigation);
                break;
            case Constants.TYPE_PROJECT_PAGER:
                mBottomNavigationView.setSelectedItemId(R.id.tab_project);
                break;
        }
    }

    private void startCollectFragment() {
        if (mPresenter.getLoginState()) {
            mTitleTv.setText(getString(R.string.my_collect));
            switchFragment(Constants.TYPE_COLLECT_PAGER);
        }
    }

    private void initBottomNavigationView() {
        BottomNavUtil.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    if (mPresenter.getCurrentPage() == Constants.TYPE_MAIN_PAGER) {
                        mMainPagerFragment.reload();
                    } else {
                        loadPager(getString(R.string.home_pager), 0, Constants.TYPE_MAIN_PAGER);
                    }
                    break;
                case R.id.tab_knowledge_hierarchy:
                    loadPager(getString(R.string.knowledge_hierarchy), 1, Constants.TYPE_KNOWLEDGE_PAGER);
                    break;
                case R.id.tab_wx_article:
                    loadPager(getString(R.string.wx_article), 2, Constants.TYPE_WX_ARTICLE_PAGER);
                    break;
                case R.id.tab_navigation:
                    loadPager(getString(R.string.navigation), 3, Constants.TYPE_NAVIGATION_PAGER);
                    break;
                case R.id.tab_project:
                    loadPager(getString(R.string.project), 4, Constants.TYPE_PROJECT_PAGER);
                    break;
            }
            return true;
        });
    }

    private void loadPager(String title, int position, int pagerType) {
        mTitleTv.setText(title);
        switchFragment(position);
        mPresenter.setCurrentPage(pagerType);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
//                View mContent = mDrawerLayout.getChildAt(0);
//                float scale = 1 - slideOffset;
//                float endScale = 0.8f + scale * 0.2f;
//                float startScale = 1 - 0.3f * scale;
//
//                //设置左边菜单滑动后的占据屏幕大小
//                drawerView.setScaleX(startScale);
//                drawerView.setScaleY(startScale);
//                //设置菜单透明度
//                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));
//
//                //设置内容界面水平和垂直方向偏转量
//                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
//                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
//                //设置内容界面操作无效（比如有button就会点击无效）
//                mContent.invalidate();
//                //设置右边菜单滑动后的占据屏幕大小
//                mContent.setScaleX(endScale);
//                mContent.setScaleY(endScale);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                KLog.i("onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                KLog.i("onDrawerClosed");
                if ("TODO".equals(currentLeftNaviClick)) {
                    mPresenter.doTodoClick();
                }
                if ("SETTING".equals(currentLeftNaviClick)) {
                    mPresenter.doSettingClick();
                }
                if ("ABOUT".equals(currentLeftNaviClick)) {
                    mPresenter.doTurnAboutClick();
                }
                if ("LOGOUT".equals(currentLeftNaviClick)) {
                    mPresenter.doLogOutClick();
                }
                setCurrentLeftNaviClick(null);
            }
        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 选择要显示的fragment
     *
     * @param position 下标
     */
    private void switchFragment(int position) {
        if (position >= Constants.TYPE_COLLECT_PAGER) {
            mFloatingActionButton.setVisibility(View.INVISIBLE);
            mBottomNavigationView.setVisibility(View.INVISIBLE);
        } else {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            mBottomNavigationView.setVisibility(View.VISIBLE);
        }
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        fragmentTransaction.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commitAllowingStateLoss();
            fragmentTransaction.add(R.id.fragment_group, targetFg);
        }
        fragmentTransaction.show(targetFg);
        fragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 选中项目tab
     */
    @Override
    public void selectProjectTab() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setSelectedItemId(R.id.tab_project);
        }
    }

    /**
     * 选中导航tab
     */
    @Override
    public void selectNavigationTab() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setSelectedItemId(R.id.tab_navigation);
        }
    }

    @Override
    public void showLoginView() {
        setUserName();
        mUserName.setOnClickListener(v -> mPresenter.doTurnLoginClick());
        if (mLeftNavigationView == null) {
            return;
        }
        mLeftNavigationView.getMenu().findItem(R.id.nav_item_my_collect).setVisible(false);
        mLeftNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
        mLeftNavigationView.getMenu().findItem(R.id.nav_item_wan_android).setChecked(true);
    }

    @Override
    public void showLoginOutView() {
        if (mLeftNavigationView == null) {
            return;
        }
        setUserName();
        mUserName.setOnClickListener(null);
        //显示退出登录
        mLeftNavigationView.getMenu().findItem(R.id.nav_item_my_collect).setVisible(true);
        mLeftNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);

    }

    private void setUserName() {
        if (mPresenter.getLoginState()) {
            mUserName.setText(mPresenter.getLoginAccount());
        } else {
            mUserName.setText(getString(R.string.login_in));
        }
    }

    @Override
    public void showAutoLoginView() {

    }

    /**
     * 当某个activity变得"容易"被系统销毁时，该activity的onSaveInstanceState()就会被执行
     * 只适合保存瞬态数据, 比如UI控件的状态, 成员变量的值等，而不应该用来保存持久化数据，
     * 持久化数据应该当用户离开当前的 activity时，在 onPause() 中保存（比如将数据保存到数据库或文件中）
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        KLog.i("onSaveInstanceState");
        if (mMainPagerFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mMainPagerFragment", mMainPagerFragment);
        }
        if (mKnowledgeHierarchyFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mKnowledgeHierarchyFragment", mKnowledgeHierarchyFragment);
        }
        if (mWxArticleFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mWxArticleFragment", mWxArticleFragment);
        }
        if (mNavigationFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mNavigationFragment", mNavigationFragment);
        }
        if (mProjectFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mProjectFragment", mProjectFragment);
        }
        if (collectFragment != null) {
            getSupportFragmentManager().putFragment(outState, "collectFragment", collectFragment);
        }
        outState.putString("key", "value");
    }

    /**
     * onRestoreInstanceState()被调用的前提是，activity A“确实”被系统销毁了
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        KLog.i("onRestoreInstanceState");
        mMainPagerFragment = (HomePageFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mMainPagerFragment");
        mKnowledgeHierarchyFragment = (KnowledgeFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mKnowledgeHierarchyFragment");
        mWxArticleFragment = (WxFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mWxArticleFragment");
        mNavigationFragment = (NavigationFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mNavigationFragment");
        mProjectFragment = (ProjectFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mProjectFragment");
        collectFragment = (CollectFragment) getSupportFragmentManager().getFragment(savedInstanceState, "collectFragment");
        KLog.i(savedInstanceState.getString("key"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_always_use:
                mPresenter.doUsefulSitesClick();
                break;
            case R.id.action_search:
                mPresenter.doSearchClick();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean getSwipeBackEnable() {
        return false;
    }

}
