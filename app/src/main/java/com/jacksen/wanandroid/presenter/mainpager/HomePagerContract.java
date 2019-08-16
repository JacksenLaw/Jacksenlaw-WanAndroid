package com.jacksen.wanandroid.presenter.mainpager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.main.ViewBannerData;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

import java.util.ArrayList;

/**
 * @author Luo
 * @date 2018/11/9 0009
 */
public interface HomePagerContract {

    interface View extends AbstractView {
        /**
         * 轮播图数据
         *
         * @param items items
         */
        void showBannerData(ArrayList<ViewBannerData.ViewBannerItem> items);

        /**
         * 文章列表数据
         *
         * @param viewFeedArticleListData feedArticleListData
         */
        void showFeedArticleData(ViewFeedArticleListData viewFeedArticleListData);

        /**
         * 列表回滚
         */
        void scrollToTheTop(int position);

        void setNightModel();

        /**
         * 收藏、取消收藏文章
         *
         * @param position        Position
         * @param feedArticleData FeedArticleBean
         */
        void onCollectArticleData(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData);

        /**
         * 详情页收藏、取消收藏文章事件
         */
        void onEventCollect(int position, boolean isCollect);
    }

    interface Presenter extends AbstractPresenter<View> {

        void onRefresh();

        void onLoadMore();

        /**
         * 获取所有数据（轮播图和文章列表数据）
         *
         * @param pageNo 分页
         */
        void getAllData(int pageNo);

        /**
         * 获取文章列表数据
         *
         * @param isShowError isShowError
         * @param pageNo      分页
         */
        void getFeedArticleList(int pageNo);


        /**
         * 轮播图点击事件
         *
         * @param item item
         */
        void doBannerClick(ViewBannerData.ViewBannerItem item);

        /**
         * 去收藏
         *
         * @param adapter  adapter
         * @param position position
         */
        void doCollectClick(BaseQuickAdapter adapter, int position);

        /**
         * 跳转到知识体系栏目
         *
         * @param adapter  adapter
         * @param position position
         */
        void doTurnChapterKnowledgePager(BaseQuickAdapter adapter, int position);

        /**
         * 标签点击事件
         *
         * @param adapter  adapter
         * @param position position
         */
        void clickTag(BaseQuickAdapter adapter, int position);

        /**
         * 文章item点击事件
         *
         * @param adapter  adapter
         * @param view     view
         * @param position position
         */
        void doItemClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

    }

}
