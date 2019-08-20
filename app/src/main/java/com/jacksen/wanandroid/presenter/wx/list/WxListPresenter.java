package com.jacksen.wanandroid.presenter.wx.list;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
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
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class WxListPresenter extends BasePresenter<WxListContract.View> implements WxListContract.Presenter {

    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_CHAPTER_ID = "FEED_CHAPTER_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";
    private int clickPosition = -1;
    private int pageNo = 0;

    private int authorId;
    private String authorName;

    public int getClickPosition() {
        return clickPosition;
    }

    public String getSearchHint() {
        return "搜索" + authorName + "公众号内更多干货";
    }

    @Inject
    public WxListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_WX_LIST_PAGE, Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        getView().scrollToTheTop(integer);
                    }
                });
        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, new Observer<Collect>() {
                    @Override
                    public void onChanged(@Nullable Collect collect) {
                        //通知收藏图标改变颜色
                        if (BusConstant.WX_PAGE.equals(collect.getType()) && getClickPosition() >= 0) {
                            getView().onEventCollect(getClickPosition(), collect.isCollected());
                        }
                    }
                });
    }

    @Override
    public void attachView(WxListContract.View view) {
        super.attachView(view);
        authorId = getFragment().getArguments().getInt(Constants.ARG_PARAM1, 0);
        authorName = getFragment().getArguments().getString(Constants.ARG_PARAM2, "");
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getWxList(authorId, pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getWxList(authorId, pageNo);
    }

    private void getWxList(int authorId, int pageNo) {
        addSubscribe(dataManager.getWxSumData(authorId, pageNo)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_wx_data),
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
                            item.putData(FEED_CHAPTER_ID, feedArticleBean.getChapterId());
                            item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
                            listItems.add(item);
                        }
                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                        getView().showWxList(viewFeedArticleListData);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void doSearchClick(String searchText) {
        addSubscribe(dataManager.getWxSearchSumData(authorId, pageNo, searchText)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_search_data_list)
                        , true) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListBean) {
                        ArrayList<ViewFeedArticleListData.ViewFeedArticleItem> listItems = new ArrayList<>();
                        for (FeedArticleBean feedArticleBean : feedArticleListBean.getDatas()) {
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
                        getView().showWxSearchView(viewFeedArticleListData);
                    }
                }));
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
        String superChapterName = ((FeedArticleBean) adapter.getData().get(position)).getSuperChapterName();
        if (superChapterName.contains(getFragment().getString(R.string.open_project))) {
            LiveDataBus.get()
                    .with(BusConstant.SWITCH_PROJECT_PAGE).postValue(null);
        } else if (superChapterName.contains(getFragment().getString(R.string.navigation))) {
            LiveDataBus.get()
                    .with(BusConstant.SWITCH_NAVIGATION_PAGE).postValue(null);
        }
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
                false, BusConstant.WX_PAGE);
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
}
