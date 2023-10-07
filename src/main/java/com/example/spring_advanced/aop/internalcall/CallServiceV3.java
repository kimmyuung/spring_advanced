package com.example.spring_advanced.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

// 구조를 변경 및 분리
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal(); // 내부 메서드 호출
        // 내부 호출 자체가 사라지고 callService -> internalService를 호출하는 구조로 변경됨 자연스럽게 AOP 적용
    }

    public void internal() {
        log.info("call internal");
    }

    // AOP는 주로 트랜잭션 적용이나 주요 컴포넌트의 로그 출력 기능에 사용
    // 인터페이스에 메서드가 나올 정도의 규모에 AOP를 적용하는 것이 적당
    // AOP는 public 메서드에만 적용 private 메서드처럼 작은 단위에는 AOP 적용하지 않음
    // AOP 적용 위해 private 메서드를 외부 클래스로 변경하고 public 으로 변경하는 길은 없음
    // -> public 메서드에서 public 메서드를 내부 호출하는 경우에는 문제가 발생
}
