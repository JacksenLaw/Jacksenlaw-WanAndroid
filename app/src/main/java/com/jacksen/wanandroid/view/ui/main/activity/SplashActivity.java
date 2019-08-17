package com.jacksen.wanandroid.view.ui.main.activity;

import android.animation.Animator;
import android.os.Build;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.splash.SplashContract;
import com.jacksen.wanandroid.presenter.splash.SplashPresenter;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.one_animation)
    LottieAnimationView mOneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView mTwoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView mThreeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView mFourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView mFiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView mSixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView mSevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView mEightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView mNineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView mTenAnimation;

    @Override
    protected int getLayoutId() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        return R.layout.activity_splash;
    }

    @Override
    protected boolean getSwipeBackEnable() {
        return false;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initEventAndData() {
//        startAnimation(mOneAnimation, "anim_splash/W.json");
//        startAnimation(mTwoAnimation, "anim_splash/A.json");
//        startAnimation(mThreeAnimation, "anim_splash/N.json");
//        startAnimation(mFourAnimation, "anim_splash/A.json");
//        startAnimation(mFiveAnimation, "anim_splash/N.json");
//        startAnimation(mSixAnimation, "anim_splash/D.json");
//        startAnimation(mSevenAnimation, "anim_splash/R.json");
//        startAnimation(mEightAnimation, "anim_splash/O.json");
//        startAnimation(mNineAnimation, "anim_splash/I.json");
//        startAnimation(mTenAnimation, "anim_splash/D.json");
        addAnimatorListener(mOneAnimation, mTwoAnimation, mThreeAnimation, mFourAnimation, mFiveAnimation,
                mSixAnimation, mSevenAnimation, mEightAnimation, mNineAnimation, mTenAnimation);
    }

    private void cancelAnimation() {
        cancelAnimation(mOneAnimation);
        cancelAnimation(mTwoAnimation);
        cancelAnimation(mThreeAnimation);
        cancelAnimation(mFourAnimation);
        cancelAnimation(mFiveAnimation);
        cancelAnimation(mSixAnimation);
        cancelAnimation(mSevenAnimation);
        cancelAnimation(mEightAnimation);
        cancelAnimation(mNineAnimation);
        cancelAnimation(mTenAnimation);
    }

    private void startAnimation(LottieAnimationView mLottieAnimationView, String animationName) {
        mLottieAnimationView.setAnimation(animationName);
        mLottieAnimationView.playAnimation();
    }

    private void addAnimatorListener(final LottieAnimationView... mLottieAnimationViews) {
        for (LottieAnimationView mLottieAnimationView : mLottieAnimationViews) {
            mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    onAnimationCancel(animation);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    cancelAnimation(mLottieAnimationView);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

    }

    private void cancelAnimation(LottieAnimationView mLottieAnimationView) {
        if (mLottieAnimationView != null) {
            mLottieAnimationView.cancelAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {

    }
}
