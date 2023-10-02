package com.example.spring_advanced.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    // 어드바이스 종류
    // @Around : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능
    // @Before : 조인 포인트 실행 전에 수행
    // @After Returning : 조인 포인트가 정상 완료 후 실행
    // @After Throwing : 메서드가 예외를 던지는 경우 실행
    // @After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
    // 실행 순서 : Around -> Before -> After -> AfterReturning -> AfterThrowing
    // 좋은 설계는 제약이 있는 설계
    @Around("com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable
    {
        try {
            //@Before
            log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()", returning = "result")    // 이름 맞춰야한다.
    public void doReturn(JoinPoint joinPoint, Object result) {
        // 리턴문이 없기 때문에, 결과물의 조작이 불가능하다.
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }
    //
    @AfterReturning(value = "com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturnString(JoinPoint joinPoint, Integer result) {   // 받을 수 없는 타입이라 아예 실행이 안 된다.
        log.info("[return2] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    @After(value = "com.example.spring_advanced.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
