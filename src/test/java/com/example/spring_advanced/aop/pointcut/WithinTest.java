package com.example.spring_advanced.aop.pointcut;

import com.example.spring_advanced.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class WithinTest {
    // within 지시자는 특정 타입 내의 조인 포인트에 대한 매칭을 제한
    // -> 해당 타입이 매칭되면 그 안의 메서드(조인 포인트)들이 자동으로 매칭
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method method;


    @BeforeEach
    public void init() throws NoSuchMethodException{
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(com.example.spring_advanced.aop.member.MemberServiceImpl)");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(com.example.spring_advanced.aop.member.*Service*)");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.example.spring_advanced.aop..*)");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // 표현식에 부모 타입을 지정하면 안됨 -> 정확하게 타입이 맞아야 함
    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안됨")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.example.spring_advanced.aop.member.MemberService)");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입 기반, 인터페이스 선정 가능")
    void executionSuperTypeTrue() {
        pointcut.setExpression("execution(com.example.spring_advanced.aop.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // @target은 인스턴스의 모든 메서드를 조인 포인트로 적용
    // @within은 해당 타입 내에 있는 메서드만 조인 포인트로 적용

}
