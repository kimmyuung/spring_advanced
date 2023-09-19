package com.example.spring_advanced.config.dynamicproxy.handler;

import com.example.spring_advanced.app.v6_proxy.*;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*" , "order*" , "save*"};

    @Bean
    public OrderRepository_P_V1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepository_P_V1 orderRepositoryPV1 = new OrderRepositoryImpl_P_V1();

        OrderRepository_P_V1 proxy = (OrderRepository_P_V1) Proxy.newProxyInstance(OrderRepository_P_V1.class.getClassLoader() ,
                new Class[]{OrderRepository_P_V1.class},
                new LogTraceFilterHandler(orderRepositoryPV1, logTrace, PATTERNS));
        return proxy;
    }

    @Bean
    public OrderService_P_V1 orderServicePV1(LogTrace logTrace) {
        OrderService_P_V1 orderServicePV1 = new OrderServiceImpl_P_V1(orderRepositoryV1(logTrace));
        OrderService_P_V1 proxy = (OrderService_P_V1) Proxy.newProxyInstance(OrderService_P_V1.class.getClassLoader() ,
                new Class[]{OrderService_P_V1.class},
                new LogTraceFilterHandler(orderServicePV1, logTrace, PATTERNS));
        return proxy;
    }

    @Bean
    public OrderController_P_V1 orderControllerPV1(LogTrace logTrace) {
        OrderController_P_V1 orderControllerPV1 = new OrderControllerImpl_P_V1(orderServicePV1(logTrace));
        OrderController_P_V1 proxy = (OrderController_P_V1) Proxy.newProxyInstance(OrderController_P_V1.class.getClassLoader() ,
                new Class[]{OrderController_P_V1.class},
                new LogTraceFilterHandler(orderControllerPV1, logTrace, PATTERNS));
        return proxy;
    }
}
