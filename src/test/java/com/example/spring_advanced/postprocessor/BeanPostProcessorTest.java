package com.example.spring_advanced.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {
// 빈 후처리기는 빈을 조작하고 변경할 수 있는 후킹 포인트
    // 빈 객체를 조작하거나 심지어 다른 객체로 바꾸어 버릴 수 있을 정도로 막강
    // -> 조작 : 해당 객체의 특정 메서드를 호출하는 것을 의미
    // --> 일반적으로 스프링 컨테이너가 등록하는, 특히 컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없는데, 빈 후처리기를 사용하면
    // 개발자가 등록하는 모든 빈을 중간에 조작이 가능 --> 빈 객체를 프록시로 교체하는 것도 가능하다는 뜻

    // @PostConstruct
    // @PostConstruct는 스프링 빈 생성 이후에 빈을 초기화하는 역할을 담당 ->  빈의 초기화는 단순히 @PostConstruct 애노에티션이 붙은 초기화
    // 메서드를 한번 호출만 하면 됨
    // -> 생성된 빈을 한번 조작하는 것

    // 스프링은 CommonAnnotationBeanPostProcessor 라는 빈 후처리기를 자동으로 등록하는데, 여기에서 @PostConstruct
    // 애노테이션이 붙은 메서드를 호출 -> 스프링 스스로도 스프링 내부의 기능을 확장하기 위해 빈 후처리기를 사용
    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicTest.BasicConfig.class);

        // beanA라는 이름을 가진 B 객체가 생성
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        // A는 빈으로 등록되지 않음
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(BasicTest.B.class));
        //
    }

    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public BasicTest.A a(){
            return new BasicTest.A();
        }
        @Bean
        public AToBPostProcessor helloPostProcessor() {
            return new AToBPostProcessor();
        }
    }
    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {} bean = {}" , beanName, bean);
            if(bean instanceof A) {
                return new B();
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
    }
}
