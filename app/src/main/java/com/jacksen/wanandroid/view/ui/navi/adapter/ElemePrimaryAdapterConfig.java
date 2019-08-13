package com.jacksen.wanandroid.view.ui.navi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.presenter.navi.NavigationPresenter;
import com.jacksen.wanandroid.util.KLog;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

/**
 * 作者： LuoM
 * 时间： 2019/8/10
 * 描述：
 * 版本： v1.0.0
 * 更新时间： 2019/8/10
 * 本次更新内容：
 */
public class ElemePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private static final int SPAN_COUNT_FOR_GRID_MODE = 2;
    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;

    private Context mContext;

    private boolean isNightMode;

    public void setNightMode(boolean isNightMode) {
        this.isNightMode = isNightMode;
    }

    public ElemePrimaryAdapterConfig(NavigationPresenter presenter) {
        isNightMode = presenter.isNightMode();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_navi_group;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        TextView tvTitle = holder.getView(R.id.tv_group);
        tvTitle.setText(title);

        if (isNightMode) {
            tvTitle.setBackgroundColor(mContext.getResources().getColor(
                    selected ? R.color.colorNaviGroupSelected_Night : R.color.colorNaviGroupUnSelected_Night));
        } else {
            tvTitle.setBackgroundColor(mContext.getResources().getColor(
                    selected ? R.color.colorNaviGroupSelected : R.color.colorNaviGroupUnSelected));
        }
        if (isNightMode) {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorNaviGroupText_Night));
        }
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        tvTitle.setFocusable(selected);
        tvTitle.setFocusableInTouchMode(selected);
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
        KLog.i("onItemClick");
    }
}
