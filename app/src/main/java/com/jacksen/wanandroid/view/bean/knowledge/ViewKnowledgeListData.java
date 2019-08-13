package com.jacksen.wanandroid.view.bean.knowledge;

import com.jacksen.wanandroid.base.view.ViewData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewKnowledgeListData extends ViewData  {

    private ArrayList<ViewKnowledgeItem> items;

    public ViewKnowledgeListData(ArrayList<ViewKnowledgeItem> items) {
        this.items = items;
    }

    public ArrayList<ViewKnowledgeItem> getItems() {
        return items;
    }

    public static class ViewKnowledgeItem extends ViewData implements Serializable{
        private String title;
        private String content;

        public ViewKnowledgeItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "ViewKnowledgeItem{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

}
