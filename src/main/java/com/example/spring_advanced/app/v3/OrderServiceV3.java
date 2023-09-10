package com.example.spring_advanced.app.v3;

import com.example.spring_advanced.trace.TraceId;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.hellotrace.HelloTraceV2;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.begin()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    // 1. orderItem()은 파라미터로 전달 받은 traceId를 사용해서 trace.beginSync()를 실행
    // 2. beginSync()는 내부에서 다음 traceId를 싱성하면서 트랜잭션 ID는 유지하고 level은 하나 증가시킴
    // 3. beginSync()가 반환한 새로운 TraceStatis를 orderRepository.save()를 호출하면서 파라미터로 전달
    // 4. traceId를 파라미터로 전달하기 위해 orderRepository.save() 의 파라미터에 traceId를 추가해야 함
}
