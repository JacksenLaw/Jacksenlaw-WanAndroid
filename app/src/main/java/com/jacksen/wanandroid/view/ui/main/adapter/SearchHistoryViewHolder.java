package com.jacksen.wanandroid.view.ui.main.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jacksen.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： LuoM
 * 时间： 2019/3/31 0031
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchHistoryViewHolder extends BaseViewHolder {

    @BindView(R.id.item_search_history_tv)
    TextView mSearchHistoryTv;

    public SearchHistoryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
