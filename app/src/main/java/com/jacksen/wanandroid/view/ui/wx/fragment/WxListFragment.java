package com.jacksen.wanandroid.view.ui.wx.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.wx.list.WxListContract;
import com.jacksen.wanandroid.presenter.wx.list.WxListPresenter;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;
import com.jacksen.wanandroid.view.ui.mainpager.adapter.ArticleListAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述： 公众号列表详情页面
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxListFragment extends BaseRootFragment<WxListPresenter> implements WxListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.we_detail_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_tint_tv)
    TextView mSearchTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    private ArticleListAdapter mAdapter;
    private List<ViewFeedArticleListData.ViewFeedArticleItem> data;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static WxListFragment getInstance(int param1, String param2) {
        WxListFragment fragment = new WxListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_list;
    }

    @Override
    protected void initOnCreateView() {
        mToolbar.setNavigationIcon(null);
        initRecyclerView();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mSearchTintTv.setText(mPresenter.getSearchHint());
        mPresenter.onRefresh();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        mAdapter = new ArticleListAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                mPresenter.doItemClickListener(adapter, view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                //收藏文章
                case R.id.item_article_pager_like_iv:
                    mPresenter.doCollectClick(adapter, position);
                    break;
                case R.id.item_article_pager_chapterName:
                    mPresenter.doTurnChapterKnowledgePager(adapter, position);
                    break;
                case R.id.item_article_pager_tag_red_tv:
                    mPresenter.clickTag(adapter, position);
                    break;
            }
        });

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
                    mSearchTintTv.setText(mPresenter.getSearchHint());
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
                    mPresenter.doSearchClick(mSearchEdit.getText().toString());
                })
        );

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KeyboardUtils.hideSoftInput(_mActivity);
            }
        });
    }

    @Override
    public void scrollToTheTop(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(position);
        }
    }

    @Override
    public void onCollectArticleData(int position,ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
        mAdapter.setData(position, feedArticleData);
    }

    @Override
    public void onEventCollect(int position, boolean isCollect) {
        if (mAdapter != null && mAdapter.getData().size() > position) {
            mAdapter.getData().get(position).setCollect(isCollect);
            mAdapter.setData(position, mAdapter.getData().get(position));
        }
    }

    @Override
    public void showWxList(ViewFeedArticleListData feedArticleListBean) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mSearchTintTv.setText(mPresenter.getSearchHint());
            mSearchEdit.setText("");
            mRefreshLayout.finishRefresh(500);
            data.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (feedArticleListBean.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }
        data.addAll(feedArticleListBean.getItems());
        mAdapter.setNewData(data);

        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        RECYCLER_VIEW_STATE = NORMAL;
    }

    @Override
    public void showWxSearchView(ViewFeedArticleListData feedArticleListBean) {
        if (feedArticleListBean != null && feedArticleListBean.getItems().size() > 0) {
            data.clear();
            data.addAll(feedArticleListBean.getItems());
            mAdapter.setNewData(data);
            if (mRecyclerView.getVisibility() != View.VISIBLE) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            showSnackBar(getRootView(),getString(R.string.load_more_no_data_ganhuo));
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
