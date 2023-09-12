package com.example.spring_advanced.app.v5;

import com.example.spring_advanced.trace.logtrace.LogTrace;
import com.example.spring_advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }

    // 1. orderItem()은 파라미터로 전달 받은 traceId를 사용해서 trace.beginSync()를 실행
    // 2. beginSync()는 내부에서 다음 traceId를 싱성하면서 트랜잭션 ID는 유지하고 level은 하나 증가시킴
    // 3. beginSync()가 반환한 새로운 TraceStatis를 orderRepository.save()를 호출하면서 파라미터로 전달
    // 4. traceId를 파라미터로 전달하기 위해 orderRepository.save() 의 파라미터에 traceId를 추가해야 함
}
