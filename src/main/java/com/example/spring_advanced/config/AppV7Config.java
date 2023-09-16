package com.example.spring_advanced.config;

import com.example.spring_advanced.app.v6_proxy.*;
import com.example.spring_advanced.app.v7_proxy.OrderController_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderRepository_P_V2;
import com.example.spring_advanced.app.v7_proxy.OrderService_P_V2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV7Config {

    @Bean
    public OrderController_P_V2 controllerPV2() {
        return new OrderController_P_V2(orderServiceV2());
    }

    @Bean
    public OrderService_P_V2 orderServiceV2() {
        return new OrderService_P_V2(orderRepositoryV2());
    }

    @Bean
    public OrderRepository_P_V2 orderRepositoryV2() {
        return new OrderRepository_P_V2();
    }

    // 프록시 : 서버와 프록시는 같은 인터페이스를 사용해야 함
    // 프록시는 대체 가능 해야 함.
    // 런타임(애플리케이션 실행 시점)에 클라이언트 객체에 DI를 사용해서 클라이언트 코드를 전혀 변경할 필요 없이 프록시 주입 가능
    // 프록시의 주요 기능
    // 1. 접근 제어 : 권한에 따른 접근 차단, 캐싱, 지연 로딩
    // 2. 부가 기능 추가 : 원래 서버가 제공하는 기능에 더해서 부가 기능 수행, 요청 값이나 응답 값 변형, 실행 시간 측정하여 추가 로그
    // 프록시 패턴 : 접근 제어 목적

}
