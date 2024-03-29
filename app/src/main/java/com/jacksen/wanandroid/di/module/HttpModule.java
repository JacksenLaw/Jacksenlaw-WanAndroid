package com.jacksen.wanandroid.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jacksen.wanandroid.BuildConfig;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.app.WanAndroidApp;
import com.jacksen.wanandroid.di.qualifier.AppStoreUrl;
import com.jacksen.wanandroid.model.http.api.GeeksApis;
import com.jacksen.wanandroid.model.http.interceptor.CacheInterceptor;
import com.jacksen.wanandroid.model.http.interceptor.HttpLoggingInterceptor;
import com.jacksen.wanandroid.model.http.socket.SSLFactory;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者： LuoM
 * 时间： 2019/1/27 0027
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    GeeksApis provideGeeksApi(@AppStoreUrl Retrofit retrofit) {
        return retrofit.create(GeeksApis.class);
    }

    @Provides
    @Singleton
    @AppStoreUrl
    Retrofit provideStoreRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GeeksApis.HOST);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //cookie认证
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(WanAndroidApp.getInstance())));

        //设置SSL证书管理
        InputStream[] is = new InputStream[]{new Buffer().writeUtf8(Constants.SSL_KEY).inputStream()};
        SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory(is, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, String baseUrl) {
        return builder
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

}
