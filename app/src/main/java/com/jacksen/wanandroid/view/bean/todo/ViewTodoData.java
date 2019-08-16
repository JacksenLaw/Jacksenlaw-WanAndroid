package com.jacksen.wanandroid.view.bean.todo;

import com.jacksen.wanandroid.base.view.ViewData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： LuoM
 * 创建时间：2019/8/14/0014
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/14/0014
 * 更新内容：
 */
public class ViewTodoData extends ViewData implements Serializable {

    private ArrayList<ViewTodoItem> items;

    public ViewTodoData(ArrayList<ViewTodoItem> items) {
        this.items = items;
    }

    public ArrayList<ViewTodoItem> getItems() {
        return items;
    }

    public static class ViewTodoItem implements Serializable {

        private int id;//待办id
        private String title;//待办标题
        private String content;//待办内容
        private String planDate;//计划完成日期
        private String completeDate;//实际完成日期
        private int type;//待办事项类型 1:工作 2：学习 3：生活
        private int status;//完成状态 1:已完成 0：未完成
        private int priority;//优先级别 1:重要 2：一般 3：普通

        public ViewTodoItem(int id, String title, String content, String planDate, String completeDate, int type, int status, int priority) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.planDate = planDate;
            this.completeDate = completeDate;
            this.type = type;
            this.status = status;
            this.priority = priority;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getPlanDate() {
            return planDate;
        }

        public String getCompleteDate() {
            return completeDate;
        }

        public int getType() {
            return type;
        }

        public String getTypeText() {
            switch (type) {
                case 1:
                    return "工作";
                case 2:
                    return "学习";
                case 3:
                    return "生活";
            }
            return "";
        }

        public int getStatus() {
            return status;
        }
        public String getStatusText() {
            switch (status) {
                case 1:
                    return "已完成";
                case 0:
                    return "未完成";
            }
            return "";
        }

        public int getPriority() {
            return priority;
        }

        public String getPriorityText() {
            switch (priority) {
                case 1:
                    return "重要";
                case 2:
                    return "一般";
                case 3:
                    return "普通";
            }
            return "";
        }

        @Override
        public String toString() {
            return "ViewTodoItem{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", planDate='" + planDate + '\'' +
                    ", completeDate='" + completeDate + '\'' +
                    ", type=" + type +
                    ", status=" + status +
                    ", priority=" + priority +
                    '}';
        }
    }

}
