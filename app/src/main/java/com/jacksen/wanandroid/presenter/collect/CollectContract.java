package com.jacksen.wanandroid.presenter.collect;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class CollectContract {
    public interface View extends AbstractView {

        /**
         * 收藏列表
         */
        void showCollectList(ViewFeedArticleListData feedArticleListBean);

        /**
         * 取消收藏
         */
        void showCancelCollect(int position);

        /**
         * 设置夜间模式后，通知更新列表的收藏图片颜色
         */
        void setNightModel();

        /**
         * 滚动
         */
        void scrollToTop(int position);
    }

    interface Presenter extends AbstractPresenter<View> {
        /**
         * 获取收藏数据
         */
        void getCollectList(int pageNo);

        /**
         * 进入详情
         */
        void doItemClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

        /**
         * 取消收藏事件
         */
        void doItemChildClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 上拉加载
         */
        void onLoadMore();

    }
}
