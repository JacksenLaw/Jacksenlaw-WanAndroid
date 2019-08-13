package com.jacksen.wanandroid.presenter.wx;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.wx.WxAuthorBean;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.bean.wx.ViewWxAuthorBean;

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
public class WxPresenter extends BasePresenter<WxContract.View> implements WxContract.Presenter {

    @Inject
    public WxPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getWxAuthorList() {
        addSubscribe(dataManager.getWxAuthorListData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<WxAuthorBean>>(getView(),
                        getFragment().getString(R.string.fail_to_obtain_wx_author),
                        true) {
                    @Override
                    public void onNext(List<WxAuthorBean> wxAuthorBeans) {
                        ArrayList<ViewWxAuthorBean.ViewWxAuthorItemBean> itemBeans = new ArrayList<>();
                        for (WxAuthorBean wxAuthorBean : wxAuthorBeans) {
                            ViewWxAuthorBean.ViewWxAuthorItemBean itemBean = new ViewWxAuthorBean.ViewWxAuthorItemBean(
                                    wxAuthorBean.getId(),wxAuthorBean.getName());
                            itemBeans.add(itemBean);
                        }
                        ViewWxAuthorBean viewWxAuthorBean = new ViewWxAuthorBean(itemBeans);
                        getView().showNormal();
                        getView().showWxAuthor(viewWxAuthorBean);
                    }
                }));
    }

}
