package com.jacksen.wanandroid.presenter.todo.fragment.completed;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
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
public class TodoCompletedPresenter extends BasePresenter<TodoCompletedContract.View> implements TodoCompletedContract.Presenter {

    private int pageNo = 1;
    private boolean isFilter;

    private Map<String, String> params = new HashMap<>();

    @Inject
    public TodoCompletedPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.TODO_STATUS_DONE, Object.class)
                .observe(this, o -> {
                    isFilter = true;
                    getCompleteTodoData(1);
                });
    }

    @Override
    public void getCompleteTodoData(int pageNo) {
        params.put("status", "1");
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
                        ViewTodoData viewTodoCompletedData = new ViewTodoData(items);
                        getView().showTodoCompletedData(viewTodoCompletedData, isFilter);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void doUndoneTodoClick(BaseQuickAdapter adapter, View view, int position) {
        MaterialDialogUtils.showBasicDialog(getFragment().getActivity(), getFragment().getString(R.string.todo_update_status_undone))
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) ->
                        addSubscribe(dataManager.updateOnlyStatusTodo(((ViewTodoData.ViewTodoItem) adapter.getData().get(position)).getId(), "0")
                                .compose(RxUtils.rxSchedulerHelper())
                                .compose(RxUtils.handleResult())
                                .subscribeWith(new BaseObserver<TodoBean>(getView()) {
                                    @Override
                                    public void onNext(TodoBean responseBody) {
                                        getCompleteTodoData(1);
                                        isFilter = true;
                                        LiveDataBus.get().with(BusConstant.TODO_CREATED).postValue(null);
                                    }
                                })))
                .show();
    }

    @Override
    public void onRefresh() {
        isFilter = false;
        pageNo = 1;
        getCompleteTodoData(pageNo);
    }

    @Override
    public void onLoadMore() {
        isFilter = false;
        pageNo++;
        getCompleteTodoData(pageNo);
    }

    @Override
    public void filterData(List<FilterResult> results) {
        isFilter = true;
        if (results != null) {
            params.clear();
            for (FilterResult result : results) {
                if (!TextUtils.isEmpty(result.getKey())) {
                    if (!getFragment().getString(R.string.filter_all).equals(result.getTitle())) {
                        params.put(result.getKey(), result.getValue());
                    }
                }
                KLog.i(result.toString());
            }
        }
        getCompleteTodoData(1);
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
