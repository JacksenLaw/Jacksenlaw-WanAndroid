package com.jacksen.component_home.bean.main;

import com.base.lib.view.ViewData;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2018/12/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ViewBannerData extends ViewData {

    private ArrayList<ViewBannerItem> list;

    public ViewBannerData(ArrayList<ViewBannerItem> list) {
        this.list = list;
    }

    public ArrayList<ViewBannerItem> getList() {
        return list;
    }

    public static class ViewBannerItem extends ViewData{
        private String desc;
        private String imagePath;

        public ViewBannerItem(String desc, String imagePath) {
            this.desc = desc;
            this.imagePath = imagePath;
        }

        public String getDesc() {
            return desc;
        }

        public String getImagePath() {
            return imagePath;
        }

    }

}
