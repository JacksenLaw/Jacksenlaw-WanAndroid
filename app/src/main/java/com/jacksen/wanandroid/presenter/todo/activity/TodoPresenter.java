package com.jacksen.wanandroid.presenter.todo.activity;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.util.MaterialDialogUtils;

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

    }

    @Override
    public void doFilterTodoClick() {
        MaterialDialogUtils.showCustomDialog(getActivity(), "筛选", R.layout.layout_todo_filter)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).show();
    }
}
