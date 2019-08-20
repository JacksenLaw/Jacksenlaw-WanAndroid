package com.jacksen.aspectj.core.login;

import android.content.Context;

import com.jacksen.aspectj.annotation.Login;
import com.jacksen.aspectj.core.exception.AnnotationException;
import com.jacksen.aspectj.core.exception.NoInitException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者： LuoM
 * 创建时间：2019/8/19/0019
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/19/0019
 * 更新内容：
 */
@Aspect
public class LoginAspect {

    @Pointcut("execution(@com.jacksen.aspectj.annotation.Login * *(..))")
    public void loginIntercept() {
    }

    @Around("loginIntercept()")
    public void aroundLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        ILogin iLogin = LoginAssistant.getInstance().getLogin();
        if (iLogin == null) {
            throw new NoInitException("LoginSDK 没有初始化！");
        }

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new AnnotationException("@Login 注解只能用于方法上");
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Login loginIntercept = methodSignature.getMethod().getAnnotation(Login.class);
        if(loginIntercept == null){
            return;
        }

        Context context = LoginAssistant.getInstance().getApplicationContext();
        if(iLogin.isLogin(context)){
            joinPoint.proceed();
        }else{
            iLogin.login(context,loginIntercept.userDefine());
        }

    }


}
