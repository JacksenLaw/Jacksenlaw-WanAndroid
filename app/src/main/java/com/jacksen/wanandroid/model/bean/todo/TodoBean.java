package com.jacksen.wanandroid.model.bean.todo;

import java.util.List;

/**
 * 作者： LuoM
 * 创建时间：2019/8/13/0013
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/13/0013
 * 更新内容：
 */
public class TodoBean {


    /**
     * curPage : 1
     * datas : [{"completeDate":null,"completeDateStr":"","content":"","date":1565539200000,"dateStr":"2019-08-12","id":15730,"priority":0,"status":0,"title":"信件标题","type":0,"userId":12700},{"completeDate":null,"completeDateStr":"","content":"","date":1565539200000,"dateStr":"2019-08-12","id":15731,"priority":0,"status":0,"title":"新建工作todo","type":1,"userId":12700},{"completeDate":null,"completeDateStr":"","content":"","date":1565539200000,"dateStr":"2019-08-12","id":15732,"priority":0,"status":0,"title":"新建学习todo","type":2,"userId":12700},{"completeDate":null,"completeDateStr":"","content":"","date":1565539200000,"dateStr":"2019-08-12","id":15733,"priority":0,"status":0,"title":"新建生活todo","type":3,"userId":12700},{"completeDate":1565452800000,"completeDateStr":"2019-08-11","content":"南五马路","date":1565452800000,"dateStr":"2019-08-11","id":15691,"priority":0,"status":1,"title":"明天午饭吃老四季","type":3,"userId":12700}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 5
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "TodoBean{" +
                "curPage=" + curPage +
                ", offset=" + offset +
                ", over=" + over +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                ", datas=" + datas +
                '}';
    }

    public static class DatasBean {
        /**
         * completeDate : null
         * completeDateStr :
         * content :
         * date : 1565539200000
         * dateStr : 2019-08-12
         * id : 15730
         * priority : 0
         * status : 0
         * title : 信件标题
         * type : 0
         * userId : 12700
         */

        private Object completeDate;
        private String completeDateStr;
        private String content;
        private long date;
        private String dateStr;
        private int id;
        private int priority;
        private int status;
        private String title;
        private int type;
        private int userId;

        public Object getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(Object completeDate) {
            this.completeDate = completeDate;
        }

        public String getCompleteDateStr() {
            return completeDateStr;
        }

        public void setCompleteDateStr(String completeDateStr) {
            this.completeDateStr = completeDateStr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "completeDate=" + completeDate +
                    ", completeDateStr='" + completeDateStr + '\'' +
                    ", content='" + content + '\'' +
                    ", date=" + date +
                    ", dateStr='" + dateStr + '\'' +
                    ", id=" + id +
                    ", priority=" + priority +
                    ", status=" + status +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", userId=" + userId +
                    '}';
        }
    }
}
