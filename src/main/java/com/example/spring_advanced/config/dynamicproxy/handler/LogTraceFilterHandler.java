package com.example.spring_advanced.config.dynamicproxy.handler;

import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // method name filter
        String methodName = method.getName();
        // savem request, reque*, *est
        if(!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
            // 특정 메서드 이름이 매칭 되는 경우에만 logtrace 기능 실행
        }

        TraceStatus status = null;
        try{
            String message = method.getDeclaringClass().getSimpleName() + "  " + method.getName() + "()";
            status = logTrace.begin(message);

            // Logic invoke
            Object object = method.invoke(target, args);
            logTrace.end(status);
            return object;
        }catch (Exception e) {logTrace.exception(status, e); throw e;}
    }
}
