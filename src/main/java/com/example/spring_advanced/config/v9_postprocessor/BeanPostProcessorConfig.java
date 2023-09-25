package com.example.spring_advanced.config.v9_postprocessor;

import com.example.spring_advanced.config.AppV6Config;
import com.example.spring_advanced.config.AppV7Config;
import com.example.spring_advanced.config.v8_proxyfactory.advice.LogTraceAdvice;
import com.example.spring_advanced.config.v9_postprocessor.postprocessor.PackageLogTracePostProcessor;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV6Config.class, AppV7Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("com.example.spring_advanced.app",getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // 자동 프록시 생성기의 작동 과정
    // 1. 생성 : 스프링이 스프링 빈 대상이 되는 객체를 생성 - 컴포넌트 스캔 , @Bean 모두 포함
    // 2. 전달 : 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달
    // 3. 모든 Advisor 빈 조회 : 자동 프록시 생성기 - 빈 후처리기는 스프링 컨테이너에서 모든 Advisor 를 조회
    // 4. 프록시 적용 대상 체크 : 앞서 조회한 Advisor에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용 대상인지 아닌지 판단
    // -> 객체 클래스 정보는 물론이고, 해당 객체의 모든 메서드를 포인트컷에 하나하나 모두 매칭
    // 5. 프록시 생성 : 프록시 적용 대상이면 프록시를 생성하고 반환해서 프록시를 스프링 빈으로 등록
    // 6. 빈 등록 : 반환된 객체는 스프링 빈으로 등록
}
