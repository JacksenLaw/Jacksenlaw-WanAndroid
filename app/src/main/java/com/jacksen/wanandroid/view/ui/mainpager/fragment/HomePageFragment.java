package com.jacksen.wanandroid.view.ui.mainpager.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.mainpager.HomePagerContract;
import com.jacksen.wanandroid.presenter.mainpager.HomePagerPresenter;
import com.jacksen.wanandroid.util.BannerImageLoader;
import com.jacksen.wanandroid.view.bean.main.ViewBannerData;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;
import com.jacksen.wanandroid.view.ui.mainpager.adapter.ArticleListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class HomePageFragment extends BaseRootFragment<HomePagerPresenter> implements HomePagerContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;

    private ArticleListAdapter mAdapter;
    private List<ViewFeedArticleListData.ViewFeedArticleItem> mFeedArticleDataList;

    private Banner mBanner;

    private static int NORMAL = 0;
    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;
    private static int RECYCLER_VIEW_STATE = NORMAL;

    public static HomePageFragment getInstance(boolean param1, String param2) {

        HomePageFragment fragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initOnCreateView() {
        initRecyclerView();

    }

    public void scrollToTheTop(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void setNightModel() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.onRefresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                mPresenter.doItemClickListener(adapter, view, position)
        );

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
    public void reload() {
        super.reload();
        mRecyclerView.scrollToPosition(0);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    private void initRecyclerView() {
        mFeedArticleDataList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleListAdapter(mFeedArticleDataList);
        LinearLayout mHeaderGroup = (LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.header_banner_layout, null);
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(mBanner);
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showBannerData(final ArrayList<ViewBannerData.ViewBannerItem> bannerData) {
        List<String> mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        for (ViewBannerData.ViewBannerItem bannerDatum : bannerData) {
            mBannerTitleList.add(bannerDatum.getDesc());
            bannerImageList.add(bannerDatum.getImagePath());
        }

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(bannerData.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        mBanner.setOnBannerListener(position -> mPresenter.doBannerClick(bannerData.get(position)));
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void showFeedArticleData(ViewFeedArticleListData viewFeedArticleListData) {
        if (RECYCLER_VIEW_STATE == REFRESH) {
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.finishRefresh(500);
            mFeedArticleDataList.clear();
        }
        if (RECYCLER_VIEW_STATE == LOAD_MORE) {
            mRefreshLayout.finishLoadMore(500);
            if (viewFeedArticleListData.getItems().size() == 0) {
                mRefreshLayout.setEnableLoadMore(false);
                showToast(getString(R.string.load_more_no_data_ganhuo));
            }
        }
        if (mPresenter.getCurrentPage() == Constants.TYPE_MAIN_PAGER) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);

        }
        mFeedArticleDataList.addAll(viewFeedArticleListData.getItems());
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
