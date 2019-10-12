package com.jacksen.wanandroid.presenter.knowledge.know_detail_activity;

import android.content.Intent;

import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.view.bean.knowledge.ViewKnowledgeListData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/4/7 0007
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class KnowledgeActivityPresenter extends BasePresenter<KnowledgeActivityContract.View> implements KnowledgeActivityContract.Presenter {

    @Inject
    public KnowledgeActivityPresenter(DataManager dataManager) {
        super(dataManager);

    }

    @Override
    public void injectEvent() {
        super.injectEvent();
        LiveDataBus.get()
                .with(BusConstant.SWITCH_NAVIGATION_PAGE)
                .observe(this, o -> getView().showSwitchNavigation());
        LiveDataBus.get()
                .with(BusConstant.SWITCH_PROJECT_PAGE)
                .observe(this, o -> getView().showSwitchProject());
    }

    @Override
    public void handData(Intent intent) {
        try {
            if (intent.getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false)) {
                startSingleChapterPager(intent);
            } else {
                startNormalKnowledgeListPager(intent);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jumpToTop() {
        LiveDataBus.get().with(BusConstant.JUMP_TO_TOP_OF_KNOWLEDGE_LIST).postValue(0);
    }

    /**
     * 装载多个列表的知识体系页面（knowledge进入）
     */
    private void startNormalKnowledgeListPager(Intent intent) {
        ViewKnowledgeListData.ViewKnowledgeItem knowledgeHierarchyData = (ViewKnowledgeListData.ViewKnowledgeItem) intent.getSerializableExtra(Constants.ARG_PARAM1);
        if (knowledgeHierarchyData == null || knowledgeHierarchyData.getTitle() == null) {
            return;
        }
        getView().showTitle(knowledgeHierarchyData.getTitle().trim());
//        List<KnowledgeHierarchyData> children = (List<KnowledgeHierarchyData>) intent.getSerializableExtra(Constants.ARG_PARAM2);
        List<KnowledgeHierarchyData> children = cast(intent.getSerializableExtra(Constants.ARG_PARAM2));
        List<String> pageTitle = new ArrayList<>();
        for (KnowledgeHierarchyData data : children) {
            getView().showFragment(data.getId());
            pageTitle.add(data.getName());
        }
        boolean isSingleChapter = intent.getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false);
        getView().onKnowledgeHierarchyData(pageTitle, isSingleChapter, "");
    }

    /**
     * 装载单个列表的知识体系页面（tag进入）
     */
    private void startSingleChapterPager(Intent intent) {
        getView().showTitle(intent.getStringExtra(Constants.SUPER_CHAPTER_NAME));
        String chapterName = intent.getStringExtra(Constants.CHAPTER_NAME);
        int chapterId = intent.getIntExtra(Constants.CHAPTER_ID, 0);
        boolean isSingleChapter = intent.getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false);
        getView().showFragment(chapterId);
        getView().onKnowledgeHierarchyData(null, isSingleChapter, chapterName);
    }

    private <T> List<T> cast(Serializable t) {
        return (List<T>) t;
    }

}
