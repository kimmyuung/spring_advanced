package com.example.spring_advanced.pureproxy.concreteproxy.code;

public class ConcreateClient {

    private ConcreateLogic concreateLogic;

    public ConcreateClient(ConcreateLogic concreateLogic) {
        this.concreateLogic = concreateLogic;
    }

    public void exeute() {
        concreateLogic.opeation();
    }
}
