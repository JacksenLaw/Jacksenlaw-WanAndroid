package com.jacksen.wanandroid.model.http;

import android.content.Context;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.model.bean.base.BaseResponse;
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
import com.jacksen.wanandroid.model.http.api.GeeksApis;
import com.jacksen.wanandroid.view.bean.todo.FilterBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class HttpImpl implements HttpHelper {

    private GeeksApis storeApis;

    @Inject
    HttpImpl(GeeksApis storeApis) {
        this.storeApis = storeApis;
    }


    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getFeedArticleList(int pageNum) {
        return storeApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getSearchList(int pageNum, String k) {
        return storeApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<TopSearchBean>>> getTopSearchData() {
        return storeApis.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteBean>>> getUsefulSites() {
        return storeApis.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return storeApis.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getKnowledgeHierarchyDetailData(int page, int cid) {
        return storeApis.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListBean>>> getNavigationListData() {
        return storeApis.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyBean>>> getProjectClassifyData() {
        return storeApis.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<ProjectClassifyListBean>> getProjectListData(int page, int cid) {
        return storeApis.getProjectListData(page, cid);
    }

    @Override
    public Observable<BaseResponse<List<WxAuthorBean>>> getWxAuthorListData() {
        return storeApis.getWxAuthorListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSumData(int id, int page) {
        return storeApis.getWxSumData(id, page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getWxSearchSumData(int id, int page, String k) {
        return storeApis.getWxSearchSumData(id, page, k);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getLoginData(String username, String password) {
        return storeApis.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> getRegisterData(String username, String password, String rePassword) {
        return storeApis.getRegisterData(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> logout() {
        return storeApis.logout();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectArticle(int id) {
        return storeApis.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> addCollectOutsideArticle(String title, String author, String link) {
        return storeApis.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> getCollectList(int page) {
        return storeApis.getCollectList(page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectPageArticle(int id) {
        return storeApis.cancelCollectPageArticle(id, -1);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListBean>> cancelCollectArticle(int id) {
        return storeApis.cancelCollectArticle(id, -1);
    }

    @Override
    public Observable<BaseResponse<List<BannerBean>>> getBannerData() {
        return storeApis.getBannerData();
    }

    @Override
    public Observable<BaseResponse<TodoBean>> getTodoData(int pageNo, Map<String, String> params) {
        return storeApis.getTodoList(pageNo, params);
    }

    @Override
    public Observable<BaseResponse<NewTodoBean>> addNewTodo(String title, String content, String date, String type, String priority) {
        return storeApis.addNewTodo(title, content, date, type, priority);
    }

    @Override
    public Observable<BaseResponse<TodoBean>> updateTodo(int id, String title, String content, String date, String status, String type, String priority) {
        return storeApis.updateTodo(id, title, content, date, status, type, priority);
    }

    @Override
    public Observable<ResponseBody> deleteTodo(int id) {
        return storeApis.deleteTodo(id);
    }

    @Override
    public Observable<BaseResponse<TodoBean>> updateOnlyStatusTodo(int id, String status) {
        return storeApis.updateOnlyStatusTodo(id, status);
    }

    @Override
    public Observable<List<FilterBean>> getFilterData() {
        return Observable.create(emitter -> {
            List<FilterBean> list = new ArrayList<>();

            Context applicationContext = WanAndroidApp.getInstance();
            
            List<FilterBean.FilterBeanItem> typeItems = new ArrayList<>();
            typeItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_all), "type", "0"));
            typeItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_type_work), "type", "1"));
            typeItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_type_study), "type", "2"));
            typeItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_type_life), "type", "3"));

            List<FilterBean.FilterBeanItem> priorityItems = new ArrayList<>();
            priorityItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_all), "priority", "0"));
            priorityItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_priority_important), "priority", "1"));
            priorityItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_priority_general), "priority", "2"));
            priorityItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_priority_ordinary), "priority", "3"));

            List<FilterBean.FilterBeanItem> sortItems = new ArrayList<>();
            sortItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_sort_create_date_order), "orderby", "3"));
            sortItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_sort_create_date_reverse_order), "orderby", "4"));
            sortItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_sort_completion_date_order), "orderby", "1"));
            sortItems.add(new FilterBean.FilterBeanItem(applicationContext.getString(R.string.filter_sort_completion_date_reverse_order), "orderby", "2"));

            list.add(new FilterBean(applicationContext.getString(R.string.filter_type), typeItems));
            list.add(new FilterBean(applicationContext.getString(R.string.filter_priority), priorityItems));
            list.add(new FilterBean(applicationContext.getString(R.string.filter_sort), sortItems));
            emitter.onNext(list);
        });
    }
}
