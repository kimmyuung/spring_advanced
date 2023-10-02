package com.example.spring_advanced.aop.pointcut;

import com.example.spring_advanced.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method method;

    // execution
    // 메소드 실행 조인 포인트를 매칭
    // execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)throws-patttern?)
    // execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
    // ? 생략 가능
    // * 같은 패턴 지정 가능
    @BeforeEach
    public void init() throws NoSuchMethodException{
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("method = {}" , method);
    }

    @Test
    void exactMethod() {
        pointcut.setExpression("execution(public String com.example.spring_advanced.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }
    // 매핑조건
    // 접근제어자? : 생략
    // 반환타입 : *
    // 선언타입? : 생략
    // 메서드이름 : *
    // 파라미터 : (..)

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* com.(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch1() {
        pointcut.setExpression("execution(* co*.(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch2() {
        pointcut.setExpression("execution(* *om(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch() {
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.*.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* com.example.aop.*.*.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* com.example..*.*.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* com.example.aop..*.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberServiceImpl.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }
    // execution에서는 MemberService처럼 부모 타입을 선언해도 그 자식 타입은 매칭
    // 부모타입 매칭만 허용
    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        //pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberService.*(..))"); // -> X
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberServiceImpl.*(..))"); // -> O
        Method interMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(interMethod, MemberServiceImpl.class)).isTrue();
    }
    // 인터페이스(부모 타입)에 선언된 것만 매칭 가능

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.example.spring_advanced.aop.member.MemberService.*(..))");
        Method interMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(interMethod, MemberServiceImpl.class)).isFalse();
    }
    // 부모 타입을 표현식에 선언한 경우 부모 타입에서 선언한 메서드가 자식 타입에 있어야 매칭에 성공
    // -> 부모 타입에 있는 hello(String)은 메서드는 매칭에 성공하지만, 부모 타입에 없는 internal 메서드는 매칭에 실패한다.

    // String 타입의 파라미터 허용
    // (String)
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // String 타입의 파라미터 허용
    // (String)
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    // (Xxx)
    @Test
    void argsMatchScan() {
        pointcut.setExpression("execution(* *(String, ..))");
        // (String, ..) : 파라미터가 String이거나 없어도 됨
        // (String, *) : 파라미터가 2개 있어야 하고, 첫번째 파리미터는 String, 두번째 파리미터는 아무거나 상관 없음
        Assertions.assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }
    // execution 파라미터 매칭 규칙
    // (String) : 정확하게 String 타입 파라미터
    // () : 파라미터가 없어야 한다.
    // (*) : 정확히 하나의 파라미터, 단 모든 타입을 허용
    // (*, *) : 정확히 두 개의 파라미터, 단 모든 타입을 허용
    // (..) : 숫자와 무관하게 모든 파라미터, 모든 타입을 허용. 참고로 파라미터가 없어도 됨
    // (String, ..) : String 타입으로 시작해야 한다. 숫자와 무관하게 모든 파라미터, 모든 타입을 허용

}
