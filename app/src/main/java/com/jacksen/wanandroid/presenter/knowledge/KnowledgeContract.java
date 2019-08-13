package com.jacksen.wanandroid.presenter.knowledge;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.knowledge.ViewKnowledgeListData;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeContract {

    public interface View extends AbstractView {

        /**
         * 知识体系数据
         * @param viewKnowledgeListData viewKnowledgeListData
         */
        void showKnowledgeHierarchyData(ViewKnowledgeListData viewKnowledgeListData);

        /**
         * 列表回滚
         */
        void scrollToTheTop(int position);

    }

    interface Presenter extends AbstractPresenter<View> {

        void onRefresh();

        void onLoadMore();

        void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position);

    }

}
