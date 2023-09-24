package com.example.spring_advanced.advisor;

import com.example.spring_advanced.common.ServiceImpl;
import com.example.spring_advanced.common.ServiceInterface;
import com.example.spring_advanced.common.advice.TimeAdvice;
import com.example.spring_advanced.proxyfactory.ProxyFactoryTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;
@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        // Advisor 인터페이스의 가장 일반적인 구현체. 생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 됨.
        // Pointcut.TRUE : 항상 true를 반환하는 포인트컷
        // proxyFactory.addAdvisor(advisor) 프록시 팩토리에 적용할 어드바이저를 지정

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new Mypointcut(), new TimeAdvice());
        // Advisor 인터페이스의 가장 일반적인 구현체. 생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 됨.
        // Pointcut.TRUE : 항상 true를 반환하는 포인트컷
        // proxyFactory.addAdvisor(advisor) 프록시 팩토리에 적용할 어드바이저를 지정

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.addMethodName("save");
        // Advisor 인터페이스의 가장 일반적인 구현체. 생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 됨.
        // Pointcut.TRUE : 항상 true를 반환하는 포인트컷
        // proxyFactory.addAdvisor(advisor) 프록시 팩토리에 적용할 어드바이저를 지정
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class Mypointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
            // true 반환 클래스필터
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        private String matchName = "save";
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean equals = method.getName().equals(matchName);
            log.info("포인트컷 호출 method = {} targetClass = {}" , method.getName(), targetClass);
            return equals;
        }

        @Override
        public boolean isRuntime() {
            return false;
            // isRuntime()이 false인 경우 클래스의 정적 정보만 사용하기 때문에 스프링이 내부에서 캐싱을 통해 성능 향상이 가능하지만
            // isRuntime()이 false인 경우 매개변수가 동적으로 변경된다고 가정하기 때문에 캐싱을 하지 않음
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();

        }
    }
}
