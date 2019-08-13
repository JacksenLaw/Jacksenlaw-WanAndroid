package com.jacksen.wanandroid.presenter.search;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;

import java.util.List;

public interface SearchContract {

    interface View extends AbstractView {

        /**
         * 展示历史搜索记录
         *
         * @param historyDataList List<HistoryData>
         */
        void showHistoryData(List<HistorySearchData> historyDataList);

        /**
         * 展示热搜记录
         *
         * @param items ViewTextBean
         */
        void showTopSearchData(ViewTextBean items);

        /**
         * Judge to the search list activity
         */
        void judgeToTheSearchListActivity();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 搜索
         *
         * @param searchData 关键字
         */
        void doSearchClick(String searchData);

        /**
         * 清除历史搜索记录
         */
        void doClearHistoryClick();

        /**
         * 从数据库中获取所有历史搜索
         */
        void loadAllHistoryData();

        /**
         * 添加搜索记录到历史记录库中
         *
         * @param searchData history data
         */
        void addHistoryData(String searchData);

        /**
         * 热搜
         */
        void getTopSearchData();
    }

}
