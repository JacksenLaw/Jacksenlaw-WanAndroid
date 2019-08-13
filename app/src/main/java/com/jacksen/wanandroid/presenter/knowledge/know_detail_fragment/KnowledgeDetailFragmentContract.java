package com.jacksen.wanandroid.presenter.knowledge.know_detail_fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeDetailFragmentContract {
    public interface View extends AbstractView {
        /**
         * Show Knowledge Hierarchy Detail Data
         *
         * @param feedArticleListData ViewFeedArticleListData
         */
        void showKnowledgeHierarchyDetailData(ViewFeedArticleListData feedArticleListData);

        /**
         * 收藏成功、取消收藏
         *
         * @param position        Position
         * @param feedArticleData FeedArticleBean
         */
        void onCollectArticleData(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData);

        /**
         * 详情页收藏、取消收藏文章事件
         */
        void onEventCollect(int position,boolean isCollect);

        /**
         * 滚动到顶部
         */
        void showScrollTheTop();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 知识列表
         *
         * @param page page num
         * @param cid  second page id
         */
        void getKnowledgeHierarchyDetailData(int page, int cid);

        /**
         * 收藏
         *
         * @param position        Position
         * @param feedArticleData ViewFeedArticleListData.ViewProjectListItem
         */
        void addCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData);

        /**
         * 取消收藏
         *
         * @param position        Position
         * @param feedArticleData ViewFeedArticleListData.ViewProjectListItem
         */
        void cancelCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData);

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

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

}
