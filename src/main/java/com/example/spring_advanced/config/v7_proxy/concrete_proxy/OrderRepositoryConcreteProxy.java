package com.example.spring_advanced.config.v7_proxy.concrete_proxy;

import com.example.spring_advanced.app.v7_proxy.OrderRepository_P_V2;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepository_P_V2 {

    private final OrderRepository_P_V2 target;
    private final LogTrace logTrace;



    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try{
            status =  logTrace.begin("OrderRepository.save()");
            // target call
            target.save(itemId);
            logTrace.end(status);
        }catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
