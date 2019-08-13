package com.jacksen.wanandroid.presenter.search;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

/**
 * 作者： LuoM
 * 时间： 2019/4/16 0016
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchListContract {
    public interface View extends AbstractView {

        void showSearchList(ViewFeedArticleListData viewFeedArticleListData);


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

        void doItemClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

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

        void onRefresh();

        void onLoadMore();
    }
}
