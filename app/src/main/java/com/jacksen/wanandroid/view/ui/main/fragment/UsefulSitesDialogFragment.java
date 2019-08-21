package com.jacksen.wanandroid.view.ui.main.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.base.fragment.BaseDialogFragment;
import com.jacksen.wanandroid.presenter.useful_sites.UsefulContract;
import com.jacksen.wanandroid.presenter.useful_sites.UsefulPresenter;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.util.KLog;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;
import com.jacksen.wanandroid.widget.CircularRevealAnim;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/4/18 0018
 * 描述： 常用网站
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class UsefulSitesDialogFragment extends BaseDialogFragment<UsefulPresenter> implements UsefulContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;

    private CircularRevealAnim mCircularRevealAnim;

    @Override
    protected int getLayout() {
        return R.layout.fragment_useful;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }


    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    @Override
    protected void initCreateView() {
        initCircleAnimation();
        initToolbar();
    }

    @Override
    protected void initEventAndData() {
    }

    private void initToolbar() {
        mTitleTv.setText(R.string.useful_sites);
        mToolbar.setNavigationOnClickListener(v -> mCircularRevealAnim.hide(mTitleTv, mRootView));
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
    }

    @Override
    protected void initListener() {
        mCircularRevealAnim.setAnimListener(new CircularRevealAnim.AnimListener() {
            @Override
            public void onHideAnimationEnd() {
                dismissAllowingStateLoss();
            }

            @Override
            public void onShowAnimationEnd() {

            }
        });
        mTitleTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTitleTv.getViewTreeObserver().removeOnPreDrawListener(this);
                mCircularRevealAnim.show(mTitleTv, mRootView);
                return true;
            }
        });
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            KLog.i("dialog is onKey = " + keyCode);
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mCircularRevealAnim.hide(mTitleTv, mRootView);
            }
            return true;
        });
    }

    @Override
    public void showUsefulSites(ViewTextBean items) {
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<ViewTextBean.ViewTextItem>(items.getItems()) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public View getView(FlowLayout parent, int position, ViewTextBean.ViewTextItem item) {
                TextView tv = (TextView) LayoutInflater.from(_mActivity).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert item != null;
                String name = item.getText();
                tv.setText(name);
                tv.setBackgroundColor(CommonUtils.randomTagColor());
                tv.setTextColor(ContextCompat.getColor(WanAndroidApp.getInstance(), R.color.white));
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mPresenter.doFlowLayoutClick(view, position1, parent, items.getItems().get(position1));
                    return true;
                });
                return tv;
            }
        });
    }
}
