package com.jacksen.wanandroid.view.ui.main.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bar.library.StatusBarUtil;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.search.SearchListContract;
import com.jacksen.wanandroid.presenter.search.SearchListPresenter;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;
import com.jacksen.wanandroid.view.ui.mainpager.adapter.ArticleListAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/4/16 0016
 * 描述： //TODO 状态栏问题
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchListActivity extends BaseActivity<SearchListPresenter> implements SearchListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.normal_view)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_list_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    private List<ViewFeedArticleListData.ViewFeedArticleItem> datas = new ArrayList<>();
    private ArticleListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new ArticleListAdapter(null);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusBarUtil.setDarkMode(this);
        mTitleTv.setText(mPresenter.getSearchText());
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mPresenter.doItemClickListener(adapter, view, position));
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
        mFloatingActionButton.setOnClickListener(v -> mRecyclerView.smoothScrollToPosition(0));
    }

    @Override
    public void showSearchList(ViewFeedArticleListData viewFeedArticleListData) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            datas.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (viewFeedArticleListData.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }
        datas.addAll(viewFeedArticleListData.getItems());
        mAdapter.setNewData(datas);

        RECYCLER_VIEW_STATE = NORMAL;
    }

    @Override
    public void onCollectArticleData(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
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
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }
}
