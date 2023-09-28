package com.example.spring_advanced.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    @Pointcut("execution(* com.example.spring_advanced.aop.order*(..))")
    private void allOrder(){ // pointcut signature

    }

    // 패키지와 하위 패키지 표현하는 AspectJ 포인트컷 표현식
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}" , joinPoint.getSignature()); // join point signature
        return joinPoint.proceed();
    }

}
