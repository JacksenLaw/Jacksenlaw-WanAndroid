package com.jacksen.wanandroid.presenter.main;

import android.support.v4.app.Fragment;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public interface MainContract {
    interface View extends AbstractView {
        /**
         * 选择项目栏目
         */
        void selectProjectTab();

        /**
         * 选择导航栏目
         */
        void selectNavigationTab();

        /**
         * Show auto login view
         */
        void showAutoLoginView();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 去登出
         */
        void doLogOutClick();

        /**
         * 去登录
         */
        void doTurnLoginClick();

        /**
         * 去关于我们
         */
        void doTurnAboutClick();

        /**
         * 去todo
         */
        void doTodoClick();

        /**
         * 去设置
         */
        void doSettingClick();

        /**
         * 搜索
         */
        void doSearchClick();

        /**
         * 常用网站
         */
        void doUsefulSitesClick();

        /**
         * 控制fragment中列表回滚
         */
        void jumpToTheTop(Fragment fragment);

    }
}
