package com.jacksen.wanandroid.view.ui.todo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;
import com.jacksen.wanandroid.view.bean.todo.FilterResult;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： LuoM
 * 创建时间：2019/8/15/0015
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/15/0015
 * 更新内容：
 */
public class FilterAdapter extends BaseQuickAdapter<FilterBean, BaseViewHolder> {

    private Map<String, FilterResult> results = new HashMap<>();

    public Map<String, FilterResult> getFilterResults() {
        return results;
    }

    public FilterAdapter(@Nullable List<FilterBean> data) {
        super(R.layout.item_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterBean item) {
        helper.setText(R.id.tv_filter_title, item.getTitle());
        TagFlowLayout flowLayout = helper.getView(R.id.filter_tagFlowLayout);
        FlowLayoutAdapter adapter = new FlowLayoutAdapter(item.getItems(), mContext);
        flowLayout.setAdapter(adapter);

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                adapter.setSelectPosition(position);
                adapter.notifyDataChanged();
                String title = item.getItems().get(position).getTitle();
                String key = item.getItems().get(position).getKey();
                String value = item.getItems().get(position).getValue();
                FilterResult filterResult = new FilterResult(title, key, value);

                results.put(filterResult.getKey(), filterResult);
                return true;
            }
        });
    }

    public static class FlowLayoutAdapter extends TagAdapter<FilterBean.FilterBeanItem> {
        Context mContext;
        int selectPosition = -1;

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }

        public FlowLayoutAdapter(List<FilterBean.FilterBeanItem> datas, Context context) {
            super(datas);
            mContext = context;
        }

        @Override
        public View getView(FlowLayout parent, int position, FilterBean.FilterBeanItem filterBeanItem) {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_layout_checkbox,
                    parent, false);
            tv.setText(filterBeanItem.getTitle());
            if (selectPosition == position) {
                tv.setTextColor(ContextCompat.getColor(mContext, R.color.light_deep_red));
                tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_filter_bg_checked));
            } else {
                tv.setTextColor(ContextCompat.getColor(mContext, R.color.colorBtnBlack));
                tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_filter_bg_unchecked));
            }
            return tv;
        }
    }

}
