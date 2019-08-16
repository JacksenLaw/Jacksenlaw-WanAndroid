package com.jacksen.wanandroid.view.bean.todo;

import java.util.List;

/**
 * 作者： LuoM
 * 创建时间：2019/8/15/0015
 * 描述：筛选列表数据
 * 版本号： v1.0.0
 * 更新时间：2019/8/15/0015
 * 更新内容：
 */
public class FilterBean {

    private String title;
    private List<FilterBeanItem> items;

    public FilterBean(String title, List<FilterBeanItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<FilterBeanItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "FilterBean{" +
                "title='" + title + '\'' +
                ", items='" + items + '\'' +
                '}';
    }

    public static class FilterBeanItem {
        private String title;
        private String key;
        private String value;
        private boolean isSelected;

        public FilterBeanItem(String title, String key, String value) {
            this.title = title;
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getTitle() {
            return title;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "FilterBeanItem{" +
                    "title='" + title + '\'' +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

}

