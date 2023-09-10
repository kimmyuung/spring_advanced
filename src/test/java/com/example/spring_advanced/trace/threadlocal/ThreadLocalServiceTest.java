package com.example.spring_advanced.trace.threadlocal;

import com.example.spring_advanced.trace.threadlocal.code.FieldService;
import com.example.spring_advanced.trace.threadlocal.code.ThreadLocalTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalTest threadLocalTest = new ThreadLocalTest();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            threadLocalTest.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalTest.logic("userB");
        };
//        Runnable userC = new Runnable() {
//            @Override
//            public void run() {
//                fieldService.logic("userC");
//            }
//        }; 위와 같은 실행

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        //sleep(2000); // 동시성 문제 발생 X
        sleep(100); // 동시성 문제 발생
        threadB.start();

        sleep(3000); // 메인 쓰레드 종료 대기
        log.info("main exit");
    }

    private void sleep(int i) {
        try{Thread.sleep(i);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
