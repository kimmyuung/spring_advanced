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
    // 문제점
    // 인터페이스가 있는 경우에는 JDK 동적 프록시 적용 그렇지 않은 경우 CGLIB를 적용
    // 두 기술 함께 사용 시에 JDK 제공하는 InvocationHanldler와 CGLIB가 제공하는 MethodInterceptor를 각각 중복으로 생성하여 관리해야 하는가
    // 스프링은 동적 프록시를 통합해서 편리하게 만들어주는 프록시 팩토리 라는 기능을 제공
}
