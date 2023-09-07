package com.example.spring_advanced.app.v1;

import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepositoryV1;

    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try{
            status = trace.begin("OrderService.orderItem()");

            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }

            orderRepositoryV1.save(itemId);
            trace.end(status);
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
        // 저장 로직

        // 예외 처리
    }
}
