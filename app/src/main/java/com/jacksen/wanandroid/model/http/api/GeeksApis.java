package com.jacksen.wanandroid.model.http.api;


import com.jacksen.wanandroid.model.bean.base.BaseResponse;
import com.jacksen.wanandroid.model.bean.hierarchy.KnowledgeHierarchyData;
import com.jacksen.wanandroid.model.bean.main.banner.BannerBean;
import com.jacksen.wanandroid.model.bean.main.collect.FeedArticleListBean;
import com.jacksen.wanandroid.model.bean.main.login.LoginBean;
import com.jacksen.wanandroid.model.bean.main.search.TopSearchBean;
import com.jacksen.wanandroid.model.bean.navi.NavigationListBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyBean;
import com.jacksen.wanandroid.model.bean.project.ProjectClassifyListBean;
import com.jacksen.wanandroid.model.bean.main.usefulsites.UsefulSiteBean;
import com.jacksen.wanandroid.model.bean.wx.WxAuthorBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GeeksApis {

    String HOST = "https://www.wanandroid.com/";

    /**
     * 获取feed文章列表
     *
     * @param num 页数
     * @return feed文章列表数据
     */
    @GET("article/list/{num}/json")
    Observable<BaseResponse<FeedArticleListBean>> getFeedArticleList(@Path("num") int num);

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     *
     * @param page page
     * @param k    POST search key
     * @return 搜索数据
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListBean>> getSearchList(@Path("page") int page, @Field("k") String k);

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return 热门搜索数据
     */
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Observable<BaseResponse<List<TopSearchBean>>> getTopSearchData();

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    @GET("friend/json")
    Observable<BaseResponse<List<UsefulSiteBean>>> getUsefulSites();

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return 广告栏数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> getBannerData();

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     *
     * @return 知识体系数据
     */
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0?cid=60
     *
     * @param page page num
     * @param cid  second page id
     * @return 知识体系feed文章数据
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<FeedArticleListBean>> getKnowledgeHierarchyDetailData(@Path("page") int page, @Query("cid") int cid);

    /**
     * 导航
     * http://www.wanandroid.com/navi/json
     *
     * @return 导航数据
     */
    @GET("navi/json")
    Observable<BaseResponse<List<NavigationListBean>>> getNavigationListData();

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     *
     * @return 项目分类数据
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectClassifyBean>>> getProjectClassifyData();

    /**
     * 项目类别数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page page num
     * @param cid  second page id
     * @return 项目类别数据
     */
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectClassifyListBean>> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     *
     * @return 公众号列表数据
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WxAuthorBean>>> getWxAuthorListData();

    /**
     * 获取当前公众号某页的数据
     * http://wanandroid.com/wxarticle/list/405/1/json
     *
     * @param id   公众号id
     * @param page 公众号页码
     * @return 获取当前公众号某页的数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<FeedArticleListBean>> getWxSumData(@Path("id") int id, @Path("page") int page);

    /**
     * 指定搜索内容，搜索当前公众号的某页的此类数据
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     *
     * @param id   公众号id
     * @param page 公众号页码
     * @param k    key值 例如：java
     * @return 指定搜索内容，搜索当前公众号的某页的此类数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<FeedArticleListBean>> getWxSearchSumData(@Path("id") int id, @Path("page") int page, @Query("k") String k);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 登陆数据
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> getLoginData(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username   user name
     * @param password   password
     * @param repassword re password
     * @return 注册数据
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     *
     * @return 登陆数据
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginBean>> logout();

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return 收藏站内文章数据
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<FeedArticleListBean>> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     * http://www.wanandroid.com/lg/collect/add/json
     *
     * @param title  title
     * @param author author
     * @param link   link
     * @return 收藏站外文章数据
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListBean>> addCollectOutsideArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);


    /**
     * 获取收藏列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page page number
     * @return 收藏列表数据
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<FeedArticleListBean>> getCollectList(@Path("page") int page);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id       article id
     * @param originId origin id
     * @return 取消站内文章数据
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListBean>> cancelCollectPageArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id       article id
     * @param originId origin id
     * @return 取消收藏页面站内文章数据
     */
    @POST("lg/uncollect_originId/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListBean>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 新增一个Todo
     *
     * @param title    新增标题（必须）
     * @param content  新增详情（必须）
     * @param date     2018-08-01 预定完成时间（不传默认当天，建议传）
     * @param type     type 可以用于，在app 中预定义几个类别,例如：工作1、生活2、 娱乐3，新增的时候传入1，2，3，查询的时候，传入type 进行筛选,大于0的整数（可选）
     * @param priority 定义优先级，1：重要、2：一般，大于0的整数（可选）
     * @return BaseResponse
     */
    @POST("lg/todo/add/json")
    Observable<BaseResponse> addNewTodo(@Field("title") String title, @Field("content") String content,
                                        @Field("date") String date, @Field("type") int type,
                                        @Field("priority") int priority);

    /**
     * 更新一个Todo
     *
     * @param id       拼接在链接上，为唯一标识，列表数据返回时，每个todo 都会有个id标识 （必须）
     * @param title    更新标题 （必须）
     * @param content  新增详情（必须）
     * @param date     2018-08-01（必须）
     * @param status   0 // 0为未完成，1为完成   如果有当前状态没有携带，会被默认值更新，比如当前 todo status=1，更新时没有带上，会认为被重置。
     * @param type     预定义几个类别,例如：工作1、生活2、 娱乐3,大于0的整数（可选）
     * @param priority 定义优先级，1：重要、2：一般，大于0的整数（可选）
     * @return
     */
    @POST("lg/todo/update/{id}/json")
    Observable<BaseResponse> updateTodo(@Path("id") int id, @Field("title") String title,
                                        @Field("content") String content,
                                        @Field("date") String date, @Field("status") String status,
                                        @Field("type") int type, @Field("priority") int priority);

    /**
     * 删除一个Todo
     *
     * @param id 拼接在链接上，为唯一标识
     * @return
     */
    @POST("lg/todo/delete/{id}/json")
    Observable<BaseResponse> deleteTodo(@Path("id") int id);

    /**
     * 仅更新完成状态Todo
     *
     * @param id     拼接在链接上，为唯一标识
     * @param status 0或1，传1代表未完成到已完成，反之则反之。
     * @return
     */
    @POST("lg/todo/done/{id}/json")
    Observable<BaseResponse> doneTodo(@Path("id") int id, @Field("status") String status);

    /**
     * TODO列表
     *
     * @param page     页码从1开始，拼接在url 上
     * @param status   状态， 1-完成；0未完成; 默认全部展示；
     * @param type     创建时传入的类型, 默认全部展示
     * @param priority 创建时传入的优先级；默认全部展示
     * @param orderby  1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     * @return
     */
    @POST("lg/todo/v2/list/{page}/json")
    Observable<BaseResponse> getTodoList(@Path("page") int page, @Field("status") String status,
                                         @Field("type") String type, @Field("priority") String priority,
                                         @Field("orderby") String orderby);

}
