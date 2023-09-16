package com.example.spring_advanced.app.v8_proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController_P_V3 {
    private final OrderService_P_V3 orderService;

    public OrderController_P_V3(OrderService_P_V3 orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/v8/proxy/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }


    @GetMapping("/v8/proxy/no-log")
    public String noLog() {
        return "ok";
    }
}
