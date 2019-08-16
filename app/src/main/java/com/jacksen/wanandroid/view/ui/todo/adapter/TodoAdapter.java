package com.jacksen.wanandroid.view.ui.todo.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;

import java.util.List;

/**
 * 作者： LuoM
 * 创建时间：2019/8/14/0014
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/14/0014
 * 更新内容：
 */
public class TodoAdapter extends BaseItemDraggableAdapter<ViewTodoData.ViewTodoItem, BaseViewHolder> {

    public TodoAdapter(@Nullable List<ViewTodoData.ViewTodoItem> data) {
        super(R.layout.item_todo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewTodoData.ViewTodoItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (!TextUtils.isEmpty(item.getTypeText())) {
            helper.setText(R.id.tv_type, item.getTypeText());
            if ("工作".equals(item.getTypeText())) {
                helper.setVisible(R.id.tv_type, true);
                helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_todo_work_type);
            } else if ("学习".equals(item.getTypeText())) {
                helper.setVisible(R.id.tv_type, true);
                helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_todo_study_type);
            } else if ("生活".equals(item.getTypeText())) {
                helper.setVisible(R.id.tv_type, true);
                helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_todo_study_type);
            }
        }
        helper.setText(R.id.tv_content, item.getContent());
        //计划完成时间
        String planCompletionDate = String.format(mContext.getString(R.string.todo_plan_completion_date), item.getPlanDate());
        helper.setText(R.id.tv_date_plan, planCompletionDate);
        if (!TextUtils.isEmpty(item.getCompleteDate())) {
            //实际完成时间
            helper.setVisible(R.id.tv_date_completed, true);
            String actualCompletionDate = String.format(mContext.getString(R.string.todo_actual_completion_date), item.getCompleteDate());
            helper.setText(R.id.tv_date_completed, actualCompletionDate);
        }
    }
}
