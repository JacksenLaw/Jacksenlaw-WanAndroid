package com.jacksen.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者： LuoM
 * 创建时间：2019/8/19/0019
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/19/0019
 * 更新内容：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    int userDefine() default 0;
}
