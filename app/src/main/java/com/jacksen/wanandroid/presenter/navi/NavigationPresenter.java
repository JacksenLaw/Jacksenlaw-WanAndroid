package com.jacksen.wanandroid.presenter.navi;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.navi.NavigationListBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.event.Collect;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.bean.navi.ElemeGroupedItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    @Inject
    public NavigationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_NAVI_PAGE, Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        getView().scrollToTheTop(0);
                    }
                });

        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, new Observer<Collect>() {
                    @Override
                    public void onChanged(@Nullable Collect collect) {
                        if (BusConstant.NAVI_PAGE.equals(collect.getType())) {
                            getView().showCollect(collect.isCollected());
                        }
                    }
                });
    }

    @Override
    public void getNaviData() {
        addSubscribe(dataManager.getNavigationListData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<NavigationListBean>>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_navigation_list),
                        true) {
                    @Override
                    public void onNext(List<NavigationListBean> naviBeans) {
                        List<ElemeGroupedItem> items = new ArrayList<>();
                        for (NavigationListBean naviBean : naviBeans) {
                            ElemeGroupedItem elemeGroupedItem = new ElemeGroupedItem(true, naviBean.getName());
                            items.add(elemeGroupedItem);
                            for (NavigationListBean.NavigationArticleBean article : naviBean.getArticles()) {
                                ElemeGroupedItem elemeGroupedItem1 = new ElemeGroupedItem(new ElemeGroupedItem.ItemInfo(article.getTitle(), naviBean.getName(),
                                        article.getId(), article.isCollect(), null, article.getLink(), null));
                                items.add(elemeGroupedItem1);
                            }
                        }

                        getView().showNavData(items);
                        getView().showNormal();
                    }
                }));
    }

}
