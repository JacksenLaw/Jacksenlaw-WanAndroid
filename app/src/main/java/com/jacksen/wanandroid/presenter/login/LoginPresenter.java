package com.jacksen.wanandroid.presenter.login;

import android.content.Intent;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.ui.main.activity.RegisterActivity;

import javax.inject.Inject;

/**
 * @author Luo
 * @date 2018/11/11 0011
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void doLoginClick(String account, String password) {
        addSubscribe(dataManager.getLoginData(account, password)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<LoginBean>(getView()) {
                    @Override
                    public void onNext(LoginBean loginData) {
                        getView().showToast(getActivity().getString(R.string.login_success));
                        setLoginAccount(account);
                        setLoginState(true);
                        getView().onLoginSuccessful();
                        LiveDataBus.get().with(BusConstant.LOGIN_STATE).postValue(true);
                    }
                }));
    }

    @Override
    public void doRegisterClick() {
        getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
    }
}
