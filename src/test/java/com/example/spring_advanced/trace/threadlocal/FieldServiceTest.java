package com.example.spring_advanced.trace.threadlocal;

import com.example.spring_advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
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
    // 동시성 문제는 지역 변수에서는 발생 하지 않음 -> 지역 변수는 쓰레드마다 각각 다른 메모리 영역이 할당
    // 주로 발생하는 곳은 같은 인스턴스의 필드(주로 싱글톤에서 자주 발생), 또는 static 같은 공용 필드에 접근할 때 발생
    // + 값을 읽기만 하면 발생하지 않으나 값을 변경하면 발생 가능
    // --> 쓰레드 로컬 사용로 해결

    // ThreadLocal : 해당 쓰레드만 접근할 수 있는 특별한 저장소 ex) 물건 보관 창구
    // 사용자A, 사용자B 모두 창구 직원(쓰레드 로컬)통해 물건 보관하지만, 창구 직원(쓰레드 로컬)이 물건을 구분하여 보관


}
