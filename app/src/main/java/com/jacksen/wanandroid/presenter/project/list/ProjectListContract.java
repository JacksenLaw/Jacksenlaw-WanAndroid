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
        /**
         * 展示项目详情列表数据
         */
        void showProjectListData(ViewProjectListData items);

        /**
         * 滚动到position位置
         */
        void scrollToTheTop(int position);

        /**
         * 收藏事件
         */
        void onEventCollect(int position, boolean isCollect);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 上拉加载
         */
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
