package com.jacksen.wanandroid.view.bean.project;

import com.jacksen.wanandroid.base.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2018/12/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewProjectListData extends ViewData {
    private ArrayList<ViewProjectListItem> items;

    public ViewProjectListData(ArrayList<ViewProjectListItem> items) {
        this.items = items;
    }

    public ArrayList<ViewProjectListItem> getItems() {
        return items;
    }

    public static class ViewProjectListItem extends ViewData {
        private String envelopePic;
        private String title;
        private String desc;
        private String niceDate;
        private String author;
        private String apkLink;
        private boolean isCollect;

        public ViewProjectListItem(String envelopePic, String title, String desc, String niceDate, String author, String apkLink,boolean isCollect) {
            this.envelopePic = envelopePic;
            this.title = title;
            this.desc = desc;
            this.niceDate = niceDate;
            this.author = author;
            this.apkLink = apkLink;
            this.isCollect = isCollect;
        }

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public String getAuthor() {
            return author;
        }

        public String getApkLink() {
            return apkLink;
        }
    }
}
