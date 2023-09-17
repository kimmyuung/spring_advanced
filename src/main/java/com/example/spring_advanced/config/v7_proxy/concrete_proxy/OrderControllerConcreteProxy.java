package com.example.spring_advanced.config.v7_proxy.concrete_proxy;

import com.example.spring_advanced.app.v7_proxy.OrderController_P_V2;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;


public class OrderControllerConcreteProxy extends OrderController_P_V2 {
    private final OrderController_P_V2 target;
    private final LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderController_P_V2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository.request()");
            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
