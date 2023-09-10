package com.example.spring_advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        call(); // 상속
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {} " , resultTime);
    }

    // 탬플릿 메서드 패턴 : 탬플릿을 사용하는 방식
    // 탬필릿이라는 틀에 변하지 않는 부분을 몰아두고, 변하는 부분을 별도로 호출하여 처리

    protected abstract void call();
}
