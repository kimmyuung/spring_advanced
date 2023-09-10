package com.example.spring_advanced.trace.template;

import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace logTrace;

    public AbstractTemplate(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("start");

            // 로직 호출
            T result = call();
            logTrace.end(status);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected abstract T call();
    // 변하는 부분을 처리하는 메소드 상속 구현


}
