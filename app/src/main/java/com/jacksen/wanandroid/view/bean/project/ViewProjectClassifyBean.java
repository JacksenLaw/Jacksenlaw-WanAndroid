package com.jacksen.wanandroid.view.bean.project;

import com.jacksen.wanandroid.base.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewProjectClassifyBean extends ViewData {

    private ArrayList<ViewProjectClassifyItemBean> items;

    public ViewProjectClassifyBean(ArrayList<ViewProjectClassifyItemBean> items) {
        this.items = items;
    }

    public ArrayList<ViewProjectClassifyItemBean> getItems() {
        return items;
    }

    public static class ViewProjectClassifyItemBean extends ViewData {
        private int projectId;
        private String projectName;

        public ViewProjectClassifyItemBean(int projectId, String projectName) {
            this.projectId = projectId;
            this.projectName = projectName;
        }

        public int getProjectId() {
            return projectId;
        }

        public String getProjectName() {
            return projectName;
        }
    }

}
