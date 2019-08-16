package com.jacksen.wanandroid.presenter.todo.fragment.filter;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.presenter.todo.activity.TodoPresenter;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class FilterContract {
    public interface View extends AbstractView {

        void showFilterData(List<FilterBean> items);

    }

    interface Presenter extends AbstractPresenter<View> {

        void getFilterData();

    }
}
