package com.jacksen.wanandroid.view.ui.project.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.util.GlideImageLoader;
import com.jacksen.wanandroid.view.bean.project.ViewProjectListData;
import com.jacksen.wanandroid.view.ui.project.viewholder.ProjectListViewHolder;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectListAdapter extends BaseQuickAdapter<ViewProjectListData.ViewProjectListItem, ProjectListViewHolder> {

    public ProjectListAdapter(@Nullable List<ViewProjectListData.ViewProjectListItem> data) {
        super(R.layout.item_project_list, data);
    }

    @Override
    protected void convert(ProjectListViewHolder helper, ViewProjectListData.ViewProjectListItem item) {
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            GlideImageLoader.load(mContext, item.getEnvelopePic(), helper.getView(R.id.item_project_list_iv));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_project_list_title_tv, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDesc())) {
            helper.setText(R.id.item_project_list_content_tv, item.getDesc());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.item_project_list_time_tv, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getApkLink())) {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_project_list_install_tv);
    }
}
