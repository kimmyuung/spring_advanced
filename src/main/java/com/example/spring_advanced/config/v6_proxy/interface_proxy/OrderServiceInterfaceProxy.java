package com.example.spring_advanced.config.v6_proxy.interface_proxy;

import com.example.spring_advanced.app.v6_proxy.OrderService_P_V1;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderService_P_V1 {

    private final OrderService_P_V1 target;
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try{
            status =  logTrace.begin("OrderService.orderItem()");
            // target call
            target.orderItem(itemId);
            logTrace.end(status);
        }catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
