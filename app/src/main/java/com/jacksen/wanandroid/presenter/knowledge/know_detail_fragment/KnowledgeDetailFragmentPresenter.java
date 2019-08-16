package com.jacksen.wanandroid.presenter.knowledge.know_detail_fragment;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeDetailFragmentPresenter extends BasePresenter<KnowledgeDetailFragmentContract.View> implements KnowledgeDetailFragmentContract.Presenter {

    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";
    private int clickPosition = -1;
    private int pageNo = 0;
    private int id = 0;

    public int getClickPosition() {
        return clickPosition;
    }

    @Inject
    public KnowledgeDetailFragmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.JUMP_TO_TOP_OF_KNOWLEDGE_LIST,Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        getView().showScrollTheTop(integer);
                    }
                });
        LiveDataBus.get()
                .with(BusConstant.COLLECT, Collect.class)
                .observe(this, new Observer<Collect>() {
                    @Override
                    public void onChanged(@Nullable Collect collect) {
                        //通知收藏图标改变颜色
                        if (BusConstant.KNOWLEDGE_PAGE.equals(collect.getType()) && getClickPosition() >= 0) {
                            getView().onEventCollect(getClickPosition(), collect.isCollected());
                        }
                    }
                });
    }

    @Override
    public void getKnowledgeHierarchyDetailData(int page, int cid) {
        addSubscribe(dataManager.getKnowledgeHierarchyDetailData(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_knowledge_data),
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
                            item.putData(FEED_ARTICLE_LINK, feedArticleBean.getLink());
                            listItems.add(item);
                        }
                        ViewFeedArticleListData viewFeedArticleListData = new ViewFeedArticleListData(listItems);
                        getView().showKnowledgeHierarchyDetailData(viewFeedArticleListData);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
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

    @Override
    public void cancelCollectArticle(int position, ViewFeedArticleListData.ViewFeedArticleItem feedArticleData) {
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

    @Override
    public void doCollectClick(BaseQuickAdapter adapter, int position) {
        if (!getLoginState()) {
            getFragment().startActivity(new Intent(getFragment().getContext(), LoginActivity.class));
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

    }

    @Override
    public void clickTag(BaseQuickAdapter adapter, int position) {

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
                false, BusConstant.KNOWLEDGE_PAGE);
    }


    @Override
    public void onRefresh() {
        id = getFragment().getArguments().getInt(Constants.ARG_PARAM1, 0);
        if (id == 0) {
            return;
        }
        pageNo = 0;
        getKnowledgeHierarchyDetailData(pageNo, id);

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getKnowledgeHierarchyDetailData(pageNo, id);
    }
}
