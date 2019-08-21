package com.jacksen.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.util.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： AbstractSimpleFragment
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class AbstractSimpleFragment extends SkinBaseFragment {

    private Unbinder unbinder;


    protected void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllView(viewGroup.getChildAt(i));
            }
            removeViewInSkinInflaterFactory(v);
        } else {
            removeViewInSkinInflaterFactory(v);
        }
    }

    private void removeViewInSkinInflaterFactory(View v) {
        if (getSkinInflaterFactory() != null) {
            //此方法用于Activity中Fragment销毁的时候，移除Fragment中的View
            getSkinInflaterFactory().removeSkinView(v);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initOnCreateView();
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
        initListener();
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
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
     * 有些初始化必须在onCreateView中，例如setAdapter,
     * 否则，会弹出 No adapter attached; skipping layout
     */
    protected abstract void initOnCreateView();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

}
