package com.example.spring_advanced.proxyfactory;

import com.example.spring_advanced.common.ConcreateService;
import com.example.spring_advanced.common.ServiceImpl;
import com.example.spring_advanced.common.ServiceInterface;
import com.example.spring_advanced.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}" , target.getClass());
        log.info("proxyClass = {}" , proxy.getClass());

        proxy.save();
        //proxy가 제공하는 부가기능 로직을 advice라 한다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreateService target = new ConcreateService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreateService proxy = (ConcreateService) proxyFactory.getProxy();

        log.info("targetClass = {}" , target.getClass());
        log.info("proxyClass = {}" , proxy.getClass());

        proxy.call();
        //proxy가 제공하는 부가기능 로직을 advice라 한다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 기반 프록시 생성
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}" , target.getClass());
        log.info("proxyClass = {}" , proxy.getClass());

        proxy.save();
        //proxy가 제공하는 부가기능 로직을 advice라 한다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    // 프록시 팩토리의 기술 선택 방법
    // 1. 대상에 인터페이스가 있으면 JDK 동적 프록시, 인터페이스 기반 프록시
    // 2. 대상에 인터페이스가 없으면 CGLIB, 구체 클래스 기반 프록시
    // 3. proxyTargetClass = true : CGLIB,구체 클래스 기반 프록시, 인터페이스 여부와 상관없음
}
