package com.jacksen.wanandroid.presenter.wx;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.view.bean.wx.ViewWxAuthorBean;

import java.util.ArrayList;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxContract {

    public interface View extends AbstractView {

        void showWxAuthor(ViewWxAuthorBean wxAuthorBean);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 获取公众号作者列表
         */
        void getWxAuthorList();

    }
}
