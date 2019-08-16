package com.jacksen.wanandroid.presenter.todo.activity.create;

import android.support.annotation.ColorInt;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoCreateContract {
    public interface View extends AbstractView {

        /**
         * 设置todo标题
         */
        void setTitleText(String text);

        /**
         * 设置todo内容
         */
        void setContentText(String text);

        /**
         * 设置todo时间
         */
        void setDateText(String text);

        /**
         * 设置todo分类及颜色
         */
        void setTypeTextColor(String text, @ColorInt int color);

        /**
         * 设置todo优先级及眼颜色
         */
        void setPriorityTextColor(String text, @ColorInt int color);

        /**
         * 设置todo状态及颜色
         */
        void setStatusTextColor(String text, @ColorInt int color);

        /**
         * 设置toolbar标题
         */
        void setToolbarTitleText(String text);

        /**
         * 设置todo按钮文字
         */
        void setBtnText(String text);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 选择时间
         */
        void doSwitchDateClick();

        /**
         * 选择类型
         */
        void doSwitchTypeClick();

        /**
         * 选择优先级
         */
        void doSwitchPriorityClick();

        /**
         * 选择状态
         */
        void doSwitchStatusClick();

        /**
         * 新建todo
         */
        void doCreateClick(String title, String content);

    }
}
