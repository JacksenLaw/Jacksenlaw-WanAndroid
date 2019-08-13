package com.jacksen.wanandroid.presenter.splash;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SplashContract {

    public interface View extends AbstractView{

    }

    public interface Presenter extends AbstractPresenter<View>{
        /**
         * jump到主页
         */
        void jumpToMain();
    }

}
