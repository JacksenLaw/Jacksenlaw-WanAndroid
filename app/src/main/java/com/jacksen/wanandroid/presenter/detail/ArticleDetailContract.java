package com.jacksen.wanandroid.presenter.detail;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * @author Luo
 * @date 2018/11/9 0009
 */
public interface ArticleDetailContract {

    interface View extends AbstractView {

        /**
         * 收藏成功
         *
         * @param isCollect isCollect
         */
        void showCollectArticleData(boolean isCollect);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 分享
         *
         * @param title title
         * @param link  link
         */
        void doShare(String title, String link);

        /**
         * 收藏
         *
         * @param title         title
         * @param isCollectPage 是否是 收藏页面过来
         * @param articleId     文章id
         */
        void doCollectEvent(CharSequence title, boolean isCollectPage, int articleId);

        /**
         * 使用系统自带浏览器打开
         */
        void doOpenWithSystemBrowser(String uri);

    }
}
