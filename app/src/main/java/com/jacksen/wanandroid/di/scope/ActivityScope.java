package com.jacksen.wanandroid.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 作者： LuoM
 * 时间： 2019/1/25 0025
 * 描述： 自定义注解 实现@Scope 用来标识作用域是在Activity中
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
