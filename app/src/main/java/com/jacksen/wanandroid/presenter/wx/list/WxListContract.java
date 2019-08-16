package com.jacksen.wanandroid.presenter.wx.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxListContract {

    public interface View extends AbstractView {

        /**
         * 文章列表数据
         *
         * @param feedArticleListBean feedArticleListBean
         */
        void showWxList(ViewFeedArticleListData feedArticleListBean);

        /**
         * 搜索数据
         *
         * @param feedArticleListBean feedArticleListBean
         */
        void showWxSearchView(ViewFeedArticleListData feedArticleListBean);

        /**
         * 收藏、取消收藏文章
         *
         * @param position        Position
         * @param feedArticleData FeedArticleBean
         */
        void onCollectArticleData(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData);

        /**
         * 列表回滚
         */
        void scrollToTheTop(int position);

        /**
         * 详情页收藏、取消收藏文章事件
         */
        void onEventCollect(int position, boolean isCollect);
    }

    interface Presenter extends AbstractPresenter<View> {
        /**
         * 下拉刷新
         */
        void onRefresh();
        /**
         * 上啦加载
         */
        void onLoadMore();
        /**
         * 搜索
         */
        void doSearchClick(String searchText);

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
