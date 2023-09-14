package com.example.spring_advanced;

import com.example.spring_advanced.trace.logtrace.FieldLogTrace;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import com.example.spring_advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 자동으로 컴포넌트 스캔
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    // 사용자 B가 조회를 위한 새로운 HTTP 요청
    // WAS는 쓰레드 풀에서 스레드를 하나 조회
    // 쓰레드 thread-A가 할당
    // thread-A는 쓰레드 로컬에서 데이터를 조회
    // 쓰레드 로컬은 thread-A 전용 데이터 보관소에 있는 사용자 A 값을 반환
    // 사용자 A 값이 반환
    // 사용자 B는 사용자 A의 정보를 조회 하게 됨

    // 예방하려면 사용자A의 요청이 끝날 때 쓰레드 로컬의 값을 ThreadLocal.remove()을 통해 꼭 제거해야 함

}
