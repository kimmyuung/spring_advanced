package com.example.spring_advanced.trace.callback;

import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace logTrace;


    public TraceTemplate(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("start");

            // 로직 호출
            T result = callback.call();
            logTrace.end(status);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
