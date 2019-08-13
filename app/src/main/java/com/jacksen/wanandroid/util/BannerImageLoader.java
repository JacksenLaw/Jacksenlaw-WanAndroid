package com.jacksen.wanandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {
        if (!WanAndroidApp.getAppComponent().getDataManager().isNoImageModel()) {
            Glide.with(context).load(o).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)).into(imageView);
        }
    }
}