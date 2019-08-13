package com.jacksen.wanandroid.view.bean.useful_search;

import com.jacksen.wanandroid.base.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/4/18 0018
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewTextBean extends ViewData {

    private ArrayList<ViewTextItem> items;

    public ViewTextBean(ArrayList<ViewTextItem> items) {
        this.items = items;
    }

    public ArrayList<ViewTextItem> getItems() {
        return items;
    }

    public static class ViewTextItem extends ViewData {
        private String text;

        public ViewTextItem(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
