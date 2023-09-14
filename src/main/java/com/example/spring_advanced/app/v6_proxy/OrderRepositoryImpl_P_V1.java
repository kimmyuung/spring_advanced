package com.example.spring_advanced.app.v6_proxy;

public class OrderRepositoryImpl_P_V1 implements OrderRepository_P_V1{

    @Override
    public void save(String itemId) {
        // 저장 로직
        if(itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
