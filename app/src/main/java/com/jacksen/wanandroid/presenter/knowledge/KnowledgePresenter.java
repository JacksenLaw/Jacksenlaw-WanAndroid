package com.jacksen.wanandroid.presenter.knowledge;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.KLog;
import com.jacksen.wanandroid.view.bean.knowledge.ViewKnowledgeListData;
import com.jacksen.wanandroid.view.ui.knowledge.activity.KnowledgeDetailActivity;

import java.io.Serializable;
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
public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter {

    private static String CHILDREN = "children";
    private int pageNo = 0;

    @Inject
    public KnowledgePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.SCROLL_TO_KNOWLEDGE_PAGE,Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        getView().scrollToTheTop(integer);
                    }
                });
    }

    private void getKnowledgeHierarchyData(int pageNo) {
        addSubscribe(dataManager.getKnowledgeHierarchyData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<KnowledgeHierarchyData>>(getView(),
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_knowledge_data),
                        true) {
                    @Override
                    public void onNext(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {
                        ArrayList<ViewKnowledgeListData.ViewKnowledgeItem> items = new ArrayList<>();
                        for (KnowledgeHierarchyData hierarchyData : knowledgeHierarchyDataList) {
                            StringBuilder content = new StringBuilder();
                            for (KnowledgeHierarchyData data : hierarchyData.getChildren()) {
                                content.append(data.getName()).append("   ");
                            }
                            ViewKnowledgeListData.ViewKnowledgeItem item = new ViewKnowledgeListData.ViewKnowledgeItem(
                                    hierarchyData.getName(), content.toString()
                            );
                            item.putData(CHILDREN, hierarchyData.getChildren());
                            items.add(item);
                        }
                        ViewKnowledgeListData viewKnowledgeListData = new ViewKnowledgeListData(items);
                        getView().showKnowledgeHierarchyData(viewKnowledgeListData);
                        getView().showNormal();
                    }
                }));
    }

    @Override
    public void onRefresh() {
        getKnowledgeHierarchyData(pageNo);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getKnowledgeHierarchyData(pageNo);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getFragment().getActivity(), KnowledgeDetailActivity.class);
        intent.putExtra(Constants.ARG_PARAM1, (Serializable) adapter.getData().get(position));
        KLog.i(((ViewKnowledgeListData.ViewKnowledgeItem) adapter.getData().get(position)).getData(CHILDREN).toString());
        intent.putExtra(Constants.ARG_PARAM2, (Serializable) ((ViewKnowledgeListData.ViewKnowledgeItem) adapter.getData().get(position)).getData(CHILDREN));
        if (modelFiltering()) {
            getFragment().getActivity().startActivity(intent);
        } else {
            getFragment().getActivity().startActivity(intent);
        }
    }

    /**
     * 机型适配
     *
     * @return 返回true表示非三星机型且Android 6.0以上
     */
    private boolean modelFiltering() {
        return !Build.MANUFACTURER.contains(Constants.SAMSUNG) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
