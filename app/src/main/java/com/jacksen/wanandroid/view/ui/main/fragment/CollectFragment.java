package com.jacksen.wanandroid.view.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.event.Collect;
import com.jacksen.wanandroid.presenter.collect.CollectContract;
import com.jacksen.wanandroid.presenter.collect.CollectPresenter;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;
import com.jacksen.wanandroid.view.ui.mainpager.adapter.ArticleListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述： 收藏fragment
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class CollectFragment extends BaseRootFragment<CollectPresenter> implements CollectContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.collect_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.collect_floating_action_btn)
    FloatingActionButton mActionButton;

    private ArticleListAdapter mAdapter;
    private List<ViewFeedArticleListData.ViewFeedArticleItem> data;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static CollectFragment getInstance(boolean param1, String param2) {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                mPresenter.doItemClickListener(adapter, view, position)
        );
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.doItemChildClickListener(adapter, view, position);
            }
        });

        mActionButton.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
        LiveDataBus.get()
                .with(BusConstant.NIGHT_MODEL, Boolean.class)
                .observe(this, aBoolean -> {
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                });
        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, new Observer<Collect>() {
                    @Override
                    public void onChanged(@Nullable Collect collect) {
                        if (BusConstant.COLLECT_PAGE.equals(collect.getType())) {
                            showCancelCollect(mPresenter.getCurrentPosition());
                        }
                    }
                });
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.getCollectList(0);

    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        mAdapter = new ArticleListAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (RECYCLER_VIEW_STATE == NORMAL) {
            RECYCLER_VIEW_STATE = REFRESH;
            mPresenter.onRefresh();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (RECYCLER_VIEW_STATE == NORMAL) {
            RECYCLER_VIEW_STATE = LOAD_MORE;
            mPresenter.onLoadMore();
        }
    }

    @Override
    public void showCollectList(ViewFeedArticleListData feedArticleListBean) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            data.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (feedArticleListBean.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data));
            }
        }
        data.addAll(feedArticleListBean.getItems());
        mAdapter.setCollect(true);
        mAdapter.setNewData(data);
        RECYCLER_VIEW_STATE = NORMAL;
    }

    @Override
    public void showCancelCollect(int position) {
        data.remove(position);
        mAdapter.setNewData(data);
    }

}
