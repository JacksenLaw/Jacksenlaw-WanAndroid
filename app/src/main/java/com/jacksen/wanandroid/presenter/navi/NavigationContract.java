package com.jacksen.wanandroid.presenter.navi;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.navi.ElemeGroupedItem;

import java.util.List;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NavigationContract {
    public interface View extends AbstractView {

        /**
         * 导航数据
         */
        void showNaviData(List<ElemeGroupedItem> items);

        /**
         * 滚动到position位置
         */
        void scrollToTheTop(int position);
        /**
         * 是否收藏
         */
        void showCollect(boolean isCollect);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 获取导航数据
         */
        void getNaviData();

    }
}
