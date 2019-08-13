package com.jacksen.wanandroid.presenter.knowledge.know_detail_activity;

import android.content.Intent;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeActivityContract {
    public interface View extends AbstractView {

        /**
         * 显示本页标题
         *
         * @param title title
         */
        void showTitle(String title);

        /**
         * 往activity中添加fragment
         *
         * @param chapterId chapterId
         */
        void showFragment(int chapterId);

        /**
         * Show switch project
         */
        void showSwitchProject();

        /**
         * Show switch navigation
         */
        void showSwitchNavigation();

        void onKnowledgeHierarchyData(List<String> pageTitle, boolean isSingleChapter,String chapterName);
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 接收数据
         *
         * @param intent intent
         */
        void handData(Intent intent);

        /**
         * 滑动到顶部
         */
        void jumpToTop();

    }

}
