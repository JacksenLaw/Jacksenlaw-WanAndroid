package com.jacksen.wanandroid.model.bean.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class HistorySearchData extends LitePalSupport {

    @Column(unique = true)
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
