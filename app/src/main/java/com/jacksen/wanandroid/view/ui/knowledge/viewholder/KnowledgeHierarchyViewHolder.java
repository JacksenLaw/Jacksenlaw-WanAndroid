package com.jacksen.wanandroid.view.ui.knowledge.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jacksen.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowledgeHierarchyViewHolder extends BaseViewHolder {

    @BindView(R.id.item_knowledge_hierarchy_title)
    TextView mTitle;
    @BindView(R.id.item_knowledge_hierarchy_content)
    TextView mContent;

    public KnowledgeHierarchyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
