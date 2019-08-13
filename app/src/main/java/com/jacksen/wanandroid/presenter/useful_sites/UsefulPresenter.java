package com.jacksen.wanandroid.presenter.useful_sites;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.usefulsites.UsefulSiteBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/4/18 0018
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class UsefulPresenter extends BasePresenter<UsefulContract.View> implements UsefulContract.Presenter {

    private String USEFUL_SITE_ID = "USEFUL_SITE_ID";
    private String USEFUL_SITE_LINK = "USEFUL_SITE_LINK";

    @Inject
    public UsefulPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(UsefulContract.View view) {
        super.attachView(view);
        getUsefulSites();
    }

    private void getUsefulSites() {
        addSubscribe(dataManager.getUsefulSites()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<UsefulSiteBean>>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_useful_sites_data)) {
                    @Override
                    public void onNext(List<UsefulSiteBean> usefulSiteBeans) {
                        ArrayList<ViewTextBean.ViewTextItem> items = new ArrayList<>();
                        for (UsefulSiteBean bean : usefulSiteBeans) {
                            ViewTextBean.ViewTextItem item = new ViewTextBean.ViewTextItem(bean.getName());
                            item.putData(USEFUL_SITE_ID, bean.getId());
                            item.putData(USEFUL_SITE_LINK, bean.getLink());
                            items.add(item);
                        }
                        ViewTextBean viewTextBean = new ViewTextBean(items);
                        getView().showUsefulSites(viewTextBean);
                    }
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doFlowLayoutClick(View view, int position, FlowLayout parent, ViewTextBean.ViewTextItem item) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getFragment().getActivity(), view, getFragment().getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(),
                options,
                item.getData(USEFUL_SITE_ID),
                item.getText().trim(),
                item.getData(USEFUL_SITE_LINK),
                false,
                false,
                true, BusConstant.USEFUL_SITE);
    }
}
