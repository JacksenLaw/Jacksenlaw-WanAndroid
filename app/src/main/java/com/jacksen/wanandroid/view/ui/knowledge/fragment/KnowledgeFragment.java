package com.jacksen.wanandroid.view.ui.knowledge.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.knowledge.KnowledgeContract;
import com.jacksen.wanandroid.presenter.knowledge.KnowledgePresenter;
import com.jacksen.wanandroid.view.bean.knowledge.ViewKnowledgeListData;
import com.jacksen.wanandroid.view.ui.knowledge.adapter.KnowledgeHierarchyAdapter;
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
 * 描述： 知识体系列表 fragment
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeFragment extends BaseRootFragment<KnowledgePresenter> implements KnowledgeContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;

    private List<ViewKnowledgeListData.ViewKnowledgeItem> mDatas;
    private KnowledgeHierarchyAdapter mAdapter;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static KnowledgeFragment getInstance(boolean param1, String param2) {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(adapter, view, position);
            }
        });
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.onRefresh();
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new KnowledgeHierarchyAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void scrollToTheTop(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showKnowledgeHierarchyData(ViewKnowledgeListData viewKnowledgeListData) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            mDatas.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (viewKnowledgeListData.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }
        mDatas.addAll(viewKnowledgeListData.getItems());
        mAdapter.setNewData(mDatas);
        RECYCLER_VIEW_STATE = NORMAL;
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
}