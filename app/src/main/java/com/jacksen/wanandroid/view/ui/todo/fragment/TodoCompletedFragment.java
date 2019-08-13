package com.jacksen.wanandroid.view.ui.todo.fragment;

import android.os.Bundle;
import android.view.View;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.fragment.BaseFragment;
import com.jacksen.wanandroid.base.fragment.BaseRootFragment;
import com.jacksen.wanandroid.presenter.todo.fragment.completed.TodoCompletedContract;
import com.jacksen.wanandroid.presenter.todo.fragment.completed.TodoCompletedPresenter;

/**
 * 作者： LuoM
 * 创建时间：2019/8/12/0012
 * 描述： 已完成事项页
 * 版本号： v1.0.0
 * 更新时间：2019/8/12/0012
 * 更新内容：
 */
public class TodoCompletedFragment extends BaseFragment<TodoCompletedPresenter> implements TodoCompletedContract.View {

    public static TodoCompletedFragment getInstance(boolean param1, String param2) {
        TodoCompletedFragment fragment = new TodoCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_todo_completed;
    }

    @Override
    protected void initOnCreateView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showNormal();
    }

    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();
        return true;
    }

    @Override
    public View getRootView() {
        return null;
    }
}
