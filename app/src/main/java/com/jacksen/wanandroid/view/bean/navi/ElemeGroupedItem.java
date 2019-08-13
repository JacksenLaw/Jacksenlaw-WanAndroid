package com.jacksen.wanandroid.view.bean.navi;

import com.kunminx.linkage.bean.BaseGroupedItem;

/**
 * 作者： LuoM
 * 创建时间：2019/8/9/0009
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/9/0009
 * 更新内容：
 */
public class ElemeGroupedItem extends BaseGroupedItem<ElemeGroupedItem.ItemInfo> {

    public ElemeGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ElemeGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private int id;
        private boolean collect;
        private String content;
        private String imgUrl;
        private String cost;

        public ItemInfo(String title, String group, String content) {
            super(title, group);
            this.content = content;
        }

        public ItemInfo(String title, String group, String content, String imgUrl) {
            this(title, group, content);
            this.imgUrl = imgUrl;
        }

        public ItemInfo(String title, String group, String content, String imgUrl, String cost) {
            this(title, group, content, imgUrl);
            this.cost = cost;
        }

        public ItemInfo(String title, String group, int id, boolean collect, String content, String imgUrl, String cost) {
            super(title, group);
            this.id = id;
            this.collect = collect;
            this.content = content;
            this.imgUrl = imgUrl;
            this.cost = cost;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "ItemInfo{" +
                    "id=" + id +
                    ", collect=" + collect +
                    ", content='" + content + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", cost='" + cost + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ElemeGroupedItem{" +
                "isHeader=" + isHeader +
                ", header='" + header + '\'' +
                ", info=" + info +
                '}';
    }
}
