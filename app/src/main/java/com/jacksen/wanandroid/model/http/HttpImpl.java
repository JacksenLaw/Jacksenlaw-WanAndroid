package com.jacksen.wanandroid.model.http;

import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bean.main.banner.BannerBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bean.base.BaseResponse;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.model.bean.navi.NavigationListBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyListBean;
import com.jacksen.wanandroid.model.bean.main.usefulsites.UsefulSiteBean;
import com.jacksen.wanandroid.model.bean.wx.WxAuthorBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HttpImpl {

    /**
     * 获取feed文章列表
     *
     * @param pageNum 页数
     * @return feed文章列表数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getFeedArticleList(int pageNum);


    /**
     * 获取搜索的文章列表
     *
     * @param pageNum 页数
     * @param k 关键字
     * @return 搜索的文章数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getSearchList(int pageNum, String k);

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return 热门搜索数据
     */
    Observable<BaseResponse<List<TopSearchBean>>> getTopSearchData();

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    Observable<BaseResponse<List<UsefulSiteBean>>> getUsefulSites();

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     *
     * @return 广告栏数据
     */
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0?cid=60
     *
     * @param page page num
     * @param cid second page id
     * @return 知识体系数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getKnowledgeHierarchyDetailData(int page, int cid);

    /**
     * 导航
     * http://www.wanandroid.com/navi/json
     *
     * @return 知识体系feed文章数据
     */
    Observable<BaseResponse<List<NavigationListBean>>> getNavigationListData();

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     *
     * @return 导航数据
     */
    Observable<BaseResponse<List<ProjectClassifyBean>>> getProjectClassifyData();

    /**
     * 项目类别数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page page num
     * @param cid second page id
     * @return 项目分类数据
     */
    Observable<BaseResponse<ProjectClassifyListBean>> getProjectListData(int page, int cid);

    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     *
     * @return 公众号列表数据
     */
    Observable<BaseResponse<List<WxAuthorBean>>> getWxAuthorListData();

    /**
     * http://wanandroid.com/wxarticle/list/405/1/json
     *
     * @param id
     * @param page
     * @return 获取当前公众号某页的数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getWxSumData(int id, int page);

    /**
     * 指定搜索内容，搜索当前公众号的某页的此类数据
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     *
     * @param id
     * @param page
     * @param k
     * @return 指定搜索内容，搜索当前公众号的某页的此类数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getWxSearchSumData(int id, int page, String k);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 项目类别数据
     */
    Observable<BaseResponse<LoginBean>> getLoginData(String username, String password);
//

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username   user name
     * @param password   password
     * @param rePassword re password
     * @return 登陆数据
     */
    Observable<BaseResponse<LoginBean>> getRegisterData(String username, String password, String rePassword);

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginBean>> logout();

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return 注册数据
     */
    Observable<BaseResponse<FeedArticleListBean>> addCollectArticle(int id);

    /**
     * 收藏站外文章
     * http://www.wanandroid.com/lg/collect/add/json
     *
     * @param title title
     * @param author author
     * @param link link
     * @return 收藏站内文章数据
     */
    Observable<BaseResponse<FeedArticleListBean>> addCollectOutsideArticle(String title, String author, String link);

    /**
     * 获取收藏列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page page number
     * @return 收藏站外文章数据
     */
    Observable<BaseResponse<FeedArticleListBean>> getCollectList(int page);

    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @return 收藏列表数据
     */
    Observable<BaseResponse<FeedArticleListBean>> cancelCollectPageArticle(int id);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @return 取消站内文章数据
     */
    Observable<BaseResponse<FeedArticleListBean>> cancelCollectArticle(int id);

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return 取消收藏页面站内文章数据
     */
    Observable<BaseResponse<List<BannerBean>>> getBannerData();


}
