package com.example.spring_advanced.aop.proxyvs;

import com.example.spring_advanced.aop.member.MemberService;
import com.example.spring_advanced.aop.member.MemberServiceImpl;
import com.example.spring_advanced.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK Dynamic Proxy
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class = {}" , memberService.getClass());
        log.info("memberServiceImpl class = {}" , memberServiceImpl.getClass());
        memberService.hello("hello1");
    }

    // JDK 동적 프록시는 대상 클래스 타입으로 주입시 문제가 있고, CGLIB는 대상 클래스에 기본 생성자 필수, 생성자 2번 호출 문제 존재

    // 스프링은 CGLIB 라이브러리를 스프링 내부에 함께 패키징해서 별도의 라이브러리 추가 없이 사용 가능해짐
    // 스프링 4.0 부터 기본 생성자 필수인 문제 해결 -> objeneis라는 특별한 라이브러리를 사용해서 기본 생성자 없이 객체 생성 가능

    // 스프링 부트 2.0 - CGLIB 기본 사용
    // 스프링 부트는 별도의 설정이 없다면 AOP를 적용시 기본적으로 proxyTargetClass=True로 설정해서 사용

}
