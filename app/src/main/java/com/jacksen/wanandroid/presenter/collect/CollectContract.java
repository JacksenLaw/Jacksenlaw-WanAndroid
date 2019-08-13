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

        void showCollectList(ViewFeedArticleListData feedArticleListBean);

        /**
         * 取消收藏
         */
        void showCancelCollect(int position);
    }

    interface Presenter extends AbstractPresenter<View> {

        void getCollectList(int pageNo);

        void doItemClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

        void doItemChildClickListener(BaseQuickAdapter adapter, android.view.View view, int position);

        void onRefresh();

        void onLoadMore();

    }
}
