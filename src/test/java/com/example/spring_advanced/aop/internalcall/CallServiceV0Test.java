package com.example.spring_advanced.aop.internalcall;

import com.example.spring_advanced.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external() {
        callServiceV0.external();
    }
    // 실행 결과 보면 external() 실행시 프록시 호출 CallLogAspect 어드바이스 호출 확인
    // 문제는 external() 안에서 internal()을 호출할 때 발생 -> 어드바이스 호출 안됨

    // 이유는 자바 언어에서는 메서드 앞에 별도의 참조가 없으면 this 라는 뜻으로 자기 자신의 인스턴스를 가리킴
    // -> 자기 자신의 내부 메서드를 호출하게 되는데 this는 실제 대상 객체의 인스턴스를 뜻하므로 프록시를 거치지 않으므로 어드바이스 적용이 안됨


    @Test
    void internal() {
        callServiceV0.internal();
    }
}