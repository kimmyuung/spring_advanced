package com.example.spring_advanced.pureproxy.concreteproxy;

import com.example.spring_advanced.pureproxy.concreteproxy.code.ConcreateClient;
import com.example.spring_advanced.pureproxy.concreteproxy.code.ConcreateLogic;
import com.example.spring_advanced.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreateProxyTest {
    @Test
    void noProxy() {
        ConcreateLogic concreateLogic = new ConcreateLogic();
        ConcreateClient client = new ConcreateClient(concreateLogic);
        client.exeute();
    }

    @Test
    void addProy() {
        ConcreateLogic concreateLogic = new ConcreateLogic();
        TimeProxy timeProxy = new TimeProxy(concreateLogic);
        ConcreateClient client = new ConcreateClient(timeProxy);
        client.exeute();
    }


}
