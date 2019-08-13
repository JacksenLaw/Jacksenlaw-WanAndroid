package com.jacksen.wanandroid.model.http.base;

import android.text.TextUtils;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.model.http.exception.ServerException;
import com.jacksen.wanandroid.util.KLog;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

public abstract class BaseSubscribe <T> extends ResourceSubscriber<T> {

    private AbstractView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseSubscribe(AbstractView view){
        this.mView = view;
    }

    protected BaseSubscribe(AbstractView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseSubscribe(AbstractView view, boolean isShowError){
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseSubscribe(AbstractView view, String errorMsg, boolean isShowError){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        KLog.e(e.toString());
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.unKnown_error));
        }
        if (isShowError) {
            mView.showError();
        }
    }
}
