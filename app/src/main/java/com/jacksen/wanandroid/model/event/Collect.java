package com.jacksen.wanandroid.model.event;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 收藏事件
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class Collect {
    //判断是否收藏了文章： true：已收藏  false：已取消收藏
    private boolean isCollected;
    //跳转的类型：从哪里进来的
    private String type;

    public Collect(String type, boolean isCollected) {
        this.type = type;
        this.isCollected = isCollected;
    }

    public String getType() {
        return type;
    }

    public boolean isCollected() {
        return isCollected;
    }
}
