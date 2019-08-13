package com.jacksen.wanandroid.view.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.util.CommonUtils;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/3/31 0031
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class HistorySearchAdapter extends BaseQuickAdapter<HistorySearchData, SearchHistoryViewHolder> {

    public HistorySearchAdapter(@Nullable List<HistorySearchData> data) {
        super(R.layout.item_search_history, data);
    }

    @Override
    protected void convert(SearchHistoryViewHolder helper, HistorySearchData historyData) {
        helper.setText(R.id.item_search_history_tv, historyData.getData());
        helper.setTextColor(R.id.item_search_history_tv, CommonUtils.randomColor());

        helper.addOnClickListener(R.id.item_search_history_tv);
    }
}
