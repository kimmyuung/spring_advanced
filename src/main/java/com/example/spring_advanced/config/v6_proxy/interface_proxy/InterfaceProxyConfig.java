package com.example.spring_advanced.config.v6_proxy.interface_proxy;

import com.example.spring_advanced.app.v6_proxy.*;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderController_P_V1 orderController(LogTrace logTrace) {
        OrderControllerImpl_P_V1 controllerImpl = new OrderControllerImpl_P_V1(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderService_P_V1 orderService(LogTrace logTrace) {
        OrderServiceImpl_P_V1 orderServiceImpl = new OrderServiceImpl_P_V1(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepository_P_V1 orderRepository(LogTrace logTrace) {
        OrderRepositoryImpl_P_V1 orderRepositoryImpl = new OrderRepositoryImpl_P_V1();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }
}
