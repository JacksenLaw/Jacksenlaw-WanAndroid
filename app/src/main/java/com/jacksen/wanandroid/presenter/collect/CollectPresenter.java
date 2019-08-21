package com.jacksen.wanandroid.presenter.collect;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
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
import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";

    private int pageNo = 0;
    private int currentPosition;
    private List<ViewFeedArticleListData.ViewFeedArticleItem> mCollect = new ArrayList<>();

    @Inject
    public CollectPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.NIGHT_MODEL, Boolean.class)
                .observe(this, aBoolean -> getView().setNightModel());
        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, collect -> {
                    if (BusConstant.COLLECT_PAGE.equals(collect.getType())) {
                        getView().showCancelCollect(currentPosition);
                    }
                });
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_COLLECT_PAGE, Integer.class)
                .observe(this, integer -> getView().scrollToTop(integer));
    }

    @Override
    public void getCollectList(int pageNo) {
        addSubscribe(dataManager.getCollectList(pageNo)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_collection_data),
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
                            mCollect.add(item);
                        }
                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                        getView().showCollectList(viewFeedArticleListData);
                        getView().showNormal();
                    }

                }));
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getCollectList(pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getCollectList(pageNo);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doItemClickListener(BaseQuickAdapter adapter, View view, int position) {
        currentPosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getFragment().getActivity(), view, getFragment().getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(),
                options,
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_ID),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getTitle(),
                ((ViewFeedArticleListData.ViewFeedArticleItem) adapter.getData().get(position)).getData(FEED_ARTICLE_LINK),
                true,
                true,
                false, BusConstant.COLLECT_PAGE);
    }

    @Override
    public void doItemChildClickListener(BaseQuickAdapter adapter, View view, int position) {
        //收藏
        if (view.getId() == R.id.item_article_pager_like_iv) {
            cancelCollectArticle(position, mCollect.get(position));
        }

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
                        getView().showCancelCollect(position);
                        getView().showToast(getFragment().getString(R.string.cancel_collect));
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showToast(getFragment().getString(R.string.cancel_collect_fail));
                    }
                }));
    }
}
