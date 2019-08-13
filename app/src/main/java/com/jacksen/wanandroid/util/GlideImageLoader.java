package com.jacksen.wanandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jacksen.wanandroid.app.WanAndroidApp;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class GlideImageLoader {
    /**
     * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
     *
     * @param context context
     * @param url     image url
     * @param iv      imageView
     */
    public static void load(Context context, String url, ImageView iv) {
        if (!WanAndroidApp.getAppComponent().getDataManager().isNoImageModel()) {
            Glide.with(context).load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)).into(iv);
        }
    }
}
