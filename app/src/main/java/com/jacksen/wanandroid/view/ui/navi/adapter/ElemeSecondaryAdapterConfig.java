package com.jacksen.wanandroid.view.ui.navi.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.view.bean.navi.ElemeGroupedItem;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;

/**
 * 作者： LuoM
 * 时间： 2019/8/10
 * 描述：
 * 版本： v1.0.0
 * 更新时间： 2019/8/10
 * 本次更新内容：
 */
public class ElemeSecondaryAdapterConfig implements ILinkageSecondaryAdapterConfig<ElemeGroupedItem.ItemInfo> {
    private static final int SPAN_COUNT_FOR_GRID_MODE = 2;
    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;

    private int clickPosition = -1;

    public int getClickPosition() {
        return clickPosition;
    }

    private static int[] colors = new int[]{
            R.color.colorPrimary,
            android.R.color.holo_green_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_blue_bright,
    };

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return 0;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.item_navi_child;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.item_navi_header;
    }

    @Override
    public int getFooterLayoutId() {
        return 0;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.item_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT_FOR_GRID_MODE;
    }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {

        ((TextView) holder.getView(R.id.item_tv)).setText(item.info.getTitle());
        holder.getView(R.id.item_tv).setOnClickListener(v -> {
            clickPosition = holder.getAdapterPosition();
            startNavigationPager(v, v.getContext(), item.info);
        });

    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        TextView header = holder.getView(R.id.item_header);
        header.setText(item.header);
//            header.setTextColor(ContextCompat.getColor(mContext, colors[holder.getAdapterPosition() % colors.length]));
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        ((TextView) holder.getView(R.id.tv_secondary_footer)).setText("");
    }

    private static void startNavigationPager(View view, Context context, ElemeGroupedItem.ItemInfo mArticles) {
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view,
                view.getWidth() / 2,
                view.getHeight() / 2,
                0,
                0);
        JudgeUtils.startArticleDetailActivity(context,
                options,
                mArticles.getId(),
                mArticles.getTitle(),
                mArticles.getImgUrl(),
                mArticles.isCollect(),
                mArticles.isCollect(),
                false, BusConstant.NAVI_PAGE);
    }
}
