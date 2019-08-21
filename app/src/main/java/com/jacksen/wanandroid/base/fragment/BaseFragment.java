package com.jacksen.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.util.KLog;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： MVP模式的BaseFragment
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@SuppressWarnings("unchecked")
public abstract class BaseFragment<T extends AbstractPresenter> extends AbstractSimpleFragment implements AbstractView {

    private long clickTime;
    @Inject
    protected T mPresenter;

    protected abstract boolean isInnerFragment();

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.injectLifecycleOwner(this);
            mPresenter.attachView(this);
        }
    }

    @CallSuper
    @Override
    protected void initEventAndData() {
        if (mPresenter != null) {
            mPresenter.injectEvent();
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
    public void showSnackBar(View view, String message) {
        CommonUtils.showSnackMessage(view, message);
    }

    @Override
    public View getRootView() {
        return _mActivity.getWindow().getDecorView();
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (isInnerFragment()) {
                KLog.i(isInnerFragment());
                _mActivity.finish();
                return true;
            }
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > 2000) {
                CommonUtils.showMessage(getString(R.string.double_click_exit_tint));
                clickTime = System.currentTimeMillis();
            } else {
                _mActivity.finish();
            }
        }
        return true;
    }
}
