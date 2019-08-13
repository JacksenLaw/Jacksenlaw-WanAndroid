package com.jacksen.wanandroid.presenter.register;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.model.http.RxUtils;

import javax.inject.Inject;

/**
 * @author Luo
 * @date 2018/11/13 0013
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void toRegister(String account, String password, String rePassword) {
        addSubscribe(dataManager.getRegisterData(account, password, rePassword)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<LoginBean>(getView()) {
                    @Override
                    public void onNext(LoginBean loginData) {
                        getView().showToast(getActivity().getString(R.string.register_success));
                        getView().onRegisterSuccessful();
                    }

                }));
    }
}
