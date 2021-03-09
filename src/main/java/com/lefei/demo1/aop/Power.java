package com.lefei.demo1.aop;

import com.lefei.demo1.exception.PermissionDeniedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author le
 * date:    2020/12/25
 * describe：
 */
@Component
@Aspect
@Order(2)
public class Power {
    @Autowired
    Logger logger;

    @Pointcut("execution(* com.lefei.demo1.controller.UserController.*(..))")
    private void UserController() {
    }

    @Pointcut("execution(* com.lefei.demo1.controller.ProductController.*(..))")
    private void ProductController() {
    }

    @Before("UserController()||ProductController()")
    public void before() throws PermissionDeniedException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String username = (String)requestAttributes.getAttribute("username",RequestAttributes.SCOPE_SESSION);
        if (username == null) {
            logger.info("未登录");
            throw new PermissionDeniedException("权限不足");
        }
    }
}
