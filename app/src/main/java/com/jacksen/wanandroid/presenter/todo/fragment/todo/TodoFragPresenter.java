package com.jacksen.wanandroid.presenter.todo.fragment.todo;

import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.todo.TodoBean;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.KLog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class TodoFragPresenter extends BasePresenter<TodoFragContract.View> implements TodoFragContract.Presenter {

    private int pageNo = 1;

    private Map<String, String> params = new HashMap<>();

    @Inject
    public TodoFragPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getTodoData(int pageNo) {
        params.put("status","0");
        addSubscribe(dataManager.getTodoData(pageNo, params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<TodoBean>(getView()) {
                    @Override
                    public void onNext(TodoBean todoBean) {
                        KLog.i(todoBean.toString());
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        getTodoData(pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getTodoData(pageNo);
    }
}
