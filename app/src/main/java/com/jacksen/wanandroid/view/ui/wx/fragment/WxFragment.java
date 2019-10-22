package com.jacksen.wanandroid.view.ui.wx.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.presenter.wx.WxContract;
import com.jacksen.wanandroid.presenter.wx.WxPresenter;
import com.jacksen.wanandroid.view.bean.wx.ViewWxAuthorBean;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述： 公众号作者名页
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxFragment extends BaseRootFragment<WxPresenter> implements WxContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.search_tint_tv)
    TextView mSearchTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.include_toolbar)
    Toolbar mToolbar;

    private CharSequence authorName;
    private int mPosition;

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
    }


    @Override
    protected void initOnCreateView() {
        mToolbar.setNavigationIcon(null);
        LiveDataBus.get()
                .with(BusConstant.HIDE_TOOLBAR, Boolean.class)
                .observe(this, b -> mToolbar.setVisibility(b ? View.GONE : View.VISIBLE));
    }

    @Override
    protected void initListener() {
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mSearchTintTv.setText(String.format(getString(R.string.search_hint), authorName));
                } else {
                    mSearchTintTv.setText("");
                }
            }
        });

        mPresenter.addRxBindingSubscribe(RxView.clicks(mSearchTv)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .filter(s -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .subscribe(o -> {
                    KeyboardUtils.hideSoftInput(_mActivity);
                    ((WxListFragment) mFragments.get(mPosition)).doSearchClick(mSearchEdit.getText().toString());
                })
        );

        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPosition = tab.getPosition();
                mSearchEdit.setText("");
                authorName = tab.getText();
                mSearchTintTv.setText(String.format(getString(R.string.search_hint), authorName));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    ((WxListFragment) mFragments.get(mPosition)).showRecyclerView();
                }
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
        mViewPager.setOffscreenPageLimit(30);
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
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
