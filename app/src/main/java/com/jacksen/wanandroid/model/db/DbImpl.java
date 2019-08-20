package com.jacksen.wanandroid.model.db;

import com.jacksen.wanandroid.model.bean.db.HistorySearchData;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/31 0031
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class DbImpl implements DbHelper {

    @Inject
    DbImpl() {

    }

    @Override
    public List<HistorySearchData> addHistoryData(String historyData) {
        for (HistorySearchData data : loadAllHistoryData()) {
            if (data.getData().equals(historyData)) {
                return loadAllHistoryData();
            }
        }
        createHistoryData(historyData);
        return loadAllHistoryData();
    }

    private void createHistoryData(String historyData) {
        HistorySearchData data = new HistorySearchData();
        data.setData(historyData);
        data.save();
    }

    @Override
    public List<HistorySearchData> loadAllHistoryData() {
        //只展示最新搜索的10条数据
        List<HistorySearchData> list = new ArrayList<>();
        for (int i = LitePal.findAll(HistorySearchData.class).size() - 1; i >= 0; i--) {
            list.add(LitePal.findAll(HistorySearchData.class).get(i));
        }
        if (list.size() > 10) {
            return list.subList(0, 10);
        } else {
            return list;
        }
    }

    @Override
    public void clearHistoryData() {
        LitePal.deleteAll(HistorySearchData.class);
    }
}
