package com.example.spring_advanced.pureproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{
    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }


    @Override
    public String operation() {
        log.info("프록시 호출");
        if(cacheValue == null) {
            cacheValue = target.operation();
        }
        return cacheValue;
    }
    // 프록시도 실제 객체와 그 모양이 같아야 하기 때문에 Subject 인터페이스 구현 필요
    // private Subject target : 클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체를 호출해야 함
    // -> 내부에 실제 객체의 참조를 가지고 있어야 함
    // operation : cacheValue에 값이 없으면 실제 객체를 호출해서 값을 구함. -> 구한 값을 cacheValue에 저장하고 반환
}
