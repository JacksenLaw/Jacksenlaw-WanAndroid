package com.jacksen.wanandroid.presenter.project.list;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyListBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.view.bean.project.ViewProjectListData;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/4/13 0013
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private String FEED_ARTICLE_ID = "FEED_ARTICLE_ID";
    private String FEED_ARTICLE_LINK = "FEED_ARTICLE_LINK";
    private int clickPosition = -1;
    private int pageNo = 0;

    private int projectId;

    public int getClickPosition() {
        return clickPosition;
    }

    @Inject
    public ProjectListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(ProjectListContract.View view) {
        super.attachView(view);
        projectId = getFragment().getArguments().getInt(Constants.ARG_PARAM1, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void doItemClickListener(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getFragment().getActivity(), view, getFragment().getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(getFragment().getActivity(),
                options,
                ((ViewProjectListData.ViewProjectListItem) adapter.getData().get(position)).getData(FEED_ARTICLE_ID),
                ((ViewProjectListData.ViewProjectListItem) adapter.getData().get(position)).getTitle(),
                ((ViewProjectListData.ViewProjectListItem) adapter.getData().get(position)).getData(FEED_ARTICLE_LINK),
                ((ViewProjectListData.ViewProjectListItem) adapter.getData().get(position)).isCollect(),
                false,
                false, BusConstant.PROJECT_PAGE);
    }

    private void getProjectList(int projectId, int pageNo) {
        addSubscribe(dataManager.getProjectListData(pageNo, projectId)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ProjectClassifyListBean>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_project_list),
                        true) {
                    @Override
                    public void onNext(ProjectClassifyListBean projectClassifyListBean) {
                        ArrayList<ViewProjectListData.ViewProjectListItem> items = new ArrayList<>();
                        for (ProjectClassifyListBean.ProjectClassifyArticleBean bean : projectClassifyListBean.getDatas()) {
                            ViewProjectListData.ViewProjectListItem item = new ViewProjectListData.ViewProjectListItem(
                                    bean.getEnvelopePic(), bean.getTitle(), bean.getDesc(), bean.getNiceDate(), bean.getAuthor(), bean.getApkLink(), bean.isCollect());
                            items.add(item);
                            item.putData(FEED_ARTICLE_ID, bean.getId());
                            item.putData(FEED_ARTICLE_LINK, bean.getLink());
                        }
                        ViewProjectListData viewProjectListData = new ViewProjectListData(items);
                        getView().showProjectListData(viewProjectListData);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getProjectList(projectId, pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getProjectList(projectId, pageNo);
    }
}
