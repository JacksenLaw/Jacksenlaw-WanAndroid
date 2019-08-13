package com.jacksen.wanandroid.view.ui.main.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.login.LoginContract;
import com.jacksen.wanandroid.presenter.login.LoginPresenter;
import com.jacksen.wanandroid.util.StatusBarUtils;

import butterknife.BindView;

/**
 * @author Luo
 * @date 2018/11/11 0011
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mToolbarTitle;
    @BindView(R.id.common_toolbar_left_tv)
    TextView mToolbarLeftTitle;
    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtils.with(this).setColor(ContextCompat.getColor(this, R.color.colorPrimary)).init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mToolbarLeftTitle.setText(getString(R.string.login_in));
        mToolbarLeftTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.GONE);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        etAccount.setText(mPresenter.getLoginAccount());
        etAccount.setSelection(mPresenter.getLoginAccount().length());
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initListener() {
        btnRegister.setOnClickListener(v -> mPresenter.doRegisterClick());
        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (!TextUtils.isEmpty(account) || !TextUtils.isEmpty(password)) {
                mPresenter.doLoginClick(etAccount.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    @Override
    public void onLoginSuccessful() {
        onBackPressedSupport();
    }

}
