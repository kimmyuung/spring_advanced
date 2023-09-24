package com.example.spring_advanced.config.v8_proxyfactory;

import com.example.spring_advanced.app.v6_proxy.*;
import com.example.spring_advanced.config.v8_proxyfactory.advice.LogTraceAdvice;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderController_P_V1 orderController(LogTrace logTrace) {
        OrderController_P_V1 orderController = new OrderControllerImpl_P_V1(orderService(logTrace));
        ProxyFactory factory = new ProxyFactory(orderController);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderController_P_V1 proxy = (OrderController_P_V1) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass(), orderController.getClass());
        return proxy;
    }

    @Bean
    public OrderService_P_V1 orderService(LogTrace logTrace) {
        OrderService_P_V1 orderService = new OrderServiceImpl_P_V1(orderRepository(logTrace));
        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderService_P_V1 proxy = (OrderService_P_V1) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass(), orderService.getClass());
        return proxy;
    }

    @Bean
    public OrderRepository_P_V1 orderRepository(LogTrace logTrace) {
        OrderRepositoryImpl_P_V1 orderRepository = new OrderRepositoryImpl_P_V1();

        ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
        proxyFactory.addAdvisor(getAdvisor(logTrace));
        OrderRepository_P_V1 proxy = (OrderRepository_P_V1) proxyFactory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass(), orderRepository.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // 어드바이저, 어드바이스, 포인트컷 이라는 개념 덕분에 어떠 부가 기능을 어디에 적용할지 알 수 있었다.

    // 다만 너무 많은 설정이 있음
    // 컴포넌트 스캔 : 컴포넌트 스캔을 사용하는 경우 지금까지 학습한 방법으로는 프록시 적용이 불가능
    // 학습 프록시 적용을 위해서는 실제 객체를 스프링 컨테이너에 등록하는 것이 아니라 실제 객체 대신 스프링 컨테이너에 빈으로 등록해야 한다.

}
