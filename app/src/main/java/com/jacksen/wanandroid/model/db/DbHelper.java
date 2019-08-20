package com.jacksen.wanandroid.model.db;

import com.jacksen.wanandroid.model.bean.db.HistorySearchData;

import java.util.List;

public interface DbHelper {

    /**
     * 增加历史数据
     *
     * @param data  added string
     * @return  List<HistoryData>
     */
    List<HistorySearchData> addHistoryData(String data);

    /**
     * Clear search history data
     */
    void clearHistoryData();

    /**
     * Load all history data
     *
     * @return List<HistoryData>
     */
    List<HistorySearchData> loadAllHistoryData();

}
