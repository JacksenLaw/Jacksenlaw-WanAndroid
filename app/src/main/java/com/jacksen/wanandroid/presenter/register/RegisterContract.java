package com.jacksen.wanandroid.presenter.register;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * @author Luo
 * @date 2018/11/13 0013
 */
public interface RegisterContract {
    interface View extends AbstractView {
        /**
         * 注册成功
         */
        void onRegisterSuccessful();
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 去注册
         *
         * @param account    账号
         * @param password   密码
         * @param rePassword 重复密码
         */
        void toRegister(String account, String password, String rePassword);
    }
}
