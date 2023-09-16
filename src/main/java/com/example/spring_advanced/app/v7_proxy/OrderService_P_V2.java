package com.example.spring_advanced.app.v7_proxy;

import com.example.spring_advanced.app.v6_proxy.OrderRepository_P_V1;

public class OrderService_P_V2 {

    private final OrderRepository_P_V2 orderRepository;

    public OrderService_P_V2(OrderRepository_P_V2 orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
