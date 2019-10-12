package com.jacksen.wanandroid.presenter.search;

import android.text.TextUtils;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 作者： LuoM
 * 时间： 2019/3/31 0031
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private String TOP_SEARCH_LINK = "TOP_SEARCH_LINK";

    @Inject
    SearchPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.FINISH_SEARCH_ACTIVITY)
                .observe(this, o -> getActivity().finish());
    }

    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);
        getTopSearchData();
    }

    @Override
    public void loadAllHistoryData() {
        getView().showHistoryData(dataManager.loadAllHistoryData());
    }

    @Override
    public void doSearchClick(String searchData) {
        if (TextUtils.isEmpty(searchData)) return;
        addHistoryData(searchData);
    }

    @Override
    public void addHistoryData(String searchText) {
        addSubscribe(Observable
                .create((ObservableOnSubscribe<List<HistorySearchData>>) emitter -> {
                    List<HistorySearchData> list = dataManager.addHistoryData(searchText);
                    emitter.onNext(list);
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(historySearchData -> {
                            getView().judgeToTheSearchListActivity();
                            getView().showHistoryData(historySearchData);
                        }
                )
        );
    }

    @Override
    public void doClearHistoryClick() {
        addSubscribe(Observable
                .create((ObservableOnSubscribe<List<HistorySearchData>>) emitter -> {
                    dataManager.clearHistoryData();
                    emitter.onNext(dataManager.loadAllHistoryData());
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(historySearchData ->
                        getView().showHistoryData(historySearchData)
                )
        );
    }

    @Override
    public void getTopSearchData() {
        addSubscribe(dataManager.getTopSearchData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<TopSearchBean>>(getView(),
                        getActivity().getString(R.string.failed_to_obtain_top_data)) {
                    @Override
                    public void onNext(List<TopSearchBean> topSearchDataList) {
                        ArrayList<ViewTextBean.ViewTextItem> items = new ArrayList<>();
                        for (TopSearchBean bean : topSearchDataList) {
                            ViewTextBean.ViewTextItem item = new ViewTextBean.ViewTextItem(bean.getName());
                            item.putData(TOP_SEARCH_LINK, bean.getLink());
                            items.add(item);
                        }
                        ViewTextBean viewTextBean = new ViewTextBean(items);
                        getView().showTopSearchData(viewTextBean);
                    }
                }));
    }

}
