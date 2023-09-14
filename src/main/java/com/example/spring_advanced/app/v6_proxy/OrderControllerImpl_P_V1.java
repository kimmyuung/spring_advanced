package com.example.spring_advanced.app.v6_proxy;

public class OrderControllerImpl_P_V1 implements OrderController_P_V1{

    private final OrderService_P_V1 orderService;

    public OrderControllerImpl_P_V1(OrderService_P_V1 orderService) {
        this.orderService = orderService;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
