package com.jacksen.wanandroid.presenter.todo.fragment.todo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.todo.TodoBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.KLog;
import com.jacksen.wanandroid.util.MaterialDialogUtils;
import com.jacksen.wanandroid.view.bean.todo.FilterResult;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;
import com.jacksen.wanandroid.view.ui.todo.activity.TodoCreateActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoFragPresenter extends BasePresenter<TodoFragContract.View> implements TodoFragContract.Presenter {

    private int pageNo = 1;
    private boolean isFilter;

    private Map<String, String> params = new HashMap<>();

    @Inject
    public TodoFragPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.TODO_CREATED, Object.class)
                .observe(this, o -> {
                    getTodoData(1);
                    isFilter = true;
                });
    }

    @Override
    public void getTodoData(int pageNo) {
        params.put("status", "0");
        addSubscribe(dataManager.getTodoData(pageNo, params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<TodoBean>(getView()) {
                    @Override
                    public void onNext(TodoBean todoBean) {
                        KLog.i(todoBean.toString());
                        ArrayList<ViewTodoData.ViewTodoItem> items = new ArrayList<>();
                        for (TodoBean.DatasBean data : todoBean.getDatas()) {
                            items.add(new ViewTodoData.ViewTodoItem(data.getId(), data.getTitle(), data.getContent(),
                                    data.getDateStr(), data.getCompleteDateStr(), data.getType(), data.getStatus(), data.getPriority()));
                        }
                        ViewTodoData viewTodoData = new ViewTodoData(items);
                        getView().showTodoData(viewTodoData, isFilter);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void onRefresh() {
        isFilter = false;
        pageNo = 1;
        getTodoData(pageNo);
    }

    @Override
    public void onLoadMore() {
        isFilter = false;
        pageNo++;
        getTodoData(pageNo);
    }

    @Override
    public void filterData(List<FilterResult> results) {
        isFilter = true;
        if (results != null) {
            params.clear();
            for (FilterResult result : results) {
                if (!TextUtils.isEmpty(result.getKey())) {
                    if (!getFragment().getString(R.string.filter_all).equals(result.getKey())) {
                        params.put(result.getKey(), result.getValue());
                    }
                }
                KLog.i(result.toString());
            }
        }
        getTodoData(1);
    }

    @Override
    public void doUpdateTodoClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getFragment().getActivity(), TodoCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, false);
        bundle.putSerializable(Constants.ARG_PARAM2, (ViewTodoData.ViewTodoItem) adapter.getData().get(position));
        intent.putExtras(bundle);
        getFragment().startActivity(intent);
    }

    @Override
    public void doDoneTodoClick(BaseQuickAdapter adapter, View view, int position) {
        MaterialDialogUtils.showBasicDialog(getFragment().getActivity(), getFragment().getString(R.string.todo_update_status_done))
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) ->
                        addSubscribe(dataManager.updateOnlyStatusTodo(((ViewTodoData.ViewTodoItem) adapter.getData().get(position)).getId(), "1")
                                .compose(RxUtils.rxSchedulerHelper())
                                .compose(RxUtils.handleResult())
                                .subscribeWith(new BaseObserver<TodoBean>(getView()) {
                                    @Override
                                    public void onNext(TodoBean responseBody) {
                                        getTodoData(pageNo);
                                        isFilter = true;
                                        LiveDataBus.get().with(BusConstant.TODO_STATUS_DONE).postValue(null);
                                    }
                                })))
                .show();

    }

    @Override
    public void doDeleteSwipe(int id) {
        addSubscribe(dataManager.deleteTodo(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<ResponseBody>(getView()) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            KLog.i(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }
}

