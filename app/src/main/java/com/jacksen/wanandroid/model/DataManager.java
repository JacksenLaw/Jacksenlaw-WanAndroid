package com.jacksen.wanandroid.model;

import com.jacksen.wanandroid.core.prefs.PreferenceImpl;
import com.jacksen.wanandroid.model.bean.base.BaseResponse;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bean.main.banner.BannerBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.model.bean.navi.NavigationListBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyListBean;
import com.jacksen.wanandroid.model.bean.main.usefulsites.UsefulSiteBean;
import com.jacksen.wanandroid.model.bean.todo.TodoBean;
import com.jacksen.wanandroid.model.bean.wx.WxAuthorBean;
import com.jacksen.wanandroid.model.db.DbHelperImpl;
import com.jacksen.wanandroid.model.http.HttpImpl;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述： DataManager  管理所有单例
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class DataManager implements PreferenceImpl, DbHelperImpl, HttpImpl {

    private PreferenceImpl mPreferenceHelperImpl;
    private HttpImpl mHttpImpl;
    private DbHelperImpl mDbHelperImpl;

    public DataManager(PreferenceImpl preferenceHelperImpl, HttpImpl httpImpl, DbHelperImpl dbHelperImpl) {
        super();
        this.mPreferenceHelperImpl = preferenceHelperImpl;
        this.mHttpImpl = httpImpl;
        this.mDbHelperImpl = dbHelperImpl;
    }

    /* PreferenceHelper */
    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelperImpl.setLoginAccount(account);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelperImpl.getLoginAccount();
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelperImpl.setLoginPassword(password);
    }


    @Override
    public String getLoginPassword() {
        return mPreferenceHelperImpl.getLoginPassword();
    }

    @Override
    public void setLoginState(boolean isLogin) {
        mPreferenceHelperImpl.setLoginState(isLogin);
    }

    @Override
    public boolean getLoginState() {
        return mPreferenceHelperImpl.getLoginState();
    }

    @Override
    public void setCookie(String domain, String cookie) {
        mPreferenceHelperImpl.setCookie(domain, cookie);
    }

    @Override
    public String getCookie(String domain) {
        return mPreferenceHelperImpl.getCookie(domain);
    }

    @Override
    public void setCurrentPage(int position) {
        mPreferenceHelperImpl.setCurrentPage(position);
    }

    @Override
    public int getCurrentPage() {
        return mPreferenceHelperImpl.getCurrentPage();
    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferenceHelperImpl.getProjectCurrentPage();
    }


    @Override
    public void setProjectCurrentPage(int position) {
        mPreferenceHelperImpl.setProjectCurrentPage(position);
    }

    @Override
    public void setNoCacheModel(boolean b) {
        mPreferenceHelperImpl.setNoCacheModel(b);
    }

    @Override
    public boolean isNoCacheModel() {
        return mPreferenceHelperImpl.isNoCacheModel();
    }

    @Override
    public void setNoImageModel(boolean b) {
        mPreferenceHelperImpl.setNoImageModel(b);
    }

    @Override
    public boolean isNoImageModel() {
        return mPreferenceHelperImpl.isNoImageModel();
    }

    @Override
    public void setNightMode(boolean nightModel) {
        mPreferenceHelperImpl.setNightMode(nightModel);
    }

    @Override
    public boolean isNightMode() {
        return mPreferenceHelperImpl.isNightMode();
    }

    /* PreferenceHelper */

    /* DbHelperImpl */
    @Override
    public List<HistorySearchData> addHistoryData(String data) {
        return mDbHelperImpl.addHistoryData(data);
    }

    @Override
    public List<HistorySearchData> loadAllHistoryData() {
        return mDbHelperImpl.loadAllHistoryData();
    }

    @Override
    public void clearHistoryData() {
        mDbHelperImpl.clearHistoryData();
    }
    /* DbHelperImpl */


    /* HttpImpl */
    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getFeedArticleList(int pageNum) {
        return mHttpImpl.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getSearchList(int pageNum, String k) {
        return mHttpImpl.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<TopSearchBean>>> getTopSearchData() {
        return mHttpImpl.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteBean>>> getUsefulSites() {
        return mHttpImpl.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return mHttpImpl.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getKnowledgeHierarchyDetailData(int page, int cid) {
        return mHttpImpl.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<BaseResponse<ProjectClassifyListBean>> getProjectListData(int page, int cid) {
        return mHttpImpl.getProjectListData(page, cid);
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyBean>>> getProjectClassifyData() {
        return mHttpImpl.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<List<NavigationListBean>>> getNavigationListData() {
        return mHttpImpl.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectOutsideArticle(String title, String author, String link) {
        return mHttpImpl.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<List<WxAuthorBean>>> getWxAuthorListData() {
        return mHttpImpl.getWxAuthorListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSumData(int id, int page) {
        return mHttpImpl.getWxSumData(id, page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSearchSumData(int id, int page, String k) {
        return mHttpImpl.getWxSearchSumData(id, page, k);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getLoginData(String username, String password) {
        return mHttpImpl.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getRegisterData(String username, String password, String rePassword) {
        return mHttpImpl.getRegisterData(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> logout() {
        return mHttpImpl.logout();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectArticle(int id) {
        return mHttpImpl.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getCollectList(int page) {
        return mHttpImpl.getCollectList(page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectPageArticle(int id) {
        return mHttpImpl.cancelCollectPageArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectArticle(int id) {
        return mHttpImpl.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<List<BannerBean>>> getBannerData() {
        return mHttpImpl.getBannerData();
    }

    @Override
    public Observable<BaseResponse<TodoBean>> getTodoData(int pageNo, Map<String, String> params) {
        return mHttpImpl.getTodoData(pageNo, params);
    }

    /* HttpImpl */
}
