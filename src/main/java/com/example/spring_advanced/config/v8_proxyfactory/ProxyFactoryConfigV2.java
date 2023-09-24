package com.example.spring_advanced.config.v8_proxyfactory;

import com.example.spring_advanced.app.v6_proxy.*;
import com.example.spring_advanced.app.v7_proxy.OrderController_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderRepository_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderService_P_V2;
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
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderController_P_V2 orderController(LogTrace logTrace) {
        OrderController_P_V2 orderController = new OrderController_P_V2(orderService(logTrace));
        ProxyFactory proxyFactory = new ProxyFactory(orderController);
        proxyFactory.addAdvisor(getAdvisor(logTrace));
        OrderController_P_V2 proxy = (OrderController_P_V2) proxyFactory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass(), orderController.getClass());
        return proxy;

    }

    @Bean
    public OrderService_P_V2 orderService(LogTrace logTrace) {
        OrderService_P_V2 orderService = new OrderService_P_V2(orderRepositoryPV2(logTrace));
        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderService_P_V2 proxy = (OrderService_P_V2) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass(), orderService.getClass());
        return proxy;
    }

    @Bean
    public OrderRepository_P_V2 orderRepositoryPV2(LogTrace logTrace) {
        OrderRepository_P_V2 orderRepository = new OrderRepository_P_V2();

        ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
        proxyFactory.addAdvisor(getAdvisor(logTrace));
        OrderRepository_P_V2 proxy = (OrderRepository_P_V2) proxyFactory.getProxy();
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
}
