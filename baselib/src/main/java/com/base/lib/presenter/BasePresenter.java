package com.base.lib.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.base.lib.view.AbstractView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： BasePresenter
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class BasePresenter<T extends AbstractView> implements AbstractPresenter<T> {

    private WeakReference<T> mView;
    private CompositeDisposable compositeDisposable;

    public T getView() {
        return mView.get();
    }

    public Activity getActivity() {
        return (Activity) mView.get();
    }

    public Fragment getFragment() {
        return (Fragment) mView.get();
    }

    @Override
    public void attachView(T view) {
        mView = new WeakReference<T>(view);
    }

    @Override
    public void showLoadingView() {
        getView().showLoading();
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void addRxBindingSubscribe(Disposable disposable) {
        addSubscribe(disposable);
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

}
