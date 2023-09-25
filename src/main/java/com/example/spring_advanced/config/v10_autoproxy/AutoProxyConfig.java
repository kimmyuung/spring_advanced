package com.example.spring_advanced.config.v10_autoproxy;

import com.example.spring_advanced.config.AppV6Config;
import com.example.spring_advanced.config.AppV7Config;
import com.example.spring_advanced.config.v8_proxyfactory.advice.LogTraceAdvice;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV6Config.class, AppV7Config.class})
public class AutoProxyConfig {

 //   @Bean
    public Advisor getAdvisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // 포인트컷은 2가지에 사용
    // 1. 프록시 적용 여부 판단 - 생성 단계
    // 자동 프록시 생성기는 포인트컷을 사용해서 해당 빈이 프록시를 생성할 필요가 있는지 없는지 체크
    // 조건에 맞는 것이 있으면 프록시 생성, 없으면 프록시 생성하지 않음
    // 2. 어드바이스 적용 여부 판단 - 사용 단계
    // 메서드 호출 되었을 때  request()는 포인트컷 조건에 만족하므로 프록시는 어드바이스 먼저 호출 후 target 호출
    // noLog는 어드바이스 호출 없이 바로 target 호출

    @Bean
    public Advisor  advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.spring_advanced.app..*(..)) && !execution(* com.example.spring_advanced.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
