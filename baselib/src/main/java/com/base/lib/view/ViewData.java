package com.base.lib.view;

import java.util.HashMap;

/**
 * 作者： LuoM
 * 时间： 2018/12/7 0007
 * 描述： P
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewData {

    /**
     * 多个对象数据缓存区
     */
    HashMap<String, Object> dataMap;

    public <T extends Object> void putData(String key, T data) {
        if (dataMap == null)
            dataMap = new HashMap<String, Object>();
        dataMap.put(key, data);
    }

    public <T extends Object> T getData(String key) {
        if (dataMap == null)
            throw new RuntimeException("data is null!");
        Object res = dataMap.get(key);
        if (res == null)
            throw new RuntimeException("data is null!dataMap not found!");
        return (T) res;
    }

    /**
     * 单个对象数据缓存区
     */
    Object mData;

    public <T extends Object> void setData(T data) {
        mData = data;
    }

    public <T extends Object> T getData() {
        return (T) mData;
    }


}
