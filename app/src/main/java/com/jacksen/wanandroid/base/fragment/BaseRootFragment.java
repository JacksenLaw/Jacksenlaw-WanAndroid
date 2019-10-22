package com.jacksen.wanandroid.base.fragment;

import android.support.annotation.CallSuper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.AbstractPresenter;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： BaseRootFragment  页面状态封装
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public abstract class BaseRootFragment<T extends AbstractPresenter> extends BaseFragment<T> {

    /**
     * 页面的三种状态 ：正常、等待、错误
     */
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    /**
     * 当前页面状态 默认值：正常
     */
    private int currentState = NORMAL_STATE;

    private View mNormalView;
    private View mErrorView;
    private View mLoadingView;
    private LottieAnimationView mLoadingAnimation;

    @Override
    protected void initEventAndData() {
        if (getView() == null) {
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'normal_view'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "normal_view's ParentView should be a ViewGroup.");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(_mActivity, R.layout.loading_view, parent);
        View.inflate(_mActivity, R.layout.error_view, parent);

        mLoadingView = parent.findViewById(R.id.loading_group);
        mErrorView = parent.findViewById(R.id.error_group);

        TextView reloadTv = mErrorView.findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(v -> reload());

        mLoadingAnimation = mLoadingView.findViewById(R.id.loading_animation);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
        super.initEventAndData();
        if (mPresenter != null) {
            mPresenter.showLoadingView();
        }
    }

    @Override
    public void onDestroyView() {
        if (mLoadingAnimation != null) {
            mLoadingAnimation.clearAnimation();
        }
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE || mLoadingView == null) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAnimation.setAnimation("anim_splash/loading_bus.json");
        mLoadingAnimation.loop(true);
        mLoadingAnimation.playAnimation();
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case LOADING_STATE:
                mLoadingAnimation.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
            default:
                break;
        }
    }

    @Override
    public void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public View getRootView() {
        return mNormalView;
    }

    @Override
    protected boolean isInnerFragment() {
        return false;
    }
}
