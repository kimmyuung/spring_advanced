package com.example.spring_advanced.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator execute");
        long startTime = System.currentTimeMillis();
        String result = component.operation();
        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;

        log.info("TimeDecorator quit resultTime = {} ms", resultTime);

        return result;
    }
}
