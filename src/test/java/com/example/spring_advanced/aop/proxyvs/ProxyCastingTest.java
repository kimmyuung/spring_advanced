package com.example.spring_advanced.aop.proxyvs;

import com.example.spring_advanced.aop.member.MemberService;
import com.example.spring_advanced.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkproxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // jdk dynamic proxy

        // proxy interface casting
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        MemberService MemberServiceProxy = (MemberServiceImpl) proxy;

        Assertions.assertThrows(ClassCastException.class, () ->{
                MemberServiceImpl castingMemberService = (MemberServiceImpl) MemberServiceProxy;
        });

    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // cglib proxy

        // proxy interface casting
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        MemberService MemberServiceProxy = (MemberServiceImpl) proxy;



    }

    // jdk 동적 프록시는 대상 객체인 MemberServiceImpl 로 캐스팅 불가
    // cglib 프록시는 대상 객체인 MemberServiceImpl 로 캐스팅 가능

    // -> 의존관계 주입에 영향을 주기 때문에 중요!
}
