package com.example.spring_advanced.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    // 스프링은 프록시 방식의 AOP를 사용
    // -> 항상 프록시를 통해 대상 객체(Target)을 호출
    // 프록시에서 먼저 어드바이스를 호출하고, 그 이후에 대상 객체를 호출
    // AOP를 적용하면 스프링은 대상 객체 대신에 프록시를 스프링 빈으로 등록 --> 스프링은 의존관계 주입시에 항상 프록시 객체를 주입
    // -> 프록시 객체가 주입되기 때문에 대상 객체를 직접 호출하는 문제는 일반적으로 발생하지 않으나, 대상 객체의 내부에서 메서드 호출 발생시
    // 프록시를 거치지 않고 직접 호출하는 문제가 발생
    public void external() {
        log.info("call external");

        internal(); // 내부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
