package com.example.spring_advanced.pureproxy;

import com.example.spring_advanced.pureproxy.code.ProxyPatternClient;
import com.example.spring_advanced.pureproxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPattenrTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }
}
