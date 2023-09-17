package com.example.spring_advanced.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); // 호출 메서드 다름
        log.info("result = {}" , result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}" , result2); // 호출 메서드 다름
        // 공통 로직2 종료
    }

    @Test
    void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보
        Class classHello = Class.forName("com.example.spring_advanced.jdkdynamic.ReflectionTest$Hello");
        // 클래스 내부 정보 획득 클래스 내부 클래스는 구분을 위해 $ 사용하여 검색
        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        // 메서드의 메타 정보를 획득
        Object result1 = methodCallA.invoke(target);
        log.info("result = {}", result1);

        // callB 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result = {}", result2);
    }

    @Test
    void reflection2 () throws Exception{
        // 클래스 정보
        Class classHello = Class.forName("com.example.spring_advanced.jdkdynamic.ReflectionTest$Hello");
        // 클래스 내부 정보 획득 클래스 내부 클래스는 구분을 위해 $ 사용하여 검색
        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        // 메서드의 메타 정보를 획득
       dynamicCall(methodCallA, target);

        // callB 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}" , result);
    }
    // 리플렉션은 가급적 사용하면 안됨
    // -> 리플렉션은 동적으로 작동하기 때문에 컴파일 시점에서 오류를 잡을 수 없음
    // --> 런타임 시점에서 오류가 발생하므로 사용자가 직접 실행해야 오류가 발생
    // ---> 리플렉션은 프레임워크 개발이나 또는 매우 일반적인 공통 처리가 필요할 때 부분적으로 주의해서 사용해야 함


    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }

    }

}
