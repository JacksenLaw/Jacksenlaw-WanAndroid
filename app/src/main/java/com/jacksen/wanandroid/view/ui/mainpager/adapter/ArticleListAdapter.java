package com.jacksen.wanandroid.view.ui.mainpager.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

import java.util.List;

/**
 * 文章列表适配器
 *
 * @author Luo
 * @date 2018/11/9 0009
 */
public class ArticleListAdapter extends BaseQuickAdapter<ViewFeedArticleListData.ViewFeedArticleItem, BaseViewHolder> {

    private boolean isCollect;

    public void setCollect(boolean collect) {
        isCollect = collect;
        notifyDataSetChanged();
    }

    public ArticleListAdapter(@Nullable List<ViewFeedArticleListData.ViewFeedArticleItem> data) {
        super(R.layout.item_article_list_pager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewFeedArticleListData.ViewFeedArticleItem item) {
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_article_pager_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getSuperChapterName())) {
            helper.setText(R.id.item_article_pager_chapterName, item.getSuperChapterName() + "/" + item.getChapterName());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.item_article_pager_niceDate, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_article_pager_title, item.getTitle());
        }

        setTag(helper, item);
        helper.setImageResource(R.id.item_article_pager_like_iv, item.isCollect() || isCollect ? R.drawable.ic_like : R.drawable.ic_like_article_not_selected);

        helper.addOnClickListener(R.id.item_article_pager_like_iv);
        helper.addOnClickListener(R.id.item_article_pager_chapterName);
        helper.addOnClickListener(R.id.item_article_pager_tag_red_tv);
    }

    private void setTag(BaseViewHolder helper, ViewFeedArticleListData.ViewFeedArticleItem item) {
        helper.getView(R.id.item_article_pager_tag_red_tv).setVisibility(View.GONE);
        helper.getView(R.id.item_article_pager_tag_green_tv).setVisibility(View.GONE);
        if (isCollect) {
            return;
        }
        if (!TextUtils.isEmpty(item.getSuperChapterName())) {
            if (item.getSuperChapterName().contains(mContext.getString(R.string.open_project))) {
                setRedTag(helper, mContext.getString(R.string.project));
            }
            if (item.getSuperChapterName().contains(mContext.getString(R.string.navigation))) {
                setRedTag(helper, mContext.getString(R.string.navigation));
            }
        }
        if (item.getNiceDate().contains(mContext.getString(R.string.minute))
                || item.getNiceDate().contains(mContext.getString(R.string.hour))
                || item.getNiceDate().contains(mContext.getString(R.string.one_day))) {
            helper.getView(R.id.item_article_pager_tag_green_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_article_pager_tag_green_tv, R.string.text_new);
            helper.setTextColor(R.id.item_article_pager_tag_green_tv, ContextCompat.getColor(mContext, R.color.light_green));
            helper.setBackgroundRes(R.id.item_article_pager_tag_green_tv, R.drawable.shape_tag_green_background);
        }
    }

    private void setRedTag(BaseViewHolder helper, String tagName) {
        helper.getView(R.id.item_article_pager_tag_red_tv).setVisibility(View.VISIBLE);
        helper.setText(R.id.item_article_pager_tag_red_tv, tagName);
        helper.setTextColor(R.id.item_article_pager_tag_red_tv, ContextCompat.getColor(mContext, R.color.light_deep_red));
        helper.setBackgroundRes(R.id.item_article_pager_tag_red_tv, R.drawable.selector_tag_red_background);
    }

}
