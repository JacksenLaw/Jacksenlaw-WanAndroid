package com.jacksen.component_home.presenter;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.base.lib.Constants;
import com.base.lib.http.RxUtils;
import com.base.lib.http.base.BaseObserver;
import com.base.lib.model.BaseResponse;
import com.base.lib.presenter.BasePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.component_home.R;
import com.jacksen.component_home.bean.bean.banner.BannerBean;
import com.jacksen.component_home.bean.bean.collect.FeedArticleBean;
import com.jacksen.component_home.bean.bean.collect.FeedArticleListBean;
import com.jacksen.component_home.bean.main.ViewBannerData;
import com.jacksen.component_home.bean.main.ViewFeedArticleListData;

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

    @Inject
    public HomePagerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(HomePagerContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    @Override
    public void setNightModeState(boolean nightState) {

    }

    @Override
    public boolean getNightModeState() {
        return false;
    }

    @Override
    public void setAutoCacheState(boolean autoCacheState) {

    }

    @Override
    public boolean getAutoCacheState() {
        return false;
    }

    @Override
    public void setLoginState(boolean loginStatus) {

    }

    @Override
    public boolean getLoginState() {
        return false;
    }

    @Override
    public void setNoImageState(boolean noImageState) {

    }

    @Override
    public boolean getNoImageState() {
        return false;
    }

    @Override
    public void setLoginAccount(String account) {

    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public void setCurrentPage(int page) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    private void registerEvent() {
//        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
//                .subscribe(collectEvent -> {
//                    //通知收藏图标改变颜色
//                    if (CollectEvent.HOME_PAGE.equals(collectEvent.getType()) && clickPosition >= 0) {
//                        getView().onEventCollect(clickPosition, collectEvent.isCancelCollect());
//                    }
//                }));
//        addSubscribe(RxBus.getDefault().toFlowable(ScrollToTopEvent.class)
//                .subscribe(scrollToTopEvent -> {
//                    if (CollectEvent.HOME_PAGE.equals(scrollToTopEvent.getPageType())) {
//                        getView().scrollToTheTop(0);
//                    }
//                }));
    }

    @Override
    public void doCollectClick(BaseQuickAdapter adapter, int position) {
        if (!getLoginState()) {
//            getFragment().startActivity(new Intent(getFragment().getContext(), LoginActivity.class));
            getView().showToast(getFragment().getString(R.string.login_tint));
            return;
        }
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
//        JudgeUtils.startKnowledgeHierarchyDetailActivity(getFragment().getActivity(),
//                true,
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getSuperChapterName(),
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getChapterName(),
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_CHAPTER_ID));
    }

    @Override
    public void clickTag(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        String superChapterName = ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getSuperChapterName();
        if (superChapterName.contains(getFragment().getString(R.string.open_project))) {
//            RxBus.getDefault().post(new SwitchProjectEvent());
        } else if (superChapterName.contains(getFragment().getString(R.string.navigation))) {
//            RxBus.getDefault().post(new SwitchNavigationEvent());
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
//        Observable<BaseResponse<LoginBean>> mLoginObservable = dataManager.getLoginData(dataManager.getLoginAccount(), dataManager.getLoginPassword());
        Observable<BaseResponse<List<BannerBean>>> mBannerObservable = dataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListBean>> mArticleObservable = dataManager.getFeedArticleList(pageNo);

        addSubscribe(Observable.zip(mBannerObservable, mArticleObservable, this::createResponseMap)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String, Object>>(getView()) {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
//                        BaseResponse<LoginBean> loginResponse = cast(map.get(Constants.LOGIN_DATA));
//                        if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
//                            getView().onLoginSuccessful(loginResponse);
//                        } else {
//                            getView().showAutoLoginFail();
//                        }
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

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
//                        getView().showAutoLoginFail();
                        getView().showError();
                    }
                }));
    }

    @Override
    public void getFeedArticleList(int pageNo) {
//        addSubscribe(dataManager.getFeedArticleList(pageNo)
//                .compose(RxUtils.rxSchedulerHelper())
//                .compose(RxUtils.handleResult())
//                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(), BaseApplication.getInstance().getString(R.string.failed_to_obtain_banner_data),
//                        true) {
//                    @Override
//                    public void onNext(FeedArticleListBean feedArticleListData) {
//                        ArrayList<ViewFeedArticleListData.ViewFeedArticleItem> listItems = new ArrayList<>();
//                        for (FeedArticleBean feedArticleBean : feedArticleListData.getDatas()) {
//                            ViewFeedArticleListData.ViewFeedArticleItem item = new ViewFeedArticleListData.ViewFeedArticleItem(
//                                    feedArticleBean.getAuthor(), feedArticleBean.getSuperChapterName(), feedArticleBean.getChapterName(),
//                                    feedArticleBean.getNiceDate(), feedArticleBean.getTitle(), feedArticleBean.isCollect()
//                            );
//                            item.putData(FEED_ARTICLE_ID, feedArticleBean.getId());
//                            item.putData(FEED_CHAPTER_ID, feedArticleBean.getChapterId());
//                            item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
//                            listItems.add(item);
//                        }
//                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
//                        getView().showFeedArticleData(viewFeedArticleListData);
//                    }
//                }));
    }

    /**
     * 收藏文章
     *
     * @param position        Position
     * @param feedArticleData FeedArticleBean
     */
    private void addCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
//        addSubscribe(dataManager.addCollectArticle(feedArticleData.getData(FEED_ARTICLE_ID))
//                .compose(RxUtils.rxSchedulerHelper())
//                .compose(RxUtils.handleCollectResult())
//                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
//                    @Override
//                    public void onNext(FeedArticleListBean feedArticleListData) {
//                        feedArticleData.setCollect(true);
//                        getView().onCollectArticleData(position, feedArticleData);
//                        getView().showSnackBar(getFragment().getString(R.string.collect_success));
//                    }
//                }));
    }

    /**
     * 取消收藏文章
     *
     * @param position        Position
     * @param feedArticleData FeedArticleBean
     */
    private void cancelCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
//        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getData(FEED_ARTICLE_ID))
//                .compose(RxUtils.rxSchedulerHelper())
//                .compose(RxUtils.handleCollectResult())
//                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
//                    @Override
//                    public void onNext(FeedArticleListBean feedArticleListData) {
//                        feedArticleData.setCollect(false);
//                        getView().onCollectArticleData(position, feedArticleData);
//                        getView().showSnackBar(getFragment().getString(R.string.cancel_collect));
//                    }
//                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doBannerClick(ViewBannerData.ViewBannerItem item) {
        String clickUrl = item.getData(BANNER_CLICK_URL);
//        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(), null,
//                0, item.getDesc(), clickUrl,
//                false, false, true, CollectEvent.HOME_PAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doItemClickListener(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getFragment().getActivity(), view, getFragment().getString(R.string.share_view));
//        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(),
//                options,
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_ID),
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getTitle(),
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_LINK),
//                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).isCollect(),
//                false,
//                false, CollectEvent.HOME_PAGE);
    }

    @NonNull
    private HashMap<String, Object> createResponseMap(
            BaseResponse<List<BannerBean>> bannerResponse,
                                                      BaseResponse<FeedArticleListBean> feedArticleListResponse) {
        HashMap<String, Object> map = new HashMap<>(2);
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
