package com.jacksen.wanandroid.core.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 作者： LuoM
 * 创建时间：2019/8/20/0020
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/20/0020
 * 更新内容：
 */
public class GsonFactory {

    private static Gson gson;

    public static Gson provideGson() {
        if (gson == null) {
            //.serializeNulls()配置gson可以序列化为null的字段
            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson;
    }

}
