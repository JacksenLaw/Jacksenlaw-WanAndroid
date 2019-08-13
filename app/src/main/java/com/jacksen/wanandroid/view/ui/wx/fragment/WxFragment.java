package com.jacksen.wanandroid.view.ui.wx.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.presenter.wx.WxContract;
import com.jacksen.wanandroid.presenter.wx.WxPresenter;
import com.jacksen.wanandroid.view.bean.wx.ViewWxAuthorBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述： 公众号作者名页
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxFragment extends BaseRootFragment<WxPresenter> implements WxContract.View {

    @BindView(R.id.wx_detail_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.wx_detail_viewpager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragments = new ArrayList<>();

    public static WxFragment getInstance(boolean param1, String param2) {
        WxFragment fragment = new WxFragment();
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
        return R.layout.fragment_wx;
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.getWxAuthorList();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getWxAuthorList();
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_WX_PAGE,Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                       LiveDataBus.get().with(BusConstant.SCROLL_TO_WX_LIST_PAGE).postValue(integer);
                    }
                });
    }

    @Override
    public void showWxAuthor(ViewWxAuthorBean itemBeans) {
        mFragments.clear();
        for (ViewWxAuthorBean.ViewWxAuthorItemBean itemBean : itemBeans.getItems()) {
            mFragments.add(WxListFragment.getInstance(itemBean.getAuthorId(), itemBean.getAuthorName()));
        }
        initViewPagerAndTabLayout(itemBeans.getItems());
    }

    private void initViewPagerAndTabLayout(ArrayList<ViewWxAuthorBean.ViewWxAuthorItemBean> itemBeans) {
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
                return itemBeans.get(position).getAuthorName();
            }
        });
        mTabLayout.setViewPager(mViewPager);
    }
}
