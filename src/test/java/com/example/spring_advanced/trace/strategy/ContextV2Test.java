package com.example.spring_advanced.trace.strategy;

import com.example.spring_advanced.trace.strategy.code.strategy.ContextV2;
import com.example.spring_advanced.trace.strategy.code.strategy.StrategyLogic1;
import com.example.spring_advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /*
   전략 패턴 적용
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }
    // 전략을 필드로 호출하지 않고 파라미터로 전달

    // 1. 클아이언트는 Context를 실행하면서 Context를 실행할 때 마다 전략을 인수로 전달
    // 2. Context는 execute() 로직 실행
    // 3. Context는 파라미터러 넘어온 strategy.call()를 실행
    // 4. Context의 execute()로직 종료
}
