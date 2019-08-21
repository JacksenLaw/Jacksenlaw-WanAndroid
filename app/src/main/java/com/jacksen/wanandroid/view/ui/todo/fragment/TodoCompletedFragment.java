package com.jacksen.wanandroid.view.ui.todo.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.todo.fragment.completed.TodoCompletedContract;
import com.jacksen.wanandroid.presenter.todo.fragment.completed.TodoCompletedPresenter;
import com.jacksen.wanandroid.view.bean.todo.FilterResult;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;
import com.jacksen.wanandroid.view.ui.todo.activity.TodoActivity;
import com.jacksen.wanandroid.view.ui.todo.adapter.TodoAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 创建时间：2019/8/12/0012
 * 描述： 已完成事项页
 * 版本号： v1.0.0
 * 更新时间：2019/8/12/0012
 * 更新内容：
 */
public class TodoCompletedFragment extends BaseRootFragment<TodoCompletedPresenter>
        implements TodoCompletedContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private TodoAdapter mAdapter;
    private List<ViewTodoData.ViewTodoItem> data = new ArrayList<>();

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static TodoCompletedFragment getInstance(boolean param1, String param2) {
        TodoCompletedFragment fragment = new TodoCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean isInnerFragment() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_todo_completed;
    }

    @Override
    protected void initOnCreateView() {
        mAdapter = new TodoAdapter(null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void reload() {
        super.reload();
        mPresenter.onRefresh();
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
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPresenter.doUndoneTodoClick(adapter, view, position);
            return true;
        });
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // 开启滑动删除
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                mPresenter.doDeleteSwipe(data.get(pos).getId());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
    }

    public void receiveFilterData(List<FilterResult> results) {
        mPresenter.filterData(results);
    }

    @Override
    public void showTodoCompletedData(ViewTodoData viewTodoData, boolean isFilter) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            data.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (viewTodoData.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_todo));
            }
        }
        if (isFilter) {
            data.clear();
        }
        data.addAll(viewTodoData.getItems());
        mAdapter.setNewData(data);
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

    @Override
    public boolean onBackPressedSupport() {
        if(((TodoActivity)getActivity()).mRightDrawerLayout.isDrawerOpen(GravityCompat.END)){
            ((TodoActivity)getActivity()).mRightDrawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }
        return super.onBackPressedSupport();
    }
}
