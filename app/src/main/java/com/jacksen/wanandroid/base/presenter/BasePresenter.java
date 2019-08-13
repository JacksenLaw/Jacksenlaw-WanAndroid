package com.jacksen.wanandroid.base.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jacksen.wanandroid.app.receiver.NetWorkBroadcastReceiver;
import com.jacksen.wanandroid.app.receiver.observer.NetObserverManager;
import com.jacksen.wanandroid.app.receiver.observer.SubjectNetObserver;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.util.KLog;

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
public abstract class BasePresenter<T extends AbstractView> extends DataManagerPresenter<T>
        implements SubjectNetObserver {

    private WeakReference<T> mView;
    private CompositeDisposable compositeDisposable;

    private boolean isNetConnected;
    private NetWorkBroadcastReceiver broadcastReceiver;

    public BasePresenter(DataManager dataManager) {
        super(dataManager);
    }

    boolean isNetConnected() {
        return isNetConnected;
    }

    public T getView() {
        return mView.get();
    }

    public AppCompatActivity getActivity() {
        return (AppCompatActivity) mView.get();
    }

    public Fragment getFragment() {
        return (Fragment) mView.get();
    }

    @Override
    public void attachView(T view) {
        mView = new WeakReference<T>(view);
        if (broadcastReceiver == null) {
            broadcastReceiver = new NetWorkBroadcastReceiver();
        }
        NetObserverManager.getInstance().addObserver(this);
    }

    @Override
    public void showLoadingView() {
        if (NetworkUtils.isConnected()) {
            getView().showLoading();
        }
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        NetObserverManager.getInstance().removeObserver(this);
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

    @Override
    public void onNetWorkChange(boolean isConnected) {
        KLog.i("网络已变化  " + isConnected);
        isNetConnected = isConnected;
    }

}
