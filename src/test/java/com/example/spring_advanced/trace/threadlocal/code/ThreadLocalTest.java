package com.example.spring_advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalTest {
    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name = {} -> nameStroe = {}" , name, nameStore.get());
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore = {}" , nameStore.get());
        return nameStore.get();
    }
    // ThreadLocal.set(); // ThreadLocal value set
    // ThreadLocal.get(); // ThreadLocal value get
    // ThreadLocal.remove(); // ThreadLocal value delete

    // 해당 쓰레드가 쓰레드 로컬을 모두 사용하고 나면 반드시 ThreadLocal.remove()를 호출하여 쓰레드 로컬에 저장된 값을 제거해야 함


    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {e.printStackTrace();}
    }
}
