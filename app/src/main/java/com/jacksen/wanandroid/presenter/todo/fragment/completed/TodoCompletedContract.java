package com.jacksen.wanandroid.presenter.todo.fragment.completed;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.todo.FilterResult;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoCompletedContract {
    public interface View extends AbstractView {
        /**
         * @param viewTodoData 待办数据
         * @param isFilter     是否为筛选后的数据
         */
        void showTodoCompletedData(ViewTodoData viewTodoData, boolean isFilter);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 获取已完成的todo数据
         */
        void getCompleteTodoData(int pageNo);

        /**
         * 更改todo状态为未完成状态
         */
        void doUndoneTodoClick(BaseQuickAdapter adapter, android.view.View view, int position);

        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 上拉加载
         */
        void onLoadMore();

        /**
         * 过滤数据
         */
        void filterData(List<FilterResult> results);

        /**
         * 滑动删除todo
         */
        void doDeleteSwipe(int id);

    }
}
