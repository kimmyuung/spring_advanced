package com.example.spring_advanced.aop.exam;

import com.example.spring_advanced.aop.exam.annotation.Retry;
import com.example.spring_advanced.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {
    private static int seq = 0;

    // 5번 중 1번 실패
    @Trace
    @Retry
    public String save(String itemId) {
        seq++;
        if(seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return  "ok";
    }
}
