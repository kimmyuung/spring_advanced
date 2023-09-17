package com.example.spring_advanced.config.v7_proxy.concrete_proxy;

import com.example.spring_advanced.app.v7_proxy.OrderService_P_V2;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderService_P_V2 {
    private final OrderService_P_V2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderService_P_V2 target, LogTrace logTrace) {
        super(null);
        // 부모 생성자를 호출해야 하지만 사용할 게 없으므로 null
        this.target = target;
        this.logTrace = logTrace;
    }

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
