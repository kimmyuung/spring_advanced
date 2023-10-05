package com.example.spring_advanced.aop.pointcut;

import com.example.spring_advanced.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/*
* application.properties
* spring.aop.proxy-target-class=true --> CGLIB
* spring.aop.proxy-target-class=false -> JDK dynamic proxy
 * */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK Dynamic Proxy
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}" , memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        @Around("this(com.example.spring_advanced.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}" , joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(com.example.spring_advanced.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}" , joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(com.example.spring_advanced.aop.member.MemberService)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}" , joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(com.example.spring_advanced.aop.member.MemberService)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}" , joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
