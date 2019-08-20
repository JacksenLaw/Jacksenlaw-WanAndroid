package com.jacksen.wanandroid.view.ui.todo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.presenter.todo.activity.create.TodoCreateContract;
import com.jacksen.wanandroid.presenter.todo.activity.create.TodoCreatePresenter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class TodoCreateActivity extends BaseActivity<TodoCreatePresenter> implements TodoCreateContract.View {

    @BindView(R.id.include)
    Toolbar mToolbar;
    @BindView(R.id.left_toolbar_tv)
    TextView mTitleTv;

    @BindView(R.id.et_todo_title)
    EditText etTitle;
    @BindView(R.id.et_todo_content)
    EditText etContent;
    @BindView(R.id.tv_todo_create_date)
    TextView tvDate;
    @BindView(R.id.tv_todo_create_type)
    TextView tvType;
    @BindView(R.id.tv_todo_create_priority)
    TextView tvPriority;
    @BindView(R.id.tv_todo_create_status)
    TextView tvStatus;
    @BindView(R.id.btn_todo_create_create)
    Button btnCreate;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo_create;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleTv.setText(getString(R.string.todo_create_toolbar_title));
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        tvDate.setText(SimpleDateFormat.getDateInstance(3, Locale.CHINA).format(System.currentTimeMillis()).replace("/", "-"));
    }

    @Override
    protected void initListener() {
        tvDate.setOnClickListener(v -> mPresenter.doSwitchDateClick(tvDate.getText().toString()));
        tvType.setOnClickListener(v -> mPresenter.doSwitchTypeClick());
        tvPriority.setOnClickListener(v -> mPresenter.doSwitchPriorityClick());
        tvStatus.setOnClickListener(v -> mPresenter.doSwitchStatusClick());
        btnCreate.setOnClickListener(v -> mPresenter.doCreateClick(etTitle.getText().toString(), etContent.getText().toString()));
    }

    @Override
    public void setTitleText(String text) {
        etTitle.setText(text);
    }

    @Override
    public void setContentText(String text) {
        etContent.setText(text);
    }

    @Override
    public void setDateText(String text) {
        tvDate.setText(text);
    }

    @Override
    public void setTypeTextColor(String text, int color) {
        tvType.setText(text);
        tvType.setTextColor(color);
    }

    @Override
    public void setPriorityTextColor(String text, int color) {
        tvPriority.setText(text);
        tvPriority.setTextColor(color);
    }

    @Override
    public void setStatusTextColor(String text, int color) {
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(text);
        tvStatus.setTextColor(color);
    }

    @Override
    public void setToolbarTitleText(String text) {
        mTitleTv.setText(text);
    }

    @Override
    public void setBtnText(String text) {
        btnCreate.setText(text);
    }
}
