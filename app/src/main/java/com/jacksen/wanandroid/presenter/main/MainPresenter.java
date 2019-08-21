package com.jacksen.wanandroid.presenter.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.model.http.cookies.CookiesManager;
import com.jacksen.wanandroid.view.ui.knowledge.fragment.KnowledgeFragment;
import com.jacksen.wanandroid.view.ui.main.activity.AboutUsActivity;
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SearchActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SettingActivity;
import com.jacksen.wanandroid.view.ui.main.fragment.CollectFragment;
import com.jacksen.wanandroid.view.ui.main.fragment.UsefulSitesDialogFragment;
import com.jacksen.wanandroid.view.ui.mainpager.fragment.HomePageFragment;
import com.jacksen.wanandroid.view.ui.navi.fragment.NavigationFragment;
import com.jacksen.wanandroid.view.ui.project.fragment.ProjectFragment;
import com.jacksen.wanandroid.view.ui.todo.activity.TodoActivity;
import com.jacksen.wanandroid.view.ui.wx.fragment.WxFragment;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.SWITCH_PROJECT_PAGE)
                .observe(this, o -> getView().selectProjectTab());
        LiveDataBus.get()
                .with(BusConstant.SWITCH_NAVIGATION_PAGE)
                .observe(this, o -> getView().selectNavigationTab());
        LiveDataBus.get()
                .with(BusConstant.LOGIN_STATE, Boolean.class)
                .observe(this, aBoolean -> {
                    if (aBoolean) {
                        getView().showLoginOutView();
                    } else {
                        getView().showLoginView();
                    }
                });
    }

    @Override
    public void doLogOutClick() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.log_out))
                .setPositiveButton(getActivity().getString(R.string.log_out_sure), (dialog, which) -> logout())
                .setNegativeButton(getActivity().getString(R.string.log_out_not_sure), (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void doTurnLoginClick() {
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void doTurnAboutClick() {
        getActivity().startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    @Override
    public void doTodoClick() {
        getActivity().startActivity(new Intent(getActivity(), TodoActivity.class));
    }

    @Override
    public void doSettingClick() {
        getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    private UsefulSitesDialogFragment usefulSitesDialogFragment;

    @Override
    public void doSearchClick() {
        getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void doUsefulSitesClick() {
        if (usefulSitesDialogFragment == null) {
            usefulSitesDialogFragment = new UsefulSitesDialogFragment();
        }
        if (!getActivity().isDestroyed() && usefulSitesDialogFragment.isAdded()) {
            usefulSitesDialogFragment.dismiss();
        }
        usefulSitesDialogFragment.show(getActivity().getSupportFragmentManager(), "UsefulSitesDialogFragment");
    }

    @Override
    public void jumpToTheTop(Fragment fragment) {
        switch (getCurrentPage()) {
            case Constants.TYPE_MAIN_PAGER:
                if (fragment instanceof HomePageFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_HOME_PAGE).postValue(0);
                }
                break;
            case Constants.TYPE_KNOWLEDGE_PAGER:
                if (fragment instanceof KnowledgeFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_KNOWLEDGE_PAGE).postValue(0);
                }
                break;
            case Constants.TYPE_WX_ARTICLE_PAGER:
                if (fragment instanceof WxFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_WX_PAGE).postValue(0);
                }
            case Constants.TYPE_NAVIGATION_PAGER:
                if (fragment instanceof NavigationFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_NAVI_PAGE).postValue(0);
                }
                break;
            case Constants.TYPE_PROJECT_PAGER:
                if (fragment instanceof ProjectFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_PROJECT_PAGE).postValue(0);
                }
                break;
            case Constants.TYPE_COLLECT_PAGER:
                if (fragment instanceof CollectFragment) {
                    LiveDataBus.get().with(BusConstant.SCROLL_TO_COLLECT_PAGE).postValue(0);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 登出
     */
    private void logout() {
        addSubscribe(dataManager.logout()
                .compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleLogoutResult())
                .subscribeWith(new BaseObserver<LoginBean>(getView()) {
                    @Override
                    public void onNext(LoginBean loginData) {
                        setLoginState(false);
                        CookiesManager.clearAllCookies();
                        getView().showLoginView();
                        LiveDataBus.get().with(BusConstant.LOGIN_STATE).postValue(false);
                    }
                }));
    }

}
