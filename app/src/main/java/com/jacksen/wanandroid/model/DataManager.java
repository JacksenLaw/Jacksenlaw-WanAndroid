package com.jacksen.wanandroid.model;

import com.jacksen.wanandroid.core.prefs.PreferenceHelper;
import com.jacksen.wanandroid.model.bean.base.BaseResponse;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bean.main.banner.BannerBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.model.bean.main.usefulsites.UsefulSiteBean;
import com.jacksen.wanandroid.model.bean.navi.NavigationListBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyListBean;
import com.jacksen.wanandroid.model.bean.todo.NewTodoBean;
import com.jacksen.wanandroid.model.bean.todo.TodoBean;
import com.jacksen.wanandroid.model.bean.wx.WxAuthorBean;
import com.jacksen.wanandroid.model.db.DbHelper;
import com.jacksen.wanandroid.model.http.HttpHelper;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述： DataManager  管理所有单例
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class DataManager implements PreferenceHelper, DbHelper, HttpHelper {

    private PreferenceHelper mPreferenceHelper;
    private HttpHelper mHttpHelper;
    private DbHelper mDbDbHelper;

    public DataManager(PreferenceHelper mPreferenceHelper, HttpHelper mHttpHelper, DbHelper mDbDbHelper) {
        super();
        this.mPreferenceHelper = mPreferenceHelper;
        this.mHttpHelper = mHttpHelper;
        this.mDbDbHelper = mDbDbHelper;
    }

    /* PreferenceImpl */
    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelper.setLoginPassword(password);
    }


    @Override
    public String getLoginPassword() {
        return mPreferenceHelper.getLoginPassword();
    }

    @Override
    public void setLoginState(boolean isLogin) {
        mPreferenceHelper.setLoginState(isLogin);
    }

    @Override
    public boolean isLogin() {
        return mPreferenceHelper.isLogin();
    }

    @Override
    public void setCookie(String domain, String cookie) {
        mPreferenceHelper.setCookie(domain, cookie);
    }

    @Override
    public String getCookie(String domain) {
        return mPreferenceHelper.getCookie(domain);
    }

    @Override
    public void setCurrentPage(int position) {
        mPreferenceHelper.setCurrentPage(position);
    }

    @Override
    public int getCurrentPage() {
        return mPreferenceHelper.getCurrentPage();
    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferenceHelper.getProjectCurrentPage();
    }


    @Override
    public void setProjectCurrentPage(int position) {
        mPreferenceHelper.setProjectCurrentPage(position);
    }

    @Override
    public void setNoCacheModel(boolean b) {
        mPreferenceHelper.setNoCacheModel(b);
    }

    @Override
    public boolean isNoCacheModel() {
        return mPreferenceHelper.isNoCacheModel();
    }

    @Override
    public void setNoImageModel(boolean b) {
        mPreferenceHelper.setNoImageModel(b);
    }

    @Override
    public boolean isNoImageModel() {
        return mPreferenceHelper.isNoImageModel();
    }

    @Override
    public void setNightMode(boolean nightModel) {
        mPreferenceHelper.setNightMode(nightModel);
    }

    @Override
    public boolean isNightMode() {
        return mPreferenceHelper.isNightMode();
    }

    /* PreferenceImpl */

    /* DbHelper */
    @Override
    public List<HistorySearchData> addHistoryData(String data) {
        return mDbDbHelper.addHistoryData(data);
    }

    @Override
    public List<HistorySearchData> loadAllHistoryData() {
        return mDbDbHelper.loadAllHistoryData();
    }

    @Override
    public void clearHistoryData() {
        mDbDbHelper.clearHistoryData();
    }
    /* DbHelper */


    /* HttpHelper */
    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getFeedArticleList(int pageNum) {
        return mHttpHelper.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getSearchList(int pageNum, String k) {
        return mHttpHelper.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<TopSearchBean>>> getTopSearchData() {
        return mHttpHelper.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteBean>>> getUsefulSites() {
        return mHttpHelper.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return mHttpHelper.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getKnowledgeHierarchyDetailData(int page, int cid) {
        return mHttpHelper.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<BaseResponse<ProjectClassifyListBean>> getProjectListData(int page, int cid) {
        return mHttpHelper.getProjectListData(page, cid);
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyBean>>> getProjectClassifyData() {
        return mHttpHelper.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<List<NavigationListBean>>> getNavigationListData() {
        return mHttpHelper.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectOutsideArticle(String title, String author, String link) {
        return mHttpHelper.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<List<WxAuthorBean>>> getWxAuthorListData() {
        return mHttpHelper.getWxAuthorListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSumData(int id, int page) {
        return mHttpHelper.getWxSumData(id, page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSearchSumData(int id, int page, String k) {
        return mHttpHelper.getWxSearchSumData(id, page, k);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getLoginData(String username, String password) {
        return mHttpHelper.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getRegisterData(String username, String password, String rePassword) {
        return mHttpHelper.getRegisterData(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> logout() {
        return mHttpHelper.logout();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectArticle(int id) {
        return mHttpHelper.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getCollectList(int page) {
        return mHttpHelper.getCollectList(page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectPageArticle(int id) {
        return mHttpHelper.cancelCollectPageArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectArticle(int id) {
        return mHttpHelper.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<List<BannerBean>>> getBannerData() {
        return mHttpHelper.getBannerData();
    }

    @Override
    public Observable<BaseResponse<TodoBean>> getTodoData(int pageNo, Map<String, String> params) {
        return mHttpHelper.getTodoData(pageNo, params);
    }

    @Override
    public Observable<BaseResponse<NewTodoBean>> addNewTodo(String title, String content, String date, String type, String priority) {
        return mHttpHelper.addNewTodo(title, content, date, type, priority);
    }

    @Override
    public Observable<BaseResponse<TodoBean>> updateTodo(int id, String title, String content, String date, String status, String type, String priority) {
        return mHttpHelper.updateTodo(id, title, content, date, status, type, priority);
    }

    @Override
    public Observable<ResponseBody> deleteTodo(int id) {
        return mHttpHelper.deleteTodo(id);
    }

    @Override
    public Observable<BaseResponse<TodoBean>> updateOnlyStatusTodo(int id, String status) {
        return mHttpHelper.updateOnlyStatusTodo(id, status);
    }

    @Override
    public Observable<List<FilterBean>> getFilterData() {
        return mHttpHelper.getFilterData();
    }

    /* HttpHelper */
}
