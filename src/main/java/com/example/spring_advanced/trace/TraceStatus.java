package com.example.spring_advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TraceStatus {
    // 로그의 시작할 때의 상태 정보를 가지고 있음
    // 로그를 종료할 때 사용

    private TraceId traceId;
    private Long startTimeMs;
    private String message;


}
