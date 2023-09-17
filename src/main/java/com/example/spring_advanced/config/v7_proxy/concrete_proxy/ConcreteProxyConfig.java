package com.example.spring_advanced.config.v7_proxy.concrete_proxy;

import com.example.spring_advanced.app.v7_proxy.OrderController_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderRepository_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderService_P_V2;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderController_P_V2 orderControllerPV2(LogTrace logTrace) {
        OrderController_P_V2 orderControllerPV2 = new OrderController_P_V2(orderServicePV2(logTrace));
        return new OrderControllerConcreteProxy(orderControllerPV2, logTrace);
    }

    @Bean
    public OrderService_P_V2 orderServicePV2(LogTrace trace) {
        OrderService_P_V2 servicePV2 = new OrderService_P_V2(orderRepository_p_v2(trace));
        return new OrderServiceConcreteProxy(servicePV2, trace);
    }

    @Bean
    public OrderRepository_P_V2 orderRepository_p_v2(LogTrace logTrace) {
        OrderRepository_P_V2 repositoryPV2 = new OrderRepository_P_V2();
        return new OrderRepositoryConcreteProxy(repositoryPV2, logTrace);
    }
}
