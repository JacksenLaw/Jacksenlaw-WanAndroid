package com.jacksen.wanandroid.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 作者： LuoM
 * 时间： 2019/1/27 0027
 * 描述： 自定义注解 Qualifier限定符
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AppStoreUrl {
}
