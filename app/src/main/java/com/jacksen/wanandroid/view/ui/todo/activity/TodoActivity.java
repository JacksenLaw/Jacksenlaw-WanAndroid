package com.jacksen.wanandroid.view.ui.todo.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.activity.BaseActivity;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.presenter.todo.activity.TodoContract;
import com.jacksen.wanandroid.presenter.todo.activity.TodoPresenter;
import com.jacksen.wanandroid.view.ui.todo.fragment.TodoCompletedFragment;
import com.jacksen.wanandroid.view.ui.todo.fragment.TodoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述： 待办事项页
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoActivity extends BaseActivity<TodoPresenter> implements TodoContract.View {

    @BindView(R.id.fragment_contain)
    FrameLayout mFragmentContain;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.include)
    Toolbar mToolbar;
    @BindView(R.id.left_toolbar_tv)
    TextView mTitleTv;

    private int mLastFgIndex;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private TodoFragment todoFragment;
    private TodoCompletedFragment todoCompleteFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        if (savedInstanceState == null) {
            initPage(false, 0);
        } else {
            initPage(true, mPresenter.getCurrentPage());
        }

    }

    private void initPage(boolean isCreate, int position) {
        initFragment(isCreate);
        switchFragment(position);
    }

    private void initFragment(boolean isCreate) {
        todoFragment = TodoFragment.getInstance(isCreate, null);
        todoCompleteFragment = TodoCompletedFragment.getInstance(isCreate, null);
        mFragments.add(todoFragment);
        mFragments.add(todoCompleteFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_todo_add:
                mPresenter.doNewTodoClick();
                break;
            case R.id.action_todo_filter:
                mPresenter.doFilterTodoClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleTv.setText(getString(R.string.todo));
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

    }

    @Override
    protected void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_todo:
                    loadPage(getString(R.string.todo), 0);
                    return true;
                case R.id.tab_todo_ed:
                    loadPage(getString(R.string.todo_completed), 1);
                    return true;
            }

            return false;
        });
    }

    private void loadPage(String title, int currentPager) {
        mTitleTv.setText(title);
        switchFragment(currentPager);
    }

    private void switchFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        fragmentTransaction.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commitAllowingStateLoss();
            fragmentTransaction.add(R.id.fragment_contain, targetFg);
        }
        fragmentTransaction.show(targetFg);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
