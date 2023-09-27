package com.example.spring_advanced.config.v11_aop.aspect.aspect;

import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
// 자동 프록시 생성기는 2가지 일을 함
// @Aspect를 찾아서 Advisor로 만들어줌
// 어드바이저를 기반으로 프록시를 생성
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* com.example.spring_advanced..*(..))") // 포인트컷(AspectJ 표현식)
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{ // 어드바이스
        TraceStatus status = null;
        try{

            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // Logic invoke
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        }catch (Exception e) {logTrace.exception(status, e); throw e;}
    }
    // 어드바이저

    // @Aspect를 어드바이저로 변환해서 저장하는 과정
    // 1. 실행 : 스프링 애플리케이션 시점에 자동 프록시 생성기를 호출
    // 2. 모든 @Aspect 빈 조회 : 자동 프록시 생성기는 스프링 컨테이너에서 @Aspect 애노테이션이 붙은 스프링 빈을 모두 조회
    // 3. 어드바이저 생성 : @Aspect 어드바이저 빌더를 통해 @Aspect 애노테이션 정보를 기반으로 어드바이저를 생성
    // 4. @Aspect 기반 어드바이저 저장 : 생선한 어드바이저를 @Aspect 어드바이저 빌더 내부에 저장

    // AOP
    // 애플리케이션 로직은 크게 핵심 기능과 부가 기능으로 나눌 수 잇음
    // 핵심 기능 : 해당 객체가 제공하는 고유의 기능 ex) OrderService
    // 부가 기능 : 핵심 기능을 보조하기 위해 제공되는 기능 ex) 로그 추적기, 트랜잭션 기능

    // 컴파일러를 사용하여 .class를 만드는 시점에 부가 기능 로직을 추가 가능 = 부가 기능 코드가 핵심 기능이 있는 컴파일된 코드 주변에 붙어 버림
    // -> 원본 로직에 부가 기능 로직이 추가되는 것을 위빙이라 함
    // 로드 타임 위빙 : JVM에 저장하기 전 class 파일 조작gksms rjt
    // 런타임 시점 : 클래스 로더에 클래스가 다 올라가서 이미 자바가 실행되고 난 다음 -> main 메서드 실행 이후
    // -> 스프링 컨테이너의 도움을 받고 프록시와 DI, 빈 포스트 프로세서 같은 개념을 총 동원 -> 프록시 방식의 AOP

    // AOP 용어
    // 조인 포인트 : 어드바이스가 적용될 수 있는 위치, 메소드 실행, 생성자 호출, 필드 값 접근, static 메서드 접근 같은 프로그램 실행 중 지정
    // 조인 포인트는 추상적인 개념 AOP를 적용할 수 있는 모든 지점이라 생각하면 됨
    // 스프링 AOP는 프록시 방식을 사용하므로 조인 포인트는 항상 메서드 실행 지점으로 제한
    // 포인트컷 : 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
    // 주로 AspectJ 표현식을 사용해서 지정
    // 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능
    // 타겟 : 어드바이스를 받는 객체, 포인트컷으로 결정
    // 어드바이스 : 부가 기능, 특정 조인 포인트에서 Aspect에 의해 취해지는 조치, Around, Before, After 등 다양한 종류의 어드바이스 존재
    // 애스팩트 : 어드바이스 + 포인트컷을 모듈화 한 것
    // 어드바이저 : 하나의 어드바이스와 하나의 포인트 컷으로 구성 스프링 AOP에서만 사용
    // 위빙 : 포인트컷으로 결정한 타켓의 조인 포인트에 어드바이스를 적용하는 것, 위빙을 통해 핵심 기능 코드에 영향 주지 않고 부가 기능 추가 가능
    // AOP 프록시 : AOP기능을 구현하기 위해 만든 프록시, JDK 프록시 및 CGLIB 프록시 사용
}
