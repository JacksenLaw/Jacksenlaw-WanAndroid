package com.jacksen.wanandroid.presenter.todo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.view.ui.todo.activity.TodoCreateActivity;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoPresenter extends BasePresenter<TodoContract.View> implements TodoContract.Presenter {

    @Inject
    public TodoPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void doNewTodoClick() {
        Intent intent = new Intent(getActivity(), TodoCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, true);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

}
