package com.jacksen.wanandroid.view.ui.main.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.register.RegisterContract;
import com.jacksen.wanandroid.presenter.register.RegisterPresenter;
import com.jacksen.wanandroid.util.StatusBarUtils;

import butterknife.BindView;

/**
 * @author Luo
 * @date 2018/11/13 0013
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.common_toolbar)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mToolbarTitle;
    @BindView(R.id.common_toolbar_left_tv)
    TextView mToolbarLeftTitle;
    @BindView(R.id.register_account_edit)
    EditText etAccount;
    @BindView(R.id.register_password_edit)
    EditText etPassword;
    @BindView(R.id.register_confirm_password_edit)
    EditText etRePassword;
    @BindView(R.id.register_btn)
    Button btnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initListener() {
        btnRegister.setOnClickListener(v -> {
            String account = etAccount.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String rePassword = etRePassword.getText().toString().trim();
            if (!TextUtils.isEmpty(account) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(rePassword)) {
                if (account.length() >= 6) {
                    mPresenter.toRegister(account, password, rePassword);
                    btnRegister.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initToolbar() {
        StatusBarUtils.with(this).setColor(ContextCompat.getColor(this, R.color.colorPrimary)).init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mToolbarLeftTitle.setText(getString(R.string.register));
        mToolbarLeftTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.GONE);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onRegisterSuccessful() {
        btnRegister.setEnabled(true);
    }
}
