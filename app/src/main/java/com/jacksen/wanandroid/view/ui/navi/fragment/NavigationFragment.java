package com.jacksen.wanandroid.view.ui.navi.fragment;

import android.os.Bundle;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.presenter.navi.NavigationContract;
import com.jacksen.wanandroid.presenter.navi.NavigationPresenter;
import com.jacksen.wanandroid.view.bean.navi.ElemeGroupedItem;
import com.jacksen.wanandroid.view.ui.navi.adapter.ElemePrimaryAdapterConfig;
import com.jacksen.wanandroid.view.ui.navi.adapter.ElemeSecondaryAdapterConfig;
import com.kunminx.linkage.LinkageRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：导航页面
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NavigationFragment extends BaseRootFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.linkageRecyclerView)
    LinkageRecyclerView linkageRecyclerView;
    private ElemePrimaryAdapterConfig primaryAdapterConfig;
    private ElemeSecondaryAdapterConfig secondaryAdapterConfig;

    public static NavigationFragment getInstance(boolean param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initOnCreateView() {
        linkageRecyclerView.setScrollSmoothly(false);
    }


    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.getNaviData();
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getNaviData();
        LiveDataBus.get().with(BusConstant.NIGHT_MODEL, Boolean.class)
                .observe(this, aBoolean -> {
                    if (primaryAdapterConfig != null) {
                        primaryAdapterConfig.setNightMode(mPresenter.isNightMode());
                        linkageRecyclerView.getPrimaryAdapter().notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void showNaviData(List<ElemeGroupedItem> items) {
        primaryAdapterConfig = new ElemePrimaryAdapterConfig(mPresenter);
        secondaryAdapterConfig = new ElemeSecondaryAdapterConfig();
        linkageRecyclerView.init(items, primaryAdapterConfig, secondaryAdapterConfig);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        primaryAdapterConfig = null;
        secondaryAdapterConfig = null;
    }

    @Override
    public void scrollToTheTop(int position) {
        if (linkageRecyclerView != null) {
            linkageRecyclerView.scrollToTheTop();
        }
    }

    @Override
    public void showCollect(boolean isCollect) {
        ((ElemeGroupedItem) linkageRecyclerView.getSecondaryAdapter().getItems().get(secondaryAdapterConfig.getClickPosition())).info.setCollect(isCollect);
        linkageRecyclerView.getSecondaryAdapter().notifyItemChanged(secondaryAdapterConfig.getClickPosition());
    }
}

