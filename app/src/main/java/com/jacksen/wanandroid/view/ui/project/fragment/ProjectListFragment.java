package com.jacksen.wanandroid.view.ui.project.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.project.list.ProjectListContract;
import com.jacksen.wanandroid.presenter.project.list.ProjectListPresenter;
import com.jacksen.wanandroid.view.bean.project.ViewProjectListData;
import com.jacksen.wanandroid.view.ui.project.adapter.ProjectListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectListFragment extends BaseRootFragment<ProjectListPresenter> implements ProjectListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.project_recycler_view)
    RecyclerView mRecyclerView;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    private ProjectListAdapter mAdapter;
    private List<ViewProjectListData.ViewProjectListItem> data;

    public static ProjectListFragment getInstance(int param1, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.onRefresh();
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        mAdapter = new ProjectListAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mPresenter.doItemClickListener(adapter, view, position));
    }

    @Override
    public void showProjectListData(ViewProjectListData items) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            data.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (items.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }
        data.addAll(items.getItems());
        mAdapter.setNewData(data);

        RECYCLER_VIEW_STATE = NORMAL;
    }

    @Override
    public void scrollToTheTop(int position) {
        if(mRecyclerView != null){
            mRecyclerView.scrollToPosition(position);
        }
    }

    @Override
    public void onEventCollect(int position, boolean isCollect) {
        if (mAdapter != null && mAdapter.getData().size() > position) {
            mAdapter.getData().get(position).setCollect(isCollect);
            mAdapter.setData(position, mAdapter.getData().get(position));
        }
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
