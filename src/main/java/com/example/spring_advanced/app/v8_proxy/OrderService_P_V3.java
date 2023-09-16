package com.example.spring_advanced.app.v8_proxy;

import com.example.spring_advanced.app.v7_proxy.OrderRepository_P_V2;
import org.springframework.stereotype.Service;

@Service
public class OrderService_P_V3 {
    private final OrderRepository_P_V3 orderRepository;

    public OrderService_P_V3(OrderRepository_P_V3 orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }

}
