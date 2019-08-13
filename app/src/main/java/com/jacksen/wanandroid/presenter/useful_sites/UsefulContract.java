package com.jacksen.wanandroid.presenter.useful_sites;

import android.view.View;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;
import com.zhy.view.flowlayout.FlowLayout;

/**
 * 作者： LuoM
 * 时间： 2019/4/18 0018
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class UsefulContract {
    public interface View extends AbstractView {

        void showUsefulSites(ViewTextBean viewTextBean);

    }

    public interface Presenter extends AbstractPresenter<View> {

        void doFlowLayoutClick(android.view.View view, int position, FlowLayout parent, ViewTextBean.ViewTextItem item);
    }
}
