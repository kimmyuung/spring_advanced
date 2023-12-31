package com.example.spring_advanced;

import com.example.spring_advanced.aop.order.aop.AspectV2;
import com.example.spring_advanced.config.AppV6Config;
import com.example.spring_advanced.config.AppV7Config;
import com.example.spring_advanced.config.dynamicproxy.DynamicProxyBasicConfig;
import com.example.spring_advanced.config.v10_autoproxy.AutoProxyConfig;
import com.example.spring_advanced.config.v6_proxy.interface_proxy.InterfaceProxyConfig;
import com.example.spring_advanced.config.v7_proxy.concrete_proxy.ConcreteProxyConfig;
import com.example.spring_advanced.config.v8_proxyfactory.ProxyFactoryConfigV1;
import com.example.spring_advanced.config.v9_postprocessor.BeanPostProcessorConfig;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import com.example.spring_advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(AppV6Config.class)
//@Import({AppV7Config.class , AppV6Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)

//@Import(DynamicProxyBasicConfig.class)
//@Import(ProxyFactoryConfigV1.class)
//@Import(BeanPostProcessorConfig.class)
//@Import(AutoProxyConfig.class)
// 클래스를 스프링 빈으로 등록
// 일반적으로 설정 파일 등록 시 사용되나 스프링 빈 등록 시에도 사용이 가능
@Import(AspectV2.class)
@SpringBootApplication(scanBasePackages = "com.example.spring_advanced.aop")
// @ComponentScan의 기능과 같음 : 컴포넌트 스캔을 시작할 위치를 지정
// 사용하지 않으면 SpringAdvancedApplication 있는 패키지와 그 하위 패키지를 스캔
public class SpringAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAdvancedApplication.class, args);
    }

    @Bean
    public LogTrace trace() {
        return new ThreadLocalLogTrace();
    }
}
