package com.jacksen.wanandroid.view.ui.knowledge.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.view.bean.knowledge.ViewKnowledgeListData;
import com.jacksen.wanandroid.view.ui.knowledge.viewholder.KnowledgeHierarchyViewHolder;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<ViewKnowledgeListData.ViewKnowledgeItem, KnowledgeHierarchyViewHolder> {

    public KnowledgeHierarchyAdapter(@Nullable List<ViewKnowledgeListData.ViewKnowledgeItem> data) {
        super(R.layout.item_knowledge_hierarchy, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyViewHolder helper, ViewKnowledgeListData.ViewKnowledgeItem item) {
        helper.setText(R.id.item_knowledge_hierarchy_title, TextUtils.isEmpty(item.getTitle()) ? "" : item.getTitle());
        helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());
        helper.setText(R.id.item_knowledge_hierarchy_content, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
    }
}
