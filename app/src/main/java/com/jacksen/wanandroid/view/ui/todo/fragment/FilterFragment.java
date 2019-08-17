package com.jacksen.wanandroid.view.ui.todo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseFilterFragment;
import com.jacksen.wanandroid.presenter.todo.fragment.filter.FilterContract;
import com.jacksen.wanandroid.presenter.todo.fragment.filter.FilterPresenter;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;
import com.jacksen.wanandroid.view.bean.todo.FilterResult;
import com.jacksen.wanandroid.view.ui.todo.adapter.FilterAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 创建时间：2019/8/15/0015
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/15/0015
 * 更新内容：
 */
public class FilterFragment extends BaseFilterFragment<FilterPresenter> implements FilterContract.View {

    @BindView(R.id.filter_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_sure)
    Button mBtnSure;
    private FilterAdapter mAdapter;

    private Callback mFilterCallback;
    public void setFilterCallback(Callback filterCallback) {
        this.mFilterCallback = filterCallback;
    }

    public static FilterFragment getInstance(boolean param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new FilterAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getFilterData();
    }

    @Override
    protected void initListener() {
        //重置
        mBtnReset.setOnClickListener(v -> {
            mAdapter.notifyDataSetChanged();
            mAdapter.getFilterResults().clear();
        });
        //确定开始筛选
        mBtnSure.setOnClickListener(v -> {
            List<FilterResult> results = new ArrayList<>();
            for (Map.Entry<String, FilterResult> entry : mAdapter.getFilterResults().entrySet()) {
                results.add(entry.getValue());
            }
            mFilterCallback.startFilter(results);
        });

    }

    @Override
    public void showFilterData(List<FilterBean> items) {
        mAdapter.setNewData(items);
    }

    public interface Callback {
        void startFilter(List<FilterResult> results);
    }
}
