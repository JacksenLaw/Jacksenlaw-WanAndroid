package com.jacksen.wanandroid.presenter.project;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyBean;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.view.bean.project.ViewProjectClassifyBean;

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
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Inject
    public ProjectPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(ProjectContract.View view) {
        super.attachView(view);

    }

    @Override
    public void getProjectClassify() {
        addSubscribe(dataManager.getProjectClassifyData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<ProjectClassifyBean>>(getView(),
                        getFragment().getString(R.string.failed_to_obtain_project_classify_data)
                        , true) {
                    @Override
                    public void onNext(List<ProjectClassifyBean> projectClassifyDataList) {
                        ArrayList<ViewProjectClassifyBean.ViewProjectClassifyItemBean> itemBeans = new ArrayList<>();
                        for (ProjectClassifyBean classifyBean : projectClassifyDataList) {
                            ViewProjectClassifyBean.ViewProjectClassifyItemBean itemBean = new ViewProjectClassifyBean.ViewProjectClassifyItemBean(
                                    classifyBean.getId(), classifyBean.getName());
                            itemBeans.add(itemBean);
                        }
                        getView().showNormal();
                        getView().showProjectClassify(itemBeans);
                    }
                }));
    }
}
