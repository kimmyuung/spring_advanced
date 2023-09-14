package com.example.spring_advanced.config;

import com.example.spring_advanced.app.v6_proxy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV6Config {

    @Bean
    public OrderController_P_V1 controllerPV1() {
        return new OrderControllerImpl_P_V1(orderServiceV1());
    }

    @Bean
    public OrderService_P_V1 orderServiceV1() {
        return new OrderServiceImpl_P_V1(orderRepositoryV1());
    }

    @Bean
    public OrderRepository_P_V1 orderRepositoryV1() {
        return new OrderRepositoryImpl_P_V1();
    }

}
