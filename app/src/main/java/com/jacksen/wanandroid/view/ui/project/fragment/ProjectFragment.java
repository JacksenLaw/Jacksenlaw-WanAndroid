package com.jacksen.wanandroid.view.ui.project.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.project.ProjectContract;
import com.jacksen.wanandroid.presenter.project.ProjectPresenter;
import com.jacksen.wanandroid.view.bean.project.ViewProjectClassifyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.project_divider)
    View mDivider;
    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragments = new ArrayList<>();

    public static ProjectFragment getInstance(boolean param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initOnCreateView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.getProjectClassifyData();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getProjectClassifyData();
    }

    @Override
    public void showProjectClassify(ArrayList<ViewProjectClassifyBean.ViewProjectClassifyItemBean> itemBeans) {
        mFragments.clear();
        for (ViewProjectClassifyBean.ViewProjectClassifyItemBean itemBean : itemBeans) {
            mFragments.add(ProjectListFragment.getInstance(itemBean.getProjectId(), itemBean.getProjectName()));
        }
        initViewPagerAndTabLayout(itemBeans);
    }

    private void initViewPagerAndTabLayout(ArrayList<ViewProjectClassifyBean.ViewProjectClassifyItemBean> itemBeans) {
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
                return itemBeans.get(position).getProjectName();
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
