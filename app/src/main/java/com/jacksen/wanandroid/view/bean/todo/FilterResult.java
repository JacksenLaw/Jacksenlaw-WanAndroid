package com.jacksen.wanandroid.view.bean.todo;

/**
 * 作者： LuoM
 * 创建时间：2019/8/15/0015
 * 描述： 已筛选出的数据
 * 版本号： v1.0.0
 * 更新时间：2019/8/15/0015
 * 更新内容：
 */
public class FilterResult {

    private String title;
    private String key;
    private String value;

    public FilterResult(String title, String key, String value) {
        this.title = title;
        this.key = key;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "FilterResult{" +
                "title='" + title + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
