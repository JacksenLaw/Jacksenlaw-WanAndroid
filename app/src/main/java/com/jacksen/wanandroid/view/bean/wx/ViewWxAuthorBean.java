package com.jacksen.wanandroid.view.bean.wx;

import com.jacksen.wanandroid.base.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewWxAuthorBean extends ViewData {

    private ArrayList<ViewWxAuthorItemBean> items;

    public ViewWxAuthorBean(ArrayList<ViewWxAuthorItemBean> items) {
        this.items = items;
    }

    public ArrayList<ViewWxAuthorItemBean> getItems() {
        return items;
    }

    public static class ViewWxAuthorItemBean extends ViewData{
        private int authorId;
        private String authorName;

        public ViewWxAuthorItemBean(int authorId, String authorName) {
            this.authorId = authorId;
            this.authorName = authorName;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getAuthorName() {
            return authorName;
        }
    }
}
