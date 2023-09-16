package com.example.spring_advanced.app.v7_proxy;

import com.example.spring_advanced.app.v6_proxy.OrderService_P_V1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping // @Controller는 컴포넌트 스캔의 대상이지만 @RequestMapping은 컴포넌트 스캔의 대상이 아님
@ResponseBody
public class OrderController_P_V2 {

    private final OrderService_P_V2 orderService;

    public OrderController_P_V2(OrderService_P_V2 orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/v7/proxy/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }


    @GetMapping("/v7/proxy/no-log")
    public String noLog() {
        return "ok";
    }
}
