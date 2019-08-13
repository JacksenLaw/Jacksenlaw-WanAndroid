package com.jacksen.wanandroid.view.bean.main;

import com.jacksen.wanandroid.base.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2018/12/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewFeedArticleListData extends ViewData {
    private ArrayList<ViewFeedArticleItem> items;

    public ViewFeedArticleListData(ArrayList<ViewFeedArticleItem> items) {
        this.items = items;
    }

    public ArrayList<ViewFeedArticleItem> getItems() {
        return items;
    }

    public static class ViewFeedArticleItem extends ViewData {
        private String author;
        private String superChapterName;
        private String chapterName;
        private String niceDate;
        private String title;
        private boolean isCollect;

        public ViewFeedArticleItem(String author, String superChapterName, String chapterName, String niceDate, String title, boolean isCollect) {
            this.author = author;
            this.superChapterName = superChapterName;
            this.chapterName = chapterName;
            this.niceDate = niceDate;
            this.title = title;
            this.isCollect = isCollect;
        }

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public String getAuthor() {
            return author;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public String getChapterName() {
            return chapterName;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public String getTitle() {
            return title;
        }
    }
}
