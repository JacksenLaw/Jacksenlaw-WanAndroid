package com.jacksen.wanandroid.model.bean.navi;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NavigationListBean {
    /**
     * "articles": [],
     * "cid": 272,
     * "name": "常用网站"
     */

    private List<NavigationArticleBean> articles;
    private int cid;
    private String name;

    public List<NavigationArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<NavigationArticleBean> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NavigationListBean{" +
                "articles=" + articles +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                '}';
    }

    public class NavigationArticleBean {
        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private int superChapterId;
        private String superChapterName;
        private long publishTime;
        private String title;
        private int visible;
        private int zan;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        @Override
        public String toString() {
            return "NavigationArticleBean{" +
                    "apkLink='" + apkLink + '\'' +
                    ", author='" + author + '\'' +
                    ", chapterId=" + chapterId +
                    ", chapterName='" + chapterName + '\'' +
                    ", collect=" + collect +
                    ", courseId=" + courseId +
                    ", desc='" + desc + '\'' +
                    ", envelopePic='" + envelopePic + '\'' +
                    ", id=" + id +
                    ", link='" + link + '\'' +
                    ", niceDate='" + niceDate + '\'' +
                    ", origin='" + origin + '\'' +
                    ", projectLink='" + projectLink + '\'' +
                    ", superChapterId=" + superChapterId +
                    ", superChapterName='" + superChapterName + '\'' +
                    ", publishTime=" + publishTime +
                    ", title='" + title + '\'' +
                    ", visible=" + visible +
                    ", zan=" + zan +
                    '}';
        }
    }

}
