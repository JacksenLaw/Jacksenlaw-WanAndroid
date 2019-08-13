package com.jacksen.wanandroid.presenter.login;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * @author Luo
 * @date 2018/11/11 0011
 */
public interface LoginContract {

    interface View extends AbstractView{
        /**
         * 登录成功
         */
        void onLoginSuccessful();
    }

    interface Presenter extends AbstractPresenter<View>{

        /**
         * 登录
         * @param account 账号
         * @param password 密码
         */
        void doLoginClick(String account, String password);

        /**
         * 去注册
         */
        void doRegisterClick();
    }

}
