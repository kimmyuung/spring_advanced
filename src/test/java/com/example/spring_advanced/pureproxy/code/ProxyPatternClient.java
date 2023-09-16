package com.example.spring_advanced.pureproxy.code;

import lombok.AllArgsConstructor;


public class ProxyPatternClient {

    private  Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
