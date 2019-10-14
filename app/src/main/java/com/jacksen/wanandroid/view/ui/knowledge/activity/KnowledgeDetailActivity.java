package com.jacksen.wanandroid.view.ui.knowledge.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.presenter.knowledge.know_detail_activity.KnowledgeActivityContract;
import com.jacksen.wanandroid.presenter.knowledge.know_detail_activity.KnowledgeActivityPresenter;
import com.jacksen.wanandroid.view.ui.knowledge.fragment.KnowledgeDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述： 知识体系详情页
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeDetailActivity extends BaseActivity<KnowledgeActivityPresenter> implements KnowledgeActivityContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.knowledge_hierarchy_detail_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.knowledge_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        mPresenter.handData(getIntent());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initListener() {
        mFloatingActionButton.setOnClickListener(v -> mPresenter.jumpToTop());
    }

    @Override
    public void showTitle(String title) {
        mTitleTv.setText(title);
    }

    @Override
    public void showFragment(int chapterId) {
        mFragments.add(KnowledgeDetailFragment.getInstance(chapterId, null));
    }

    @Override
    public void showSwitchProject() {
        onBackPressedSupport();
    }

    @Override
    public void showSwitchNavigation() {
        onBackPressedSupport();
    }

    @Override
    public void onKnowledgeHierarchyData(List<String> pageTitle, boolean isSingleChapter, String chapterName) {
        initViewPagerAndTabLayout(pageTitle, isSingleChapter, chapterName);
    }

    private void initViewPagerAndTabLayout(List<String> pageTitle, boolean isSingleChapter, String chapterName) {
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (isSingleChapter) {
                    return chapterName;
                } else {
                    return pageTitle.get(position);
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
