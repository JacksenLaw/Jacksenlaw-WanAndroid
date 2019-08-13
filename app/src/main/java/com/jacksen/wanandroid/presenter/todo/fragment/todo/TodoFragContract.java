package com.jacksen.wanandroid.presenter.todo.fragment.todo;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoFragContract {
    public interface View extends AbstractView {

        void showTodoData();

    }

    interface Presenter extends AbstractPresenter<View> {

        void getTodoData(int pageNo);

        void onRefresh();

        void onLoadMore();

    }
}
