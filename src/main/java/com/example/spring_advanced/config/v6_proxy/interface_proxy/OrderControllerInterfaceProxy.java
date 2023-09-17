package com.example.spring_advanced.config.v6_proxy.interface_proxy;

import com.example.spring_advanced.app.v6_proxy.OrderController_P_V1;
import com.example.spring_advanced.app.v6_proxy.OrderService_P_V1;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderController_P_V1 {

    private final OrderController_P_V1 target;
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try{
            status =  logTrace.begin("OrderController.request()");
            // target call
            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        }catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
