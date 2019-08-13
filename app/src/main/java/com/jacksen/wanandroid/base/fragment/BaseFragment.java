package com.jacksen.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.util.CommonUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： MVP模式的BaseFragment
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class BaseFragment<T extends AbstractPresenter> extends AbstractSimpleFragment
        implements AbstractView {

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @CallSuper
    @Override
    protected void initEventAndData() {
        if (mPresenter != null) {
            mPresenter.showLoadingView();
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override
    public void useNightModel(boolean isNightMode) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (isAdded()) {
            CommonUtils.showMessage(errorMsg);
        }
    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLoginOutView() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showToast(String message) {
        CommonUtils.showMessage(message);
    }

    @Override
    public void showSnackBar(View view,String message) {
        CommonUtils.showSnackMessage(view, message);
    }
}
