package com.example.spring_advanced.config.v9_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;
    // 포인트컷 + 어드바이스 존재하는 어드바이저

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName = {} bean {}" , beanName, bean.getClass());

        // 프록시 적용 대상 여부 체크
        // 프록시 적용 대상이 아니면 원본을 그대로 진행
        String packageName =  bean.getClass().getPackageName();
        if(! packageName.startsWith(basePackage)) {
            return bean; // 다른 데서 온 객체면 원본을 그대로 객체로 빈으로 등록
        }
        // 프록시 대상이면 프록시를 만들어서 반환
        ProxyFactory proxyFactory = new  ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy : target = {}, proxy {}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
