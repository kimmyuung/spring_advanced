package com.example.spring_advanced.aop.exam;

import com.example.spring_advanced.aop.exam.aop.TraceAspect;
import com.example.spring_advanced.aop.exam.aop.TryAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({TraceAspect.class, TryAspect.class})
class ExamServiceTest {

    @Autowired
    ExamService examService;

    @Test
    void test() {
        for(int i = 0; i < 5; i++) {
            examService.request("data " + i);
        }
    }
}