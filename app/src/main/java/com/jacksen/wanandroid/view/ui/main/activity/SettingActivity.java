package com.jacksen.wanandroid.view.ui.main.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.setting.SettingContract;
import com.jacksen.wanandroid.presenter.setting.SettingPresenter;
import com.jacksen.wanandroid.util.ACache;
import com.jacksen.wanandroid.util.ShareUtil;
import com.jacksen.wanandroid.util.StatusBarUtils;

import java.io.File;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox mCbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox mCbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox mCbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    TextView mLlSettingFeedback;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mLlSettingClear;
    @BindView(R.id.tv_setting_clear)
    TextView mTvSettingClear;
    @BindView(R.id.common_toolbar)
    Toolbar mToolBar;

    private File cacheFile;

    private void clearCache() {
        ACache.deleteDir(cacheFile);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        StatusBarUtils.with(this).setColor(ContextCompat.getColor(this, R.color.colorPrimary)).init();
        mToolBar.setTitle(getString(R.string.setting));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        cacheFile = new File(Constants.PATH_CACHE);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
        mCbSettingCache.setChecked(mPresenter.isNoCacheModel());
        mCbSettingImage.setChecked(mPresenter.isNoImageModel());
        mCbSettingNight.setChecked(mPresenter.isNightMode());
    }

    @Override
    protected void initListener() {
        mCbSettingCache.setOnCheckedChangeListener(this);
        mCbSettingImage.setOnCheckedChangeListener(this);
        mCbSettingNight.setOnCheckedChangeListener(this);
        mLlSettingFeedback.setOnClickListener(v ->
                ShareUtil.sendEmail(this, getString(R.string.send_email))
        );
        mLlSettingClear.setOnClickListener(v -> clearCache());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_setting_night:
                mPresenter.doNightClick(new SettingPresenter.Callback() {
                    @Override
                    public void authorizationDenied() {
                        buttonView.setChecked(false);
                    }
                }, isChecked);
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageModel(isChecked);
                break;
            case R.id.cb_setting_cache:
                mPresenter.setNoCacheModel(isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cacheFile = null;
    }
}
