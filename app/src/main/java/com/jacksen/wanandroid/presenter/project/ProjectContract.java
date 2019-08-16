package com.jacksen.wanandroid.presenter.project;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.project.ViewProjectClassifyBean;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectContract {
    public interface View extends AbstractView {

        /**
         * s展示项目分类数据
         */
        void showProjectClassify(ArrayList<ViewProjectClassifyBean.ViewProjectClassifyItemBean> itemBeans);

    }

    interface Presenter extends AbstractPresenter<View> {
        /**
         * 获取项目分类数据
         */
        void getProjectClassifyData();

    }
}
