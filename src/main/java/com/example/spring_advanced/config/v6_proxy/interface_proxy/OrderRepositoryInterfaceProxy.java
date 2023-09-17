package com.example.spring_advanced.config.v6_proxy.interface_proxy;

import com.example.spring_advanced.app.v6_proxy.OrderRepository_P_V1;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepository_P_V1 {

    private final OrderRepository_P_V1 target;

    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try{
            status =  logTrace.begin("OrderRepository.request()");
            // target call
            target.save(itemId);
            logTrace.end(status);
        }catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }

    }
}
