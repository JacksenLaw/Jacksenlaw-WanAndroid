package com.jacksen.wanandroid.presenter.project.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.project.ViewProjectListData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectListContract {
    public interface View extends AbstractView {

        void showProjectListData(ViewProjectListData items);

        void scrollToTheTop(int position);

        void onEventCollect(int position,boolean isCollect);

    }

    interface Presenter extends AbstractPresenter<View> {

        void onRefresh();

        void onLoadMore();

        /**
         * 文章item点击事件
         *
         * @param adapter  adapter
         * @param view     view
         * @param position position
         */
        void doItemClickListener(BaseQuickAdapter adapter, android.view.View view, int position);
    }
}
