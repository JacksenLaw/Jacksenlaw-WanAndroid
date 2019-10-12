package com.jacksen.wanandroid.presenter.search;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.aspectj.annotation.Login;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.event.Collect;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.view.bean.main.ViewFeedArticleListData;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/4/16 0016
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {

    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";

    private String searchText;
    private int pageNo = 0;
    private int clickPosition = -1;

    public int getClickPosition() {
        return clickPosition;
    }

    public String getSearchText() {
        return searchText;
    }

    @Inject
    public SearchListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, collect -> {
                    //通知收藏图标改变颜色
                    if (BusConstant.SEARCH_LIST_ACTIVITY.equals(collect.getType()) && getClickPosition() >= 0) {
                        getView().onEventCollect(getClickPosition(), collect.isCollected());
                    }
                });
    }

    @Override
    public void attachView(SearchListContract.View view) {
        super.attachView(view);
        searchText = getActivity().getIntent().getStringExtra(Constants.SEARCH_TEXT);
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getSearchListData(pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getSearchListData(pageNo);
    }

    private void getSearchListData(int pageNo) {
        addSubscribe(dataManager.getSearchList(pageNo, searchText)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        getActivity().getString(R.string.failed_to_obtain_search_data_list),
                        true) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListBean) {
                        ArrayList<ViewFeedArticleListData.ViewFeedArticleItem> listItems = new ArrayList<>();
                        for (FeedArticleBean feedArticleBean : feedArticleListBean.getDatas()) {
                            ViewFeedArticleListData.ViewFeedArticleItem item = new ViewFeedArticleListData.ViewFeedArticleItem(
                                    feedArticleBean.getAuthor(), feedArticleBean.getSuperChapterName(), feedArticleBean.getChapterName(),
                                    feedArticleBean.getNiceDate(), feedArticleBean.getTitle(), feedArticleBean.isCollect()
                            );
                            item.putData(FEED_ARTICLE_ID, feedArticleBean.getId());
                            item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
                            listItems.add(item);
                        }
                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                        getView().showSearchList(viewFeedArticleListData);
                        getView().showNormal();
                    }
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doItemClickListener(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, getActivity().getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(getActivity(),
                options,
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_ID),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getTitle(),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_LINK),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).isCollect(),
                false,
                false, BusConstant.SEARCH_LIST_ACTIVITY);
    }

    @Login
    @Override
    public void doCollectClick(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        if (((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).isCollect()) {
            cancelCollectArticle(position, (ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position));
        } else {
            addCollectArticle(position, (ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position));
        }
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
                        getView().showSnackBar(getView().getRootView(), getActivity().getString(R.string.collect_success));
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
                        getView().showSnackBar(getView().getRootView(), getActivity().getString(R.string.cancel_collect));
                    }
                }));
    }

    @Override
    public void doTurnChapterKnowledgePager(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        getView().showToast("跳转页面");
    }

    @Override
    public void clickTag(BaseQuickAdapter adapter, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() <= position) {
            return;
        }
        getActivity().finish();
        LiveDataBus.get()
                .with(BusConstant.FINISH_SEARCH_ACTIVITY).postValue(null);
        String superChapterName = ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getSuperChapterName();
        if (superChapterName.contains(getActivity().getString(R.string.open_project))) {
            LiveDataBus.get()
                    .with(BusConstant.SWITCH_PROJECT_PAGE).postValue(null);
        } else if (superChapterName.contains(getActivity().getString(R.string.navigation))) {
            LiveDataBus.get()
                    .with(BusConstant.SWITCH_NAVIGATION_PAGE).postValue(null);
        }
    }
}
