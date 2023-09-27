package com.example.spring_advanced.config.v11_aop.aspect;

import com.example.spring_advanced.config.AppV6Config;
import com.example.spring_advanced.config.AppV7Config;
import com.example.spring_advanced.config.v11_aop.aspect.aspect.LogTraceAspect;
import com.example.spring_advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV6Config.class, AppV7Config.class})
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

}
