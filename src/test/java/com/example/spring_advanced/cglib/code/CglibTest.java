package com.example.spring_advanced.cglib.code;

import com.example.spring_advanced.common.ConcreateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreateService target = new ConcreateService();

        Enhancer enhancer = new Enhancer();
        // CGLIB는 Enhancer를 이용하여 프록시를 생성
        enhancer.setSuperclass(ConcreateService.class);
        // 구체 클래스를 상속받아 프로시를 생성
        enhancer.setCallback(new TimeMethodInterceptor(target));
        // 프록시에 실행할 로직을 할당

        ConcreateService proxy = (ConcreateService) enhancer.create();
        log.info("targetClass = {}" , target.getClass());
        log.info("proxyClass = {}" , proxy.getClass());

        proxy.call();
    }
}
