package com.jacksen.wanandroid.view.ui.knowledge.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.knowledge.know_detail_fragment.KnowledgeDetailFragmentContract;
import com.jacksen.wanandroid.presenter.knowledge.know_detail_fragment.KnowledgeDetailFragmentPresenter;
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
 * 时间： 2019/4/7 0007
 * 描述：知识体系列表详情页
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeDetailFragment extends BaseRootFragment<KnowledgeDetailFragmentPresenter> implements KnowledgeDetailFragmentContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;
    private ArticleListAdapter mAdapter;
    private List<ViewFeedArticleListData.ViewFeedArticleItem> mFeedArticleDataList;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static KnowledgeDetailFragment getInstance(int id, String param2) {
        KnowledgeDetailFragment fragment = new KnowledgeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean getInnerFragment() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mFeedArticleDataList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleListAdapter(mFeedArticleDataList);
        mRecyclerView.setAdapter(mAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
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
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
    }

    @Override
    public void showKnowledgeHierarchyDetailData(ViewFeedArticleListData feedArticleListData) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            mFeedArticleDataList.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (feedArticleListData.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }

        mFeedArticleDataList.addAll(feedArticleListData.getItems());
        mAdapter.setNewData(mFeedArticleDataList);
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
    public void showScrollTheTop(int position) {
        mRecyclerView.scrollToPosition(position);
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
