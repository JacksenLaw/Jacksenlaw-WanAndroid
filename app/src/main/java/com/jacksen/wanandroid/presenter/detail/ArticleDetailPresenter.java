package com.jacksen.wanandroid.presenter.detail;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.core.manager.PermissionManager;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.event.Collect;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.ui.main.activity.LoginActivity;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private String turnType;

    @Override
    public void attachView(ArticleDetailContract.View view) {
        super.attachView(view);
        turnType = getActivity().getIntent().getStringExtra(Constants.TURN_TYPE);
    }

    @Override
    public void doCollectEvent(CharSequence title, boolean isCollectPage, int articleId) {
        if (!getLoginState()) {
            getView().showToast(getActivity().getString(R.string.login_tint));
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            if (title.equals(getActivity().getString(R.string.collect))) {
                addCollectArticle(articleId);
            } else {
                if (isCollectPage) {
                    cancelCollectPageArticle(articleId);
                } else {
                    cancelCollectArticle(articleId);
                }
            }
        }
    }

    @Override
    public void doShare(String title, String articleLink) {
        PermissionManager.getInstance().get(getActivity())
                .requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new String[]{"允许程序使用存储权限"})
                .requestCodes(1001)
                .request(new PermissionManager.RequestPermissionCallBack() {
                    @Override
                    public void noM() {
                        shareEvent(title, articleLink);
                    }

                    @Override
                    public void granted() {
                        shareEvent(title, articleLink);
                    }

                    @Override
                    public void denied() {
                        shareError();
                    }
                });
    }

    @Override
    public void doOpenWithSystemBrowser(String uri) {
        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    private void shareEvent(String title, String articleLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getActivity().getString(R.string.share_type_url, getActivity().getString(R.string.app_name), title, articleLink));
        intent.setType("text/plain");
        getActivity().startActivity(intent);
    }

    private void shareError() {
        getView().showToast(getActivity().getString(R.string.write_permission_not_allowed));
    }

    /**
     * 收藏文章
     *
     * @param articleId articleId
     */
    private void addCollectArticle(int articleId) {
        addSubscribe(dataManager.addCollectArticle(articleId)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        getView().showCollectArticleData(true);
                        LiveDataBus.get().with(BusConstant.COLLECT).postValue(new Collect(turnType,true));
//                        RxBus.getDefault().post(new Collect(turnType, true));
                    }
                }));
    }

    /**
     * 取消站内文章
     *
     * @param articleId 文章id
     */
    private void cancelCollectArticle(int articleId) {
        addSubscribe(dataManager.cancelCollectArticle(articleId)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView(),
                        WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        getView().showCollectArticleData(false);
                        LiveDataBus.get().with(BusConstant.COLLECT).postValue(new Collect(turnType,false));
//                        RxBus.getDefault().post(new Collect(turnType, false));
                    }
                }));
    }

    /**
     * 取消 收藏页面的站内文章
     *
     * @param articleId 文章id
     */
    private void cancelCollectPageArticle(int articleId) {
        addSubscribe(dataManager.cancelCollectPageArticle(articleId)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListBean>(getView()) {
                    @Override
                    public void onNext(FeedArticleListBean feedArticleListData) {
                        getView().showCollectArticleData(false);
                        LiveDataBus.get().with(BusConstant.COLLECT).postValue(new Collect(turnType,false));
//                        RxBus.getDefault().post(new Collect(turnType, false));
                    }
                }));
    }

}
