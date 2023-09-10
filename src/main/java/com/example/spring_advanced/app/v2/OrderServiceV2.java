package com.example.spring_advanced.app.v2;

import com.example.spring_advanced.trace.TraceId;
import com.example.spring_advanced.trace.TraceStatus;
import com.example.spring_advanced.trace.hellotrace.HelloTraceV1;
import com.example.spring_advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderService.begin()");
            orderRepository.save(status.getTraceId(), itemId);
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
