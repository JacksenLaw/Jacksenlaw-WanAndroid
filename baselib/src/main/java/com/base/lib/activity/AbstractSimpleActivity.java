package com.base.lib.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.lib.manager.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： AbstractSimpleActivity
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class AbstractSimpleActivity extends SwipeBackActivity implements View.OnClickListener {

    private Unbinder unbinder;
    protected AbstractSimpleActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
            mActivity = this;
            ActivityCollector.addActivity(this);
            onViewCreate(savedInstanceState);
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
    protected abstract void onViewCreate(Bundle savedInstanceState);

    /**
     * 点击事件
     */
    protected abstract void initListener();

    /**
     * View点击事件
     *
     * @param ids views id
     */
    protected void addOnClickListeners(@IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnClickListener(this);
            }
        }
    }

    /**
     * View点击
     **/
    protected abstract void widgetClick(View view);

    @Override
    public void onClick(View v) {
        if (!fastClick()) {
            widgetClick(v);
        }
    }

    private long lastClick = 0;

    /**
     * 防止快速点击
     *
     * @return true
     */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1500) {
            return true;
        }
        lastClick = System.currentTimeMillis();
        return false;
    }

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolbar();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

}
