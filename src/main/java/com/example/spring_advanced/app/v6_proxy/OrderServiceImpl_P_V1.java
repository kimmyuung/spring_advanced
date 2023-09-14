package com.example.spring_advanced.app.v6_proxy;

public class OrderServiceImpl_P_V1 implements OrderService_P_V1{

    private final OrderRepository_P_V1 orderRepository;

    public OrderServiceImpl_P_V1(OrderRepository_P_V1 orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
