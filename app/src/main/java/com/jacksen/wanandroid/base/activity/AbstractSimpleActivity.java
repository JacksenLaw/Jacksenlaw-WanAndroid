package com.jacksen.wanandroid.base.activity;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.jacksen.wanandroid.core.manager.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： AbstractSimpleActivity
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class AbstractSimpleActivity extends SkinBaseActivity implements LifecycleObserver {

    private Unbinder unbinder;
    protected AbstractSimpleActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
            mActivity = this;
            ActivityCollector.addActivity(this);
            onCreateView(savedInstanceState);
            initToolbar();
            initListener();
            initEventAndData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.finishActivity(this);
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 此activity是否可以滑动返回
     *
     * @return boolean
     */
    protected abstract boolean getSwipeBackEnable();

    /**
     * 在initEventAndData()之前执行
     */
    protected abstract void onCreateView(Bundle savedInstanceState);

    /**
     * 点击事件
     */
    protected abstract void initListener();

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolbar();

    /**
     * 获取状态栏的颜色
     * @return int
     */
    protected abstract int getStatusBarColor();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();


}
