package com.example.spring_advanced;

import com.example.spring_advanced.trace.logtrace.FieldLogTrace;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 자동으로 컴포넌트 스캔
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }

}
