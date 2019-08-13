package com.jacksen.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;
import com.jacksen.wanandroid.core.manager.PermissionManager;
import com.jacksen.wanandroid.util.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： MVP模式的BaseActivity
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity
        implements AbstractView, HasSupportFragmentInjector {

    //将fragment 注入到activity中(fragment中需使用： AndroidSupportInjection.inject(this); ,并创建对应@Module修饰的类)
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    //将presenter注入到activity中
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //每个继承此类的activity必须要有对应的@Module注解的类 ， 否则报错：java.lang.IllegalArgumentException: No injector factory bound for Class<XXX.class>
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(getSwipeBackEnable());
        setEdgeLevel(200);
    }

    @CallSuper
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void initEventAndData() {
        if (mPresenter != null) {
            mPresenter.showLoadingView();
        }
    }

    @Override
    protected boolean getSwipeBackEnable() {
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void useNightModel(boolean isNightMode) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CommonUtils.showMessage(errorMsg);
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
    public void showSnackBar(View view, String message) {
        CommonUtils.showSnackMessage(view, message);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public View getRootView() {
        return null;
    }
}
