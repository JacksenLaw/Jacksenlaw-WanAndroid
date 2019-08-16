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

        void showSwitchProject();

        void showSwitchNavigation();

        /**
         * 只是体系数据
         *
         * @param pageTitle       标题栏数据
         * @param isSingleChapter 标记是否为搜索后的数据
         * @param chapterName     单个标题栏数据，用于搜索后显示一个分类结果
         */
        void onKnowledgeHierarchyData(List<String> pageTitle, boolean isSingleChapter, String chapterName);

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
