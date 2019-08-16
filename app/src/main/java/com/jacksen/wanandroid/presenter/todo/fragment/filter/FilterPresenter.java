package com.jacksen.wanandroid.presenter.todo.fragment.filter;

import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;

import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class FilterPresenter extends BasePresenter<FilterContract.View> implements FilterContract.Presenter {

    @Inject
    public FilterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getFilterData() {
        addSubscribe(dataManager.getFilterData()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<List<FilterBean>>(getView()) {
                    @Override
                    public void onNext(List<FilterBean> filterBeans) {
                        getView().showFilterData(filterBeans);
                    }
                }));
    }

}


