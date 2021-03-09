package com.lefei.demo1.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;
@Component
@Aspect
@Order(3)
@Slf4j
public class Logging {

    @Autowired
    Logger logger;

    @Pointcut("execution(* com.lefei.demo1.controller.*.*(..))")
    private void logController() {
    }
    @Pointcut("execution(* com.lefei.demo1.service.*.*(..))")
    private void logeService() {
    }

    @Before("logController()||logeService()")
    public void beforeAdvice(JoinPoint joinPoint) {

            Object[] args = joinPoint.getArgs();
            String methodName = joinPoint.getSignature().getName();
            logger.info( methodName + "方法开始执行");
            logger.info("参数为：" + Arrays.asList(args));

    }

    @AfterReturning(pointcut = "logController()||logeService()", returning = "retVal")
    public void doReturn(JoinPoint joinPoint, Object retVal) {
        String methodName = joinPoint.getSignature().getName();
        logger.info( methodName + "方法返回值:" + retVal);
    }

    @After("logController()||logeService()")
    public void afterAdvice(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info(className +  "类的所有方法执行完毕" );
    }
    @AfterThrowing(pointcut = "logController()||logeService()", throwing = "ex")
    public void AfterThrowingAdvice(IllegalArgumentException ex){
        System.out.println("有异常: " + ex.toString());
    }
}