package com.example.spring_advanced.app.v6_proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping// 스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
@ResponseBody
public interface OrderController_P_V1 {

    @GetMapping("/v1/proxy/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/proxy/no-log")
    String noLog();
}
