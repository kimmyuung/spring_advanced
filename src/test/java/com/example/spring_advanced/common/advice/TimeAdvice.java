package com.example.spring_advanced.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("Time proxy execution");
      long startTime = System.currentTimeMillis();

      Object result = invocation.getMethod();
      // target 클래스를 받아 정보를 저장

      long endTime = System.currentTimeMillis();
      long resultTime = endTime - startTime;
      log.info("TimeProxy shut down resultTime = {}" , resultTime);
      return result;
    }
}
