package com.jacksen.wanandroid.presenter.todo.fragment.todo;

import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;

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
}
