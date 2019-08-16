package com.jacksen.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.util.CommonUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * MVP模式的Base Dialog fragment
 */
@SuppressWarnings("unchecked")
public abstract class BaseDialogFragment<T extends AbstractPresenter> extends AbstractSimpleDialogFragment
        implements AbstractView {

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
        if (mPresenter != null) {
            mPresenter.injectLifecycleOwner(this);
            mPresenter.attachView(this);
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
    public void showToast( String message) {
        if (getActivity() == null) {
            return;
        }
        CommonUtils.showMessage(message);
    }

    @Override
    public void showSnackBar(View view,String message) {
        if (getActivity() == null) {
            return;
        }
        CommonUtils.showSnackMessage(getActivity(), message);
    }

    @Override
    public View getRootView() {
        return null;
    }
}
