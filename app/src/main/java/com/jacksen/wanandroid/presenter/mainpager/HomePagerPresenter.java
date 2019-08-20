package com.jacksen.wanandroid.presenter.mainpager;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.aspectj.annotation.Login;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.base.BaseResponse;
import com.jacksen.wanandroid.model.bean.main.banner.BannerBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.event.Collect;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.view.bean.main.ViewBannerData;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Luo
 * @date 2018/11/9 0009
 */
public class HomePagerPresenter extends BasePresenter<HomePagerContract.View> implements HomePagerContract.Presenter {

    private String BANNER_CLICK_URL = "BANNER_CLICK_URL";
    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_CHAPTER_ID = "FEED_CHAPTER_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";
    private int clickPosition = -1;

    private int pageNo;

    public int getClickPosition() {
        return clickPosition;
    }

    @Inject
    public HomePagerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.NIGHT_MODEL, Boolean.class)
                .observe(this, aBoolean -> {
                    getView().setNightModel();
                });
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_HOME_PAGE, Integer.class)
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
                        //通知收藏图标改变颜色
                        if (BusConstant.HOME_PAGE.equals(collect.getType()) && getClickPosition() >= 0) {
                            getView().onEventCollect(getClickPosition(), collect.isCollected());
                        }
                    }
                });
    }

    @Login
    @Override
    public void doCollectClick(BaseQuickAdapter adapter, int position) {
//        if (!isLogin()) {
//            getFragment().startActivity(new Intent(getFragment().getContext(), LoginActivity.class));
//            getView().showToast(getFragment().getString(R.string.login_tint));
//            return;
//        }
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        if (((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).isCollect()) {
            cancelCollectArticle(position, (ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position));
        } else {
            addCollectArticle(position, (ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position));
        }
    }

    @Override
    public void doTurnChapterKnowledgePager(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        JudgeUtils.startKnowledgeHierarchyDetailActivity(getFragment().getActivity(),
                true,
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getSuperChapterName(),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getChapterName(),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_CHAPTER_ID));
    }

    @Override
    public void clickTag(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        String superChapterName = ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getSuperChapterName();
        if (superChapterName.contains(getFragment().getString(R.string.open_project))) {
            LiveDataBus.get().with(BusConstant.SWITCH_PROJECT_PAGE).postValue(null);
        } else if (superChapterName.contains(getFragment().getString(R.string.navigation))) {
            LiveDataBus.get().with(BusConstant.SWITCH_NAVIGATION_PAGE).postValue(null);
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getAllData(pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getFeedArticleList(pageNo);
    }

    @Override
    public void getAllData(int pageNo) {
        Observable<BaseResponse<LoginBean>> mLoginObservable = dataManager.getLoginData(dataManager.getLoginAccount(), dataManager.getLoginPassword());
        Observable<BaseResponse<List<BannerBean>>> mBannerObservable = dataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListBean>> mArticleObservable = dataManager.getFeedArticleList(pageNo);

        addSubscribe(Observable.zip(mLoginObservable, mBannerObservable, mArticleObservable, this::createResponseMap)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String, Object>>(getView()) {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                        BaseResponse<List<BannerBean>> bannerResponse = cast(map.get(Constants.BANNER_DATA));
                        if (bannerResponse != null) {
                            ArrayList<ViewBannerData.ViewBannerItem> items = new ArrayList<>();
                            for (BannerBean bannerBean : bannerResponse.getData()) {
                                ViewBannerData.ViewBannerItem item = new ViewBannerData.ViewBannerItem(
                                        bannerBean.getTitle(), bannerBean.getImagePath());
                                item.putData(BANNER_CLICK_URL, bannerBean.getUrl());
                                items.add(item);
                            }
                            getView().showBannerData(items);

                        }
                        BaseResponse<FeedArticleListBean> feedArticleListResponse = cast(map.get(Constants.ARTICLE_DATA));
                        if (feedArticleListResponse != null) {
                            ArrayList<ViewFeedArticleListData.ViewFeedArticleItem> listItems = new ArrayList<>();
                            for (FeedArticleBean feedArticleBean : feedArticleListResponse.getData().getDatas()) {
                                ViewFeedArticleListData.ViewFeedArticleItem item = new ViewFeedArticleListData.ViewFeedArticleItem(
                                        feedArticleBean.getAuthor(), feedArticleBean.getSuperChapterName(), feedArticleBean.getChapterName(),
                                        feedArticleBean.getNiceDate(), feedArticleBean.getTitle(), feedArticleBean.isCollect()
                                );
                                item.putData(FEED_ARTICLE_ID, feedArticleBean.getId());
                                item.putData(FEED_CHAPTER_ID, feedArticleBean.getChapterId());
                                item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
                                listItems.add(item);
                            }
                            ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                            getView().showFeedArticleData(viewFeedArticleListData);
                        }
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void getFeedArticleList(int pageNo) {
        addSubscribe(dataManager.getFeedArticleList(pageNo)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(), WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_banner_data),
                        true) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        ArrayList<ViewFeedArticleListData.ViewFeedArticleItem> listItems = new ArrayList<>();
                        for (FeedArticleBean feedArticleBean : feedArticleListData.getDatas()) {
                            ViewFeedArticleListData.ViewFeedArticleItem item = new ViewFeedArticleListData.ViewFeedArticleItem(
                                    feedArticleBean.getAuthor(), feedArticleBean.getSuperChapterName(), feedArticleBean.getChapterName(),
                                    feedArticleBean.getNiceDate(), feedArticleBean.getTitle(), feedArticleBean.isCollect()
                            );
                            item.putData(FEED_ARTICLE_ID, feedArticleBean.getId());
                            item.putData(FEED_CHAPTER_ID, feedArticleBean.getChapterId());
                            item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
                            listItems.add(item);
                        }
                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                        getView().showFeedArticleData(viewFeedArticleListData);
                    }
                }));
    }

    /**
     * 收藏文章
     *
     * @param position        Position
     * @param feedArticleData FeedArticleBean
     */
    private void addCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
        addSubscribe(dataManager.addCollectArticle(feedArticleData.getData(FEED_ARTICLE_ID))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(true);
                        getView().onCollectArticleData(position, feedArticleData);
                        getView().showSnackBar(getView().getRootView(), getFragment().getString(R.string.collect_success));
                    }
                }));
    }

    /**
     * 取消收藏文章
     *
     * @param position        Position
     * @param feedArticleData FeedArticleBean
     */
    private void cancelCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getData(FEED_ARTICLE_ID))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(false);
                        getView().onCollectArticleData(position, feedArticleData);
                        getView().showSnackBar(getView().getRootView(), getFragment().getString(R.string.cancel_collect));
                    }
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doBannerClick(ViewBannerData.ViewBannerItem item) {
        String clickUrl = item.getData(BANNER_CLICK_URL);
        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(), null,
                0, item.getDesc(), clickUrl,
                false, false, true, BusConstant.HOME_PAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doItemClickListener(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getFragment().getActivity(), view, getFragment().getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(),
                options,
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_ID),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getTitle(),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_LINK),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).isCollect(),
                false,
                false, BusConstant.HOME_PAGE);
    }

    @NonNull
    private HashMap<String, Object> createResponseMap(BaseResponse<LoginBean> loginResponse,
                                                      BaseResponse<List<BannerBean>> bannerResponse,
                                                      BaseResponse<FeedArticleListBean> feedArticleListResponse) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put(Constants.LOGIN_DATA, loginResponse);
        map.put(Constants.BANNER_DATA, bannerResponse);
        map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
        return map;
    }

    /**
     * 泛型转换工具方法 eg:object ==> map<String, String>
     *
     * @param object Object
     * @param <T>    转换得到的泛型对象
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
