package com.jacksen.wanandroid.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.view.ui.knowledge.activity.KnowledgeDetailActivity;
import com.jacksen.wanandroid.view.ui.main.activity.ArticleDetailActivity;
import com.jacksen.wanandroid.view.ui.main.activity.SearchListActivity;

public class JudgeUtils {

    /**
     * @param mActivity       mActivity
     * @param activityOptions activityOptions
     * @param id              文章id
     * @param articleTitle    文章Title
     * @param articleLink     文章Link
     * @param isCollect       文章是否已经收藏
     * @param isCollectPage   文章是否从收藏页面跳转过来
     * @param isCommonSite    isCommonSite
     * @param turnType        跳转区分
     */
    public static void startArticleDetailActivity(Context mActivity, ActivityOptions activityOptions, int id, String articleTitle,
                                                  String articleLink, boolean isCollect,
                                                  boolean isCollectPage, boolean isCommonSite,
                                                  String turnType) {
        Intent intent = new Intent(mActivity, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, id);
        intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
        intent.putExtra(Constants.ARTICLE_LINK, articleLink);
        intent.putExtra(Constants.IS_COLLECT, isCollect);
        intent.putExtra(Constants.IS_COLLECT_PAGE, isCollectPage);
        intent.putExtra(Constants.IS_COMMON_SITE, isCommonSite);
        intent.putExtra(Constants.TURN_TYPE, turnType);
        if (activityOptions != null && !Build.MANUFACTURER.contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.startActivity(intent, activityOptions.toBundle());
        } else {
            mActivity.startActivity(intent);
        }
    }

    public static void startKnowledgeHierarchyDetailActivity(Context mActivity, boolean isSingleChapter,
                                                             String superChapterName, String chapterName, int chapterId) {
        Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
        intent.putExtra(Constants.IS_SINGLE_CHAPTER, isSingleChapter);
        intent.putExtra(Constants.SUPER_CHAPTER_NAME, superChapterName);
        intent.putExtra(Constants.CHAPTER_NAME, chapterName);
        intent.putExtra(Constants.CHAPTER_ID, chapterId);
        mActivity.startActivity(intent);
    }

    public static void startSearchListActivity(Context mActivity, String searchText) {
        Intent intent = new Intent(mActivity, SearchListActivity.class);
        intent.putExtra(Constants.SEARCH_TEXT, searchText);
        mActivity.startActivity(intent);
    }


}
