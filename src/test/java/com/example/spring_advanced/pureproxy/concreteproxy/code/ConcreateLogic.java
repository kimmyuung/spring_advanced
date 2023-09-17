package com.example.spring_advanced.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreateLogic {
    public String operation() {
        log.info("ConcreateLogic execute");
        return "data";
    }
}
